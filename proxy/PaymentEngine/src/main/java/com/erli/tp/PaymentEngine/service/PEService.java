package com.erli.tp.PaymentEngine.service;

import com.erli.tp.PaymentEngine.dao.ErliDAO;
import com.erli.tp.PaymentEngine.model.Tabapay;
import com.erli.tp.PaymentEngine.util.JSONPathUtil;
import com.erli.tp.PaymentEngine.util.PropertiesHelper;
import com.erli.tp.PaymentEngine.util.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class PEService {

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private ServiceInvoker serviceInvoker;

    @Autowired
    ErliDAO erliDAO;

    Logger logger = LoggerFactory.getLogger(PEService.class);

    //public String executeTPPayment(String tpBody, String idErliTransaction){
    public String executeTPPayment(String tpBody){
        String apiProps = "exec-transaction.properties";
        Properties servicePropertiesEntities = propertiesHelper.loadProperties(
                apiProps, "Create Transaction");

        String resultPay = serviceInvoker.invokeBasicAuthBody(servicePropertiesEntities,tpBody);
        /**
        Map<String,String> resultPayJPs = JSONPathUtil.getAllPathWithValues(resultPay);
        String idTabapay = resultPayJPs.get("$['transactionID']");


        //TODO PERIST DISPERSE RESULT
        Tabapay tabapay = new Tabapay();
        tabapay.setTransactionID(idTabapay); //TODO SET ME with transactionID
        tabapay.setRequestBody(tpBody);
        tabapay.setErliTransaction(idErliTransaction);
        tabapay.setResponseBody(resultPay);
        logger.info("tabapay:" + tabapay.toString());
        erliDAO.confirmPaymentTP(tabapay);*/

        return resultPay;
    }
}
