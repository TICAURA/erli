package com.aura.qamm.service;

import com.aura.qamm.dao.PromocionDao;
import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.ImporteAnticipo;
import com.aura.qamm.dto.Pago_STP;
import com.aura.qamm.dto.SolicitudAnticipo;
import com.aura.qamm.dto.TestResultRequest;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.transformer.QuincenaTransformer;
import com.aura.qamm.util.DispersorENUM;
import com.aura.qamm.util.FileHelper;
import com.aura.qamm.util.JSONPathUtil;
import com.aura.qamm.util.PropertiesHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class DispersorService {

    @Autowired
    private QuincenaDAO quincenaDAO;
    @Autowired
    private PropertiesHelper propertiesHelper;
    @Autowired
    private FileHelper fileHelper;
    @Autowired
    private ServiceInvoker serviceInvoker;
    @Autowired
    private PromocionService promocionService;
    Logger logger = LoggerFactory.getLogger(DispersorService.class);



    public SolicitudAnticipo dispersar(ImporteAnticipo importeAnticipo){
        SolicitudAnticipo solicitudAnticipo = null;
        Integer idcolaborador = null;
        Double importeTotal = null;

        if (importeAnticipo != null){

            logger.info(importeAnticipo.toString());

            String dbJSON = quincenaDAO.confirmaSolitiudAnticipo(importeAnticipo);
            solicitudAnticipo = QuincenaTransformer.transformSolitiudAnticipo(dbJSON);
            Map<String,String> mapPathValues = JSONPathUtil.getAllPathWithValues(dbJSON);
            mapPathValues.put("$['monto']",importeAnticipo.getImporteSolicitado());
            logger.info("mapPathValues" + mapPathValues);

            String inputTemplate = fileHelper.readFile("payInput.json");
            logger.info("inputTemplate:" + inputTemplate);
            for (String jsonPath : mapPathValues.keySet()){
                inputTemplate = inputTemplate.replace(jsonPath,mapPathValues.get(jsonPath));
            }

            String inputJson = inputTemplate;

            Pago_STP pago_stp = null;

            /**
            if(solicitudAnticipo.getDispersor() == DispersorENUM.BANPAY.getId()){
                Properties payProps = propertiesHelper.loadProperties("payBP.properties", "Pago");
                String serviceResponse = serviceInvoker.invoke(payProps, inputJson);
                logger.info("serviceResponse:" + serviceResponse);

                //3. Guarda Resultado STP
                pago_stp = QuincenaTransformer.transformResponsePagoSTPBP(serviceResponse);
            }else if(solicitudAnticipo.getDispersor() == DispersorENUM.STP.getId()){
                Properties payProps = propertiesHelper.loadProperties("pay.properties", "Pago");
                String serviceResponse = serviceInvoker.invoke(payProps, inputJson);
                //String serviceResponse = serviceInvoker.invoke("http://localhost:8084/srvExecute?type=stp",inputJson);
                logger.info("serviceResponse:" + serviceResponse);

                //3. Guarda Resultado STP
                pago_stp = QuincenaTransformer.transformResponsePagoSTP(serviceResponse);
            } else{
                pago_stp = generarfalsoStp();
            }
            */

            //String jsonResult = quincenaDAO.confirmaSolitiudAnticipoSTP(solicitudAnticipo, pago_stp);
            String jsonResult = null;

            /**
            //CONFIRMAR LIQUIDACION EN PRUEBA
            if(solicitudAnticipo.getDispersor() == DispersorENUM.PRUEBA.getId()){

                TestResultRequest testResultRequest = new TestResultRequest();

                testResultRequest.setEstado("Liquidacion");
                testResultRequest.setId(pago_stp.getResultadoSTP());
                testResultRequest.setCausaDevolucion(" ");
                testResultRequest.setEmpresa(" ");
                testResultRequest.setFolioOrigen(" ");

                String resultSaveSTP = quincenaDAO.saveResponse(testResultRequest);

            }
            */
            logger.info("Confirma Solicitud STP jsonResult:" + jsonResult);
            solicitudAnticipo.setPago_stp(pago_stp);
        }
        return solicitudAnticipo;
    }

    /**
    public SolicitudAnticipo ejecutarPromocion(ImporteAnticipo importeAnticipo,String email) throws BusinessException{
        promocionService.cobrarPromocion(importeAnticipo);
        return  this.dispersar(importeAnticipo);
    }*/


    private Pago_STP generarfalsoStp(){
        Pago_STP pago_stp = new Pago_STP();

        pago_stp.setCadenaSellada("Prueba");

        pago_stp.setCadenaOriginal("Prueba");

        pago_stp.setResultadoSTP("PRUEBA_"+RandomStringUtils.randomAlphanumeric(15).toUpperCase());

        pago_stp.setResponse("Prueba");

        return pago_stp;
    }
}
