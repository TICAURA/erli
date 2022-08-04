package com.aura.qamm.service;

import com.aura.qamm.config.JwtTokenUtil;
import com.aura.qamm.dao.FacturacionDao;
import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.Emails;
import com.aura.qamm.dto.facturacion.*;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.util.FacturaEnum;
import com.aura.qamm.util.JSONPathUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class FacturacionService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FacturacionDao facturacionDao;

    @Autowired
    private ServiceInvoker serviceInvoker;

    @Autowired
    private ResourceLoader resourceLoader;



    Logger LOGGER = LoggerFactory.getLogger(QuincenaDAO.class);

    public boolean generarFactura(String email, int month) throws BusinessException {

        boolean existe = facturacionDao.checarSiExisteFactura(email,month);

        if(existe){
            int archivoId = facturacionDao.obtenerArchivo(email,month);
            enviarMail(archivoId,email);
            return true;
        }

        validarDayMes();
        FacturaDTO facturaDTO = facturacionDao.generarFactura(email,month);
        validarComision(facturaDTO);

        int facturaId = facturacionDao.generarStatusFactura(facturaDTO);


        //TIMBRADO
        FacturaMsDTO facturaMsDTO = timbrarFactura(facturaDTO);
        facturacionDao.actualizarStatusFactura(facturaId, FacturaEnum.FACTURADO.getId());
        //GUARDADO
        int idArchivo = guardarPDF(facturaMsDTO);
        validarArchivo(idArchivo);
        facturacionDao.actualizaArchivoFactura(facturaId, FacturaEnum.GUARDADO.getId(),idArchivo);
        //ENVIADO
        enviarMail(idArchivo,email);
        facturacionDao.actualizarStatusFactura(facturaId, FacturaEnum.ENVIADO_A_USUARIO.getId());

        return true;

    }

    private void validarComision(FacturaDTO facturaDTO) throws BusinessException {

        LOGGER.warn("comision::::"+facturaDTO.getTotales().getSubtotal().toString());
        if(facturaDTO.getTotales().getSubtotal().compareTo(new BigDecimal(0))<=0){
            throw new BusinessException("no hay nada que facturar",200);
        }

    }

    private void validarArchivo(int archivoId)throws BusinessException {

            LOGGER.warn("idArchivo::::"+archivoId);
            if(archivoId==0){
                throw new BusinessException("no se pudo guardar el Archivo",500);
            }

    }




    private FacturaMsDTO timbrarFactura(FacturaDTO facturaDTO) throws BusinessException {
        try {



            Properties spCallProps = loadTransferProperties("/factura.properties");

            String body = castObjectToJson(facturaDTO);
            LOGGER.info("Body:" + body);

            String serviceCallResponse = serviceInvoker.invoke(spCallProps, body);

            LOGGER.info("Timbre:" + serviceCallResponse);

            if(serviceCallResponse == null){
                throw new BusinessException("Error timbrando la factura, el servicio de facturación no está disponible.",500);
            }

            return (FacturaMsDTO) castJsonToObject(serviceCallResponse, FacturaMsDTO.class);

        }catch (JsonProcessingException e){
            LOGGER.error("Error timbrando la factura: "+e.getMessage(),e);
            throw new BusinessException("Error timbrando la factura: "+e.getMessage(),500);
        }
    }
    @Value("${endpoints.documento.upload}")
    private String uploadDocumentoEndpoint;

    private int guardarPDF(FacturaMsDTO facturaMsDTO) throws BusinessException {

        try {

            Properties spCallProps = loadTransferProperties("/efile.properties");

            spCallProps.setProperty("endpoint", spCallProps.getProperty("endpoint")+uploadDocumentoEndpoint);

            String body = generatePdfSaveRequest(resourceLoader.getResource("classpath:/JSON/pdfTemplate.json").getInputStream(), facturaMsDTO);

            LOGGER.info("Body:" + body);

            String serviceCallResponse = serviceInvoker.invoke(spCallProps, body);

            LOGGER.info("Timbre:" + serviceCallResponse);

            return generateIdArchivoJson(serviceCallResponse);
        }catch (IOException e){
            LOGGER.error("Error al guardar PDF: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    @Value("${email.subject}")
    private String subject;
    @Value("${email.body}")
    private String bodyEmail;
    @Value("${email.sender}")
    private String sender;


    private void enviarMail(int archivoId,String email)throws BusinessException {


      try {

          Properties spTokenProps = loadTransferProperties("/spToken.properties");

          ObjectMapper objectMapper = new ObjectMapper();
          String body = objectMapper.readTree(resourceLoader.getResource("classpath:/JSON/spTokenInput.json").getInputStream()).toPrettyString();
          LOGGER.info("spTokenInputTemplate:" + body);
          String serviceResponse = serviceInvoker.invokeHTTPS(spTokenProps, body);
          LOGGER.info("serviceResponse:" + serviceResponse);

          List<String> jsonPaths = new ArrayList<String>();
          jsonPaths.add("$['access_token']");
          Map<String, String> values = JSONPathUtil.evaluateJSONPath(serviceResponse, jsonPaths);
          String token = values.get("$['access_token']");
          LOGGER.info("token:" + token);

          Properties spCallProps = loadTransferProperties("/sp.properties");

          Properties qammPdf = loadTransferProperties("/qammPdf.properties");

          Emails emails = new Emails();
          String tokenJwt = jwtTokenUtil.generatePDFToken(email);
          emails.setBody(bodyEmail+qammPdf.getProperty("endpoint")+archivoId+"/"+tokenJwt);
          emails.setFrom(sender);
          List<String> to = new ArrayList<>();
          to.add("vcuevas@consolidamx.com");
          to.add(email);
          emails.setTo(to);
          emails.setSubject(subject);

          String spCallInputTemplate = emails.toString();
          LOGGER.info("body:" + spCallInputTemplate);
          spCallProps.put("Authorization", "Bearer " + token);
          String serviceCallResponse = serviceInvoker.invokeHTTPS(spCallProps, spCallInputTemplate);
          LOGGER.info("serviceCallResponse:" + serviceCallResponse);

      }catch (IOException e){
          LOGGER.error("Error al enviar correo: "+e.getMessage(),e);
          throw new BusinessException(e.getMessage(),500);
      }


    }
    /**
     * Metodo que se encarga de castear una cadena Json a Objeto de java.
     * @param json String
     * @param objClass Class
     * @return Object
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object castJsonToObject(String json, Class objClass) throws  JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, objClass);
    }

    /**
     * Metodo que se encarga de castear un Objeto de java a cadena Json.
     * @param object Object
     * @return String
     * @throws Exception
     */
    public static String castObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Metodo que genera el request para guardar el json
     * @param in template del request
     * @param facturaMsDTO datos del pdf
     * @return String
     * @throws Exception
     */
    public static String generatePdfSaveRequest(InputStream in, FacturaMsDTO facturaMsDTO) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(in);
        JsonNode entrada= rootNode.get("entrada");
        JsonNode archivo = entrada.get("archivo");
        ((ObjectNode) archivo).put("nombre", facturaMsDTO.getPdf().getNombreArchivo());
        ((ObjectNode) archivo).put("contenido", facturaMsDTO.getPdf().getArchivo());
        return rootNode.toPrettyString();
    }

    /**
     * Obtiene el id del response
     * @param  json response
     * @return String
     * @throws Exception
     */
    public static int generateIdArchivoJson(String json) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("data").path("id").asInt();
    }

    @Value("${endpoints.documento.descargar}")
    private String endpoindDescargarArchivo;

    public ArchivoPDF getArchivoPdf(int idArchivo, String email) throws BusinessException {
        //Validate pdf
        if(!facturacionDao.validarArchivoFactura(idArchivo,email)){
            LOGGER.error("Error el archivo no pertenece al usuario");
            throw new BusinessException("El id del archivo no pertenece al usuario.",401);
        }

        try {
            Properties spCallProps = loadTransferProperties("/efile.properties");

            spCallProps.setProperty("endpoint", spCallProps.getProperty("endpoint")+endpoindDescargarArchivo+idArchivo);
            spCallProps.setProperty("http_method","GET");


            String serviceCallResponse = serviceInvoker.invokeHTTP(spCallProps);

            LOGGER.info("Timbre:" + serviceCallResponse);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(serviceCallResponse);
            ArchivoPDF pdf = new ArchivoPDF();
            pdf.setName(rootNode.path("data").path("nombre").asText());
            pdf.setContent(rootNode.path("data").path("contenido").asText());

            return pdf;
        }catch (IOException e){
            LOGGER.error("Error al guardar PDF: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }

    }

    public void validarDayMes() throws BusinessException {
        int day = LocalDate.now().getDayOfMonth();
        if(day>30){ //TODO quitar y poner 3
            throw new BusinessException("Solo se puede facturar en los primeros 3 días del mes.",401);
        }
    }

    private Properties loadTransferProperties(String propertiesFileName) throws BusinessException {
        String userDirectory = new File("").getAbsolutePath();
        Properties properties = new Properties();
        String execPropertiesPath = userDirectory + propertiesFileName;
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException e) {
            LOGGER.error("No se encontro el archivo de propiedades de ejecucion: "+propertiesFileName+"   " + e.getMessage(),e);
            throw new BusinessException("No se encontro el archivo de propiedades de ejecucion: "+propertiesFileName,500);
        } catch (IOException e) {
            LOGGER.error("Se produjo un error de lectura de Propiedades de ejecucion: " + propertiesFileName + "   " + e.getMessage(), e);
            throw new BusinessException("Se produjo un error de lectura de Propiedades de ejecucion: " + propertiesFileName, 500);
        }
    }
}
