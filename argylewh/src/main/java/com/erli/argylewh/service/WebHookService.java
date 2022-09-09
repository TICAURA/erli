package com.erli.argylewh.service;

import com.erli.argylewh.controller.WebHookController;
import com.erli.argylewh.dao.WebHookDAO;
import com.erli.argylewh.model.ArgyleEvent;
import com.erli.argylewh.model.ArgylePayout;
import com.erli.argylewh.util.JSONPathUtil;
import com.erli.argylewh.util.PropertiesHelper;
import com.erli.argylewh.util.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Service
public class WebHookService {

    Logger logger = LoggerFactory.getLogger(WebHookService.class);

    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    PropertiesHelper propertiesHelper;

    @Autowired
    WebHookDAO webHookDAO;

    public boolean processPayOutUpdate(String payoutUpdate){
        boolean result = false;

        logger.info("Processing payout update:" + payoutUpdate);
        //TODO parse argyle def
        /**
        {
            "event": "payouts.updated",
                "name": "Payouts updated",
                "data": {
            "account": "12db5af4-fd5f-4d1f-bd98-0360df770aa8",
                    "user": "abdb5af4-fd5f-4d1f-bd10-0360df77012c",
                    "updated_from": "2019-09-10T10:00:00Z",
                    "updated_to": "2019-09-12T12:00:00Z",
                    "updated_count": 2,
                    "updated_payouts": [
            "d667a477-e252-4f8a-9518-92a51b235187",
                    "27f3cde1-1ca0-4b5a-940d-337fd33f97b5"
    ],
            "available_from": "2019-03-07T20:12:09Z",
                    "available_to": "2019-09-23T23:57:31Z",
                    "available_count": 456
        }
        }*/
        //1 Persist payment update //TODO

        //2 Extract payments ids array
        Map<String,String> jPaths = JSONPathUtil.getAllPathWithValues(payoutUpdate);
        String payoutsIDs = jPaths.get("$['data']['updated_payouts']");
        logger.info("payoutsIDs:" + payoutsIDs);
        if ((payoutsIDs != null) && (!payoutsIDs.isEmpty())){
            payoutsIDs = payoutsIDs.replace("[","");
            payoutsIDs = payoutsIDs.replace("]","");
            payoutsIDs = payoutsIDs.replace("\"","");
            logger.info("stripped payoutsIDs:" + payoutsIDs);
            String[] payoutsIDArr = payoutsIDs.split(",");

            if (payoutsIDArr == null)  {
                logger.info("Null Array of Payments:" + payoutsIDArr.toString());
                return false;
            }
            if (payoutsIDArr.length <=0)  {
                logger.info("Empty Array of Payments:"+ payoutsIDArr.toString());
                return false;
            }

            ArgyleEvent event = assembleEvent(payoutUpdate);
            String daoEventResult = webHookDAO.registraEvento(event);

            for (String payoutId : payoutsIDArr) {
                logger.info("payoutId:[" + payoutId + "]");
                if (payoutId.isEmpty()) return false;
                //if (payoutId.isBlank()) return false;

                //3 For eaxh payment update , Get detail from API
                //Get properties
                Properties servicePropertiesUP = propertiesHelper.loadProperties("payouts.properties", "Payment Dist");

                //overrride url
                String srvEndpoint = (String) servicePropertiesUP.get("srvEndpoint");
                logger.info("srvEndpoint:" + srvEndpoint);
                srvEndpoint += "/" + payoutId;
                logger.info("overrriden srvEndpoint:" + srvEndpoint);
                servicePropertiesUP.put("srvEndpoint",srvEndpoint);

                //call payout
                String resultService = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,null);
                logger.info("resultService:" + resultService);

                //Transform JSON 2 Object ArgylePayout
                ArgylePayout argylePayout = assemblePayout(resultService);

                //DAO CAll
                //TODO uncomment me
                //String daoPayoutResult = webHookDAO.registraArgylePayout(argylePayout);
                //3.1 Match with user payments




                result = true;


            }



        }
        else {
            logger.info("No payouts found.");
            return false;
        }


