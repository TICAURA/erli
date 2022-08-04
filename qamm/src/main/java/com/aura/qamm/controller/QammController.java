package com.aura.qamm.controller;


import java.util.List;


import com.aura.qamm.config.JwtTokenUtil;
import com.aura.qamm.dto.facturacion.ArchivoPDF;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.service.*;
import com.aura.qamm.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.Colaborador;
import com.aura.qamm.dto.CuentaBancaria;
import com.aura.qamm.dto.EmailReset;
import com.aura.qamm.dto.EventoLog;
import com.aura.qamm.dto.ImporteAnticipo;
import com.aura.qamm.dto.Movimiento;
import com.aura.qamm.dto.Pago_STP;
import com.aura.qamm.dto.Password;
import com.aura.qamm.dto.QUser;
import com.aura.qamm.dto.ResetPWResponse;
import com.aura.qamm.dto.ResultExecution;
import com.aura.qamm.dto.SolicitudAnticipo;
import com.aura.qamm.dto.Terminos;
import com.aura.qamm.transformer.QuincenaTransformer;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class QammController {

    Logger logger = LoggerFactory.getLogger(QammController.class);

    
    @Autowired
    private QuincenaDAO quincenaDAO;
    @Autowired
    private PropertiesHelper propertiesHelper;
    @Autowired
    private FileHelper fileHelper;
    @Autowired
    private ServiceInvoker serviceInvoker;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FacturacionService facturacionService;
    @Autowired
    private DispersorService dispersorService;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordService passwordService;


    @PostMapping("saldoDisponible")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> getSaldoDisponible(@RequestAttribute("username") String email){
        try {
            int idColaborador = authService.getClaveColaborador(email);
            logger.info("Consulta saldo colaborador =" + email + " ID : "+idColaborador);
            String dbJson = quincenaDAO.consultaSaldo(idColaborador);
            Colaborador colaborador = QuincenaTransformer.transformConsultaSaldo(dbJson);

            return new ResponseEntity<Object>(colaborador,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("comisionAnticipo")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> getComisionAnticipo(@RequestAttribute("username") String email,@RequestBody ImporteAnticipo importeAnticipo) {
        try{

            ImporteAnticipo retImporteDesglose = null;
            int idColaborador = authService.getClaveColaborador(email);
            logger.info("Comision anticipo para colaborador = " + importeAnticipo.toString());

            if (importeAnticipo != null &&
                    importeAnticipo.getImporteSolicitado() !=null &&
                        !importeAnticipo.getImporteSolicitado().isEmpty()) {

                String dbJSON = quincenaDAO.consultaComisionAnticipo(idColaborador,
                        Double.parseDouble(importeAnticipo.getImporteSolicitado()));
                retImporteDesglose = QuincenaTransformer.transformComisionAnticipo(dbJSON);
                retImporteDesglose.setIdColaborador(idColaborador);
                retImporteDesglose.setImporteSolicitado(importeAnticipo.getImporteSolicitado());

            }

            return new ResponseEntity<Object>(retImporteDesglose,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("cuentaBancaria")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> getCuentaBancaria(@RequestAttribute("username") String email){
        try{
            CuentaBancaria cuentaBancaria = null;
            int idColaborador = authService.getClaveColaborador(email);

            logger.info("Buscando cuenta bancaria para colaborador=" + email + " ID : "+idColaborador);
            String dbJSON = quincenaDAO.consultaCuentaBancaria(idColaborador);
            logger.info("dbJSON:" + dbJSON);
            cuentaBancaria = QuincenaTransformer.transformCuentaBancaria(dbJSON);
            cuentaBancaria.setIdColaborador(idColaborador);

             return new ResponseEntity<Object>(cuentaBancaria,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("confirmaSolicitudAnticipo")
    @CrossOrigin(origins = "*")
    public Object confirmaSolicitudAnticipo(@RequestBody ImporteAnticipo importeAnticipo, @RequestAttribute("username") String email){

        try{
            int idColaborador = authService.getClaveColaborador(email);
            importeAnticipo.setIdColaborador(idColaborador);
            importeAnticipo.setMontoPromocion(null);
            importeAnticipo.setIdPromocion(null);
            return dispersorService.dispersar(importeAnticipo);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("movimientos")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> getMovimientos(@RequestAttribute("username") String email){


        try{

            int idColaborador = authService.getClaveColaborador(email);

            List<Movimiento> movimientos = quincenaDAO.getMovimientos(idColaborador);

            return new ResponseEntity<>(movimientos,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("movimientosH")
    @CrossOrigin(origins = "*")
    public Object getMovimientosH(@RequestAttribute("username") String email){
        try{

            int idColaborador = authService.getClaveColaborador(email);

            List<Movimiento> movimientos = quincenaDAO.getMovimientosH(idColaborador);

            return new ResponseEntity<>(movimientos,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("registra")
    @CrossOrigin(origins = "*")
    public Object registra(@RequestBody QUser qUser){
        try{
            QUser rqUser = null;

            logger.info(qUser.toString());

            if (qUser != null){

                qUser = passwordService.hashPasswordRegistra(qUser);
                String dbJSON = quincenaDAO.registra(qUser);
                rqUser = QuincenaTransformer.transformUseReg(dbJSON);
            }
            return new ResponseEntity<Object>(rqUser,HttpStatus.OK);
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("validaToken")
    @CrossOrigin(origins = "*")
    public Object validaToken(@RequestAttribute("username") String email){

        try{
            logger.info("[QAMM] validaToken !!! ...");

            String dbJsonUser = quincenaDAO.autentica(email);
            QUser qUser = QuincenaTransformer.transformQUser(dbJsonUser);
            qUser.setEmail(email);
            qUser.setPassword(null);


            logger.info("[QAMM] validaToken.");
            return new ResponseEntity<Object>(qUser,HttpStatus.OK);
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("terminos")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> getTerminos(@RequestBody Terminos terminos){
        try{
            if (terminos != null) {
                logger.info("[QAMM] Terminos.");
                //QuincenaDAO quincenaDAO = new QuincenaDAO();
                String valor = quincenaDAO.getTerminos(terminos.getParamTerminos()); //TCAM, TCAQ
                terminos.setValor(valor);
                return new ResponseEntity<Object>(terminos,HttpStatus.OK);
            }
            else return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("actualizaPW")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> actualizaPW(@RequestBody Password password, @RequestAttribute("username") String email){
        try{
            if (password != null) {

                int idColaborador = authService.getClaveColaborador(email);
                logger.info("[QAMM] Actualiza password para colaborador "+ email + " ID : "+ idColaborador);

                passwordService.updatePassword(password, idColaborador,email);

                return new ResponseEntity<>("{\"mensajeApp\":\"Éxito al cambiar la contraseña.\"}",HttpStatus.OK);
            }
            else return new ResponseEntity<Object>("{\"error\":\"Datos Invalidos\"}",HttpStatus.BAD_REQUEST);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("resetPW")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> resetPW(@RequestBody EmailReset emailReset){
        try {
            ResetPWResponse response = new ResetPWResponse();
            if (emailReset == null
                    && StringUtils.isBlank(emailReset.getEmail())) {
                response.setMsgErrorEsp("El email es requerido");
                response.setMsgErrorEng("Email is required");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(passwordService.recuperarPassword(emailReset.getEmail()), HttpStatus.OK);
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PostMapping("crearEventoLog")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> crearEventoLog(@RequestBody EventoLog eventoLog,@RequestAttribute("username") String email) {
        try{
                int idColaborador = authService.getClaveColaborador(email);
                logger.info("/*** Prueba evento log :: ");
                ResetPWResponse response = new ResetPWResponse();
                eventoLog.setIdColaborador(idColaborador+"");
                if (StringUtils.isBlank(eventoLog.getIdColaborador()) ||
                        StringUtils.isBlank(eventoLog.getTipo())) {
                    response.setMsgErrorEsp("Faltan parámetros requeridos");
                    response.setMsgErrorEng("Required parameters are missing");

                    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
                }

                String responseBD = quincenaDAO.crearEventoLog(eventoLog);

                if (responseBD.equals("exito")) {
                    response.setMsgExitoEsp("Evento creado con éxito");
                    response.setMsgExitoEng("Event created successfully");
                } else {
                    response.setMsgErrorEsp("Error al crear evento");
                    response.setMsgErrorEng("Error creating event");
                }

    	 return new ResponseEntity<>(response,HttpStatus.OK);
    }catch(BusinessException e){
        return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
    }catch (Exception e){
        logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
        return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    



    @GetMapping("/factura")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> generarFactura( @RequestAttribute("username") String email){

        try{
            boolean bool = facturacionService.generarFactura(email,1);
            return new ResponseEntity<>("{\"facturado\":\""+bool+"\"}", HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/factura/pdf/{archivoId}/{jwtToken}",produces = MediaType.APPLICATION_PDF_VALUE)
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<Object> descargarArchivo(@PathVariable("archivoId") int archivoId, @PathVariable("jwtToken")String token){
        try{
            String email = jwtTokenUtil.getUsernameFromToken(token);
            ArchivoPDF pdf = facturacionService.getArchivoPdf(archivoId,email);
            byte[] decodedBytes = Base64.decodeBase64(pdf.getContent());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + pdf.getName())
                    .body(decodedBytes);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
