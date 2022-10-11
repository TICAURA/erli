package com.aura.qamm.controller;

import com.aura.qamm.model.Language;
import com.aura.qamm.model.payroll.PayDistribution;
import com.aura.qamm.model.payroll.UserCredentials;
import com.aura.qamm.model.payroll.UserPR;
import com.aura.qamm.service.erli.ErliService;
import com.aura.qamm.service.erli.TabapayOrch;
import com.aura.qamm.util.JSONPathUtil;
import io.jsonwebtoken.Claims;
import org.bouncycastle.crypto.ec.ECElGamalDecryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ErliController {

    Logger logger = LoggerFactory.getLogger(ErliController.class);

    @Autowired
    ErliService erliService;

    @Autowired
    TabapayOrch tabapayOrch;

    //ARGYLE
    @PostMapping("userTokens")
    @CrossOrigin(origins = "*")
    public UserCredentials getUserTokens(@RequestBody UserPR userPR, @RequestAttribute("claims") Claims claims){
        if (claims != null) {
            logger.info("[ERLI] userTokens");
            logger.info("claims:" + claims);
            //String sClientId = (String) claims.get("clientId");
            //String sPersonId = (String) claims.get("personId");
            String sCollaboratorId = (String) claims.get("collaboratorId");
            userPR.setPersId(Integer.parseInt(sCollaboratorId));
            //QuincenaDAO quincenaDAO = new QuincenaDAO();
            //String valor = quincenaDAO.getTerminos(terminos.getParamTerminos()); //TCAM, TCAQ
            //terminos.setValor(valor);
            //return terminos;

            UserCredentials userCredentials = erliService.getUserCredentials(userPR);
            return userCredentials;
        }
        else return null;
    }

    //ARGYLE
    @PostMapping("payDistributionConfigs")
    @CrossOrigin(origins = "*")
    //public String payDistributionConfigs(Object paymentDist){
    public String payDistributionConfigs(@RequestBody PayDistribution paymentDist, @RequestAttribute("claims") Claims claims){
        if ((paymentDist != null) && (claims != null)) {
            logger.info("paymentDist:" + paymentDist);
            String sClientId = (String) claims.get("clientId");
            String sPersonId = (String) claims.get("personId");
            String sCollaboratorId = (String) claims.get("collaboratorId");
            //String commision = paymentDist.getCommision();

            logger.info("sClientId:" + sClientId);
            logger.info("sPersonId:" + sPersonId);
            logger.info("sCollaboratorId:" + sCollaboratorId);
            //logger.info("commision:" + commision);

            logger.info("[ERLI] payDistributionConfigs");
            paymentDist.setUser(sCollaboratorId);
            String result  = erliService.paymentDistribution(paymentDist);
            return result;
        }
        else return null;
    }

    /**
    @GetMapping("queryCard")
    @CrossOrigin(origins = "*")
    public String queryCard(@RequestBody CardAccount cardAccount){
        logger.info("queryCard");
        logger.info("card:" + cardAccount.toString());
        if (cardAccount.getCardNumber() != null) {
            String result = erliService.queryCard(cardAccount);
            return result;
        }
        else return "{\"Error\":\"Card Not Provided\"}";
    }*/

    /**
    @GetMapping("queryKey")
    @CrossOrigin(origins = "*")
    public String queryKey(@RequestBody Object key){
        logger.info("queryKey");
        logger.info("key:" + key.toString());
        //if (cardAccount.getCardNumber() != null) {
            String result = erliService.queryKey();
            return result;
        //}
        //else return "{\"Error\":\"Card Not Provided\"}";
    }*/

    /**
    @PostMapping("createAccount")
    @CrossOrigin(origins = "*")
    public String createAccount(@RequestBody Object account){
        logger.info("createAccount");
        logger.info("account:" + account.toString());
        //if (cardAccount.getCardNumber() != null) {
            String result = erliService.createAccount();
            return result;
        //}
        //else return "{\"Error\":\"Card Not Provided\"}";
    }*/

    /**
    @PostMapping("createTransaction")
    @CrossOrigin(origins = "*")
    public String createTransaction(@RequestBody Object transaction){
        logger.info("createTransaction");
        logger.info("transaction:" + transaction.toString());
        //if (cardAccount.getCardNumber() != null) {
        String result = erliService.createTransaction();
        return result;
        //}
        //else return "{\"Error\":\"Card Not Provided\"}";
    }*/

    //ARGYLE record
    @PostMapping("registraLink")
    @CrossOrigin(origins = "*")
    public String registraLink(@RequestBody UserPR userPR, @RequestAttribute("username") String email,
                               @RequestAttribute("claims") Claims claims){
        logger.info("registraLink...");

        String syncTimeJSON = erliService.syncTime("P_SYNCTIME");
        int syncTimei = 180 * 1000;
        logger.info("Def syncTimei = " + syncTimei);

        Map<String,String> jsonSyncTimeMap = JSONPathUtil.getAllPathWithValues(syncTimeJSON);
        logger.info("jsonSyncTimeMap:" + jsonSyncTimeMap);

        try {
            String syncTime = jsonSyncTimeMap.get("$['valor']");
            logger.info("syncTime:" + syncTime);
            syncTimei = Integer.parseInt(syncTime);
        }
        catch (Exception e){
            logger.error("Exception on syncTime Conversion : " + e.getMessage());
        }

        try {
            logger.info("Estimated syncTimei = " + syncTimei);
            Thread.sleep(syncTimei);
            logger.info("Ending Delay Expired syncTimei :" + syncTimei);
        }
        catch (Exception e){
            Thread.currentThread().interrupt();
            logger.info("Delay error");
        }
        //logger.info("registraLink");
        logger.info("userPR:" + userPR.toString());
        logger.info("claims:" + claims);
        logger.info("user:" + userPR.getUser());

        if (userPR != null) {
            String sClientId = (String) claims.get("clientId");
            String sPersonId = (String) claims.get("personId");
            String sOnBoardDate = (String) claims.get("onBoardDate");
            userPR.setClientId(Integer.parseInt(sClientId));
            userPR.setPersId(Integer.parseInt(sPersonId));
            //LocalDate pOnBoarding = LocalDate.parse("20-03-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate pOnBoarding = LocalDate.parse(sOnBoardDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            userPR.setOnBoardingDate(pOnBoarding);

            userPR.setEmail(email);

            String result = erliService.registraLink(userPR);
            return result;
        }
        else return "{\"resCode\":\"-1\", \"usrMsg\":\"Payroll user not provided\"}";
    }

    @PostMapping("registraPayment")
    @CrossOrigin(origins = "*")
    public String registraPayment(@RequestBody Object paymentRequest){
        logger.info("registraPayment");

        //logger.info("registraPayment");
        logger.info("paymentRequest:" + paymentRequest.toString());
        if (paymentRequest != null) {
            String result = erliService.registraPayment(paymentRequest);
            return result;
        }
        else return "{\"resCode\":\"-1\", \"usrMsg\":\"Payment info not provided\"}";
    }

    //TABAPAY TRANSACTION (QUICK TEST)
    @PostMapping("executeTransaction")
    @CrossOrigin(origins = "*")
    public Object executeTransaction(@RequestBody String txJRequest) {
        logger.info("executeTransaction txJRequest:" + "[" + txJRequest + "]");
        //Expected JSON
        //{"bank":{"routingNumber":"01"}}
        return tabapayOrch.executeTransaction(txJRequest);
    }

    //ARGYLE GET PAY ALLOCS
    @RequestMapping("payAllocs/{accountId}")
    @CrossOrigin(origins = "*")
    public Object payAllocs(@PathVariable("accountId") String accountId) {
        logger.info("getpayAllocs accountId:" + "[" + accountId + "]");

        //PayAllocID
        String res = erliService.getPayAllocations(accountId);
        logger.info(" res payAllocs accountId:" + "[" + res + "]");

        return res;
    }

    //TABAPAY TEST
    @PostMapping("paymentExecute")
    @CrossOrigin(origins = "*")
    public Object payExec(@RequestBody String paymenObject) {
        logger.info("paymentExecute :" + "[" + paymenObject + "]");

        String res = erliService.paymentExec(paymenObject);
        logger.info("response paymentExecute:" + "[" + res + "]");

        return "{\"estatus\":\"ok\"}";
    }

    @PostMapping("testSign")
    @CrossOrigin(origins = "*")
    public String sign(){

        String unsigned = "que onda prro 3";
        logger.info("cipher ini");
        String signed = erliService.textSign(unsigned);
        logger.info("cipher end B64 [" + signed + "]");

        /**
        logger.info("uncipher ini");
        //String original = erliService.testUnSign(signed,null);
        String original = erliService.testUnSign(signed);
        logger.info("uncipher end");
        //logger.info("res [" + res + "]");
        */


        return signed;
    }

    @PostMapping("recDeferred")
    @CrossOrigin(origins = "*")
    public String recDeferred(@RequestAttribute("claims") Claims claims, @RequestBody Language language){
        logger.info("resRecordDef claims:" + claims);
        logger.info("resRecordDef language:" + language);

        String sCollaboratorId = (String) claims.get("collaboratorId");
        logger.info("sCollaboratorId:" + sCollaboratorId);
        Long lCollaboratorId = Long.parseLong(sCollaboratorId);
        logger.info("lCollaboratorId:" + lCollaboratorId);

        String resRecordDef = erliService.recordDeferred(lCollaboratorId, language.getLang());
        logger.info("resRecordDef:" + resRecordDef);

        return resRecordDef;
    }

    @PostMapping("acumulator")
    @CrossOrigin(origins = "*")
    public String acumulator(@RequestAttribute("claims") Claims claims, @RequestBody Language language){
        logger.info("acumulator claims:" + claims);
        logger.info("resRecordDef language:" + language);

        String sCollaboratorId = (String) claims.get("collaboratorId");
        logger.info("sCollaboratorId:" + sCollaboratorId);
        Long lCollaboratorId = Long.parseLong(sCollaboratorId);
        logger.info("lCollaboratorId:" + lCollaboratorId);
        String resAcumulator = erliService.acumulator(lCollaboratorId, language.getLang());
        logger.info("resAcumulator:" + resAcumulator);

        return resAcumulator;
    }

}