        return result;
    }

    private ArgylePayout assemblePayout(String jsonPayout){
        ArgylePayout argylePayout = null;

        Map<String, String> payoutsMap = JSONPathUtil.getAllPathWithValues(jsonPayout);

        String pyId = payoutsMap.get("$['id']");
        String pyDocumentId = payoutsMap.get("$['document_id']");
        String pyEmployer = payoutsMap.get("$['employer']");
        String pyStatus = payoutsMap.get("$['status']");
        String pyType = payoutsMap.get("$['type']");
        String pyPayoutDate = payoutsMap.get("$['payout_date']");
        String pyPayoutPeriodStart = payoutsMap.get("$['payout_period']['start_date']");
        String pyPayoutPeriodEnd = payoutsMap.get("$['payout_period']['end_date']");
        String pyCurrency = payoutsMap.get("$['currency']");
        String pyGrossPay = payoutsMap.get("$['gross_pay']");
        String pyReimbursements = payoutsMap.get("$['reimbursements']");
        String pyDeductions = payoutsMap.get("$['deductions']");
        String pyTaxes = payoutsMap.get("$['taxes']");
        String pyFees = payoutsMap.get("$['fees']");
        String pyNetPay = payoutsMap.get("$['net_pay']");
        String pyBonuses = payoutsMap.get("$['bonuses']");
        String pyCommission = payoutsMap.get("$['commission']");
        String pyOvertime = payoutsMap.get("$['overtime']");
        String pyHours = payoutsMap.get("$['hours']");
        String pyEmpAddLine1 = payoutsMap.get("$['employer_address']['line1']");
        String pyEmpAddLine2 = payoutsMap.get("$['employer_address']['line2']");
        String pyEmpAddCity = payoutsMap.get("$['employer_address']['city']");
        String pyEmpAddState = payoutsMap.get("$['employer_address']['state']");
        String pyEmpAddPostalCode = payoutsMap.get("$['employer_address']['postal_code']");
        String pyCountry = payoutsMap.get("$['employer_address']['country']");
        String pyAccount = payoutsMap.get("$['account']");

        logger.info("pyId:" + pyId);
        logger.info("pyDocumentId:" + pyDocumentId);
        logger.info("pyEmployer:" + pyEmployer);
        logger.info("pyStatus:" + pyStatus);
        logger.info("pyType:" + pyType);
        logger.info("pyPayoutDate:" + pyPayoutDate);
        logger.info("pyPayoutPeriodStart:" + pyPayoutPeriodStart);
        logger.info("pyPayoutPeriodEnd:" + pyPayoutPeriodEnd);
        logger.info("pyCurrency:" + pyCurrency);
        logger.info("pyCurrency:" + pyCurrency);
        logger.info("pyGrossPay:" + pyGrossPay);

        pyReimbursements = pyReimbursements == null ? "0" : pyReimbursements;
        logger.info("pyReimbursements:" + pyReimbursements);

        logger.info("pyDeductions:" + pyDeductions);

        logger.info("pyTaxes:" + pyTaxes);

        logger.info("pyFees:" + pyFees);

        logger.info("pyNetPay:" + pyNetPay);
        logger.info("pyBonuses:" + pyBonuses);
        logger.info("pyCommission:" + pyCommission);
        logger.info("pyOvertime:" + pyOvertime);
        logger.info("pyHours:" + pyHours);
        logger.info("pyEmpAddLine1:" + pyEmpAddLine1);
        logger.info("pyEmpAddLine2:" + pyEmpAddLine2);
        logger.info("pyEmpAddCity:" + pyEmpAddCity);
        logger.info("pyEmpAddState:" + pyEmpAddState);
        logger.info("pyEmpAddPostalCode:" + pyEmpAddPostalCode);
        logger.info("pyCountry:" + pyCountry);
        logger.info("pyAccount:" + pyAccount);

        argylePayout = new ArgylePayout();

        argylePayout.setPyId(pyId);
        argylePayout.setPyDocumentId(pyDocumentId);
        argylePayout.setPyEmployer(pyEmployer);
        argylePayout.setPyStatus(pyStatus);
        argylePayout.setPyType(pyType);

        LocalDate pDatetime = LocalDate.parse("22-04-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //argylePayout.setPyPayoutDate(pyPayoutDate);
        //argylePayout.setPyPayoutPeriodStart(pyPayoutPeriodStart);
        //argylePayout.setPyPayoutPeriodEnd(pyPayoutPeriodEnd);
        argylePayout.setPyPayoutDate(pDatetime);
        argylePayout.setPyPayoutPeriodStart(pDatetime);
        argylePayout.setPyPayoutPeriodEnd(pDatetime);

        argylePayout.setPyCurrency(pyCurrency);
        argylePayout.setPyGrossPay(pyGrossPay);
        argylePayout.setPyReimbursements(pyReimbursements);
        argylePayout.setPyDeductions(pyDeductions);
        argylePayout.setPyTaxes(pyTaxes);
        argylePayout.setPyFees(pyFees);
        argylePayout.setPyNetPay(pyNetPay);
        argylePayout.setPyBonuses(pyBonuses);
        argylePayout.setPyCommission(pyCommission);
        argylePayout.setPyOvertime(pyOvertime);
        argylePayout.setPyHours(pyHours);
        argylePayout.setPyEmpAddLine1(pyEmpAddLine1);
        argylePayout.setPyEmpAddLine2(pyEmpAddLine2);
        argylePayout.setPyEmpAddCity(pyEmpAddCity);
        argylePayout.setPyEmpAddState(pyEmpAddState);
        argylePayout.setPyEmpAddPostalCode(pyEmpAddPostalCode);
        argylePayout.setPyCountry(pyCountry);
        argylePayout.setPyAccount(pyAccount);
        argylePayout.setPyMetaData(jsonPayout);

        return argylePayout;
    }

    private ArgyleEvent assembleEvent(String jsonEvent){
        ArgyleEvent argyleEvent = new ArgyleEvent();
        Map<String, String> eventMap = JSONPathUtil.getAllPathWithValues(jsonEvent);

        String event =  eventMap.get("$['event']");
        String account =  eventMap.get("$['data']['account']");
        String fecha =  eventMap.get("$['data']['updated_from']");
        String user =  eventMap.get("$['data']['user']");

        argyleEvent.setEvent(event);
        argyleEvent.setAccount(account);
        argyleEvent.setFecha(fecha);
        argyleEvent.setUser(user);
        argyleEvent.setJson(jsonEvent);

        return argyleEvent;
    }
}
