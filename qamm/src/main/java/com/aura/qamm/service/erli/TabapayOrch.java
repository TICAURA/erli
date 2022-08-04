package com.aura.qamm.service.erli;

import com.aura.qamm.model.payroll.TabapayRequest;
import com.aura.qamm.util.JSONPathUtil;
import net.minidev.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TabapayOrch {

    @Autowired
    TabapayService tabapayService;

    Logger logger = LoggerFactory.getLogger(TabapayOrch.class);

    public Object executeTransaction(String requestTransaction){
        String responseTransaction = null;
        if (requestTransaction == null) return "{\"resCode\":\"-1\", \"usrMsg\":\"Null routingNumber\"}";
        if (requestTransaction.trim() == "") return "{\"resCode\":\"-2\", \"usrMsg\":\"Empty routingNumber\"}";
        //TODO VALIDATE IF JSON -3

        Map<String,String> reqTransactionJPs= JSONPathUtil.getAllPathWithValues(requestTransaction);
        logger.info("reqTransactionJPaths:" + reqTransactionJPs);

        //1 Query Bank
        logger.info("Query Bank...");
        String routingNumber = reqTransactionJPs.get("$['bank']['routingNumber']");
        logger.info("routingNumber:" + routingNumber);
        String responseQueryBank =  tabapayService.queryBank(routingNumber);
        responseTransaction = responseQueryBank;

        Map<String,String> resQueryBankPaths= JSONPathUtil.getAllPathWithValues(responseQueryBank);
        logger.info("resQueryBankPaths:" + resQueryBankPaths);
        String rtp = resQueryBankPaths.get("$['RTP']");
        logger.info("rtp:" + rtp);
        logger.info("Query Bank...[DONE]");

        if (rtp == null) return "{\"resCode\":\"-3\", \"usrMsg\":\"Empty RTP\"}";
        if (!rtp.equals("true")) return "{\"resCode\":\"-3\", \"usrMsg\":\"RTP not allowed\"}";

        //2 Create Account
        logger.info("Create Destination Account...");
        String responseCreateAccount = tabapayService.createAccount(requestTransaction);
        Map<String,String> resCreateAccountPaths = JSONPathUtil.getAllPathWithValues(responseCreateAccount);
        String accountID = resCreateAccountPaths.get("$['accountID']");
        logger.info("accountID:" + accountID);
        responseTransaction = responseCreateAccount;
        logger.info("Create Destination Account...[DONE]");

        //TODO Persist destination account or persist state of transaction?

        //3 Create Transaction
        String responseCreateTransaction = tabapayService.createTransaction(requestTransaction, accountID);
        Map<String,String> resCreateTxPaths= JSONPathUtil.getAllPathWithValues(responseCreateTransaction);
        logger.info("resCreateTxPaths:" + resCreateTxPaths);
        String transactionID = reqTransactionJPs.get("$['transactionID']");
        String errors = reqTransactionJPs.get("$['errors']");
        String sc = reqTransactionJPs.get("$['SC']");

        responseTransaction = responseCreateTransaction;

        return responseTransaction;
    }

    /**
    public Object executePayment(TabapayRequest tabapayRequest){
        //Key create onStartApp,
        //1. Create Key https://<FQDN>/v1/clients/<ClientID>/keys
        //INPUT: {
        //  "format": "ASN.1",
        //  "expiration": 365
        //}
        //OUTPUT: {
        //  "SC": 200,
        //  "EC": "0",
        //  "keyID": "TabaPay_KeyID_22-chars",
        //  "key": "Base64_Encoded_Key",
        //  "expiration": "2017-04-03T00:00:00Z"
        //}


        //2. Query Card https://<FQDN>/v1/clients/<ClientIDISO>/cards
        String inQueryCard = "{\n" +
                "  \"card\":\n" +
                "  {\n" +
                "    \"accountNumber\": \"9999999999999999\"\n" +
                "  }\n" +
                "}";
        //Invoke fuckin service 2
        String outQueryCard = "{\n" +
                "  \"SC\": 200,\n" +
                "  \"EC\": \"0\",\n" +
                "  \"card\":\n" +
                "  {\n" +
                "    \"pull\":\n" +
                "    {\n" +
                "      \"enabled\": true,\n" +
                "      \"network\": \"Visa\",\n" +
                "      \"type\": \"Debit\",\n" +
                "      \"regulated\": true,\n" +
                "      \"currency\": \"840\",\n" +
                "      \"country\": \"840\"\n" +
                "    },\n" +
                "    \"push\":\n" +
                "    {\n" +
                "      \"enabled\": true,\n" +
                "      \"network\": \"Visa\",\n" +
                "      \"type\": \"Debit\",\n" +
                "      \"regulated\": true,\n" +
                "      \"currency\": \"840\",\n" +
                "      \"country\": \"840\",\n" +
                "      \"availability\": \"Immediate\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        //2.1 if (what the hell is the condition to continue?)
        String cardPullEnabled = (String) JSONUtil.evaluateJSONPathString(outQueryCard,"$.card.pull.enabled");
        logger.info("cardPullEnabled:" + cardPullEnabled);
        // if pull.enabled=true Continue!
        //else throw "PULL not allowed by stupid card, try again duuuuude!! "
        logger.info("Validate if card is able to pull ...");
        //if (cardPullEnabled == null) return new ServiceResponse("null","Card not found on Payment Service","200");
        //if (!cardPullEnabled.equals("true")) return new ServiceResponse("null","Card not able to Pull on Payment Service","200");
        //return cardPullEnabled;

        //3. Query Bank https://<FQDN>/v1/clients/<ClientIDISO>/banks
        String inQueryBank = "{\n" +
                "  \"routingNumber\": \"999999999\"\n" +
                "}";
        //Invoke fuckin service 3
        String outQueryBank = "{\n" +
                "  \"SC\": 200,\n" +
                "  \"EC\": \"0\",\n" +
                "  \"RTP\": true\n" +
                "}";
        //3.1 if (RTP.TRUE) CONTINUE
        //else throw "RTP not allowed by stupid bank, try again duuuuude!! "
        String rtpEnabled = (String) JSONUtil.evaluateJSONPathString(outQueryBank,"$.RTP");
        logger.info("rtpEnabled:" + rtpEnabled);
        logger.info("Validate if bank allow RTP ...");
        if (rtpEnabled == null) return new ServiceResponse("null","Bank not found on Payment Service","200");
        if (!rtpEnabled.equals("true")) return new ServiceResponse("null","Bank not able to support RTP on Payment Service","200");
        //return rtpEnabled;

        //accountDestination Must be provided as input!!
        String accountDestination = "1234";
        //3.9 Retrieve Account https://<FQDN>/v1/clients/<ClientIDISO>/accounts/<AccountID>
        String inRetriveAccount = ""; //empty :)
        String outRetriveAccount = "{\n" +
                "  \"SC\": 200,\n" +
                "  \"EC\": \"0\",\n" +
                "  \"referenceID\": \"1\",\n" +
                "  \"card\":\n" +
                "  {\n" +
                "    \"last4\": \"9990\",\n" +
                "    \"expirationDate\": \"202012\"\n" +
                "  },\n" +
                "  \"owner\":\n" +
                "  {\n" +
                "    \"name\":\n" +
                "    {\n" +
                "      \"first\": \"John\",\n" +
                "      \"last\": \"Customer\"\n" +
                "    },\n" +
                "    \"address\":\n" +
                "    {\n" +
                "      \"line1\": \"465 Fairchild Drive\",\n" +
                "      \"line2\": \"Suite #222\",\n" +
                "      \"city\": \"Mountain View\",\n" +
                "      \"state\": \"CA\",\n" +
                "      \"zipcode\": \"94043\"\n" +
                "    },\n" +
                "    \"phone\":\n" +
                "    {\n" +
                "      \"number\": \"4159808222\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        //if account exists continue goto 5
        //else goto 4
        //String last4 = (String) JSONUtil.evaluateJSONPathString(outRetriveAccount,"$.card.last4");
        String last4 = null;//dummy test of create
        logger.info("last4:" + last4);
        logger.info("Account provided found ...");
        if (last4 == null) {
            logger.info("Account not found, creating Destination Account ...");
            //4. Create Account https://<FQDN>/v1/clients/<ClientIDISO>/accounts
            String inCreateAccount = "{\n" +
                    "  \"referenceID\": \"1\",\n" +
                    "  \"card\":\n" +
                    "  {\n" +
                    "    \"accountNumber\": \"9999999999999999\",\n" +
                    "    \"expirationDate\": \"202012\"\n" +
                    "  },\n" +
                    "  \"owner\":\n" +
                    "  {\n" +
                    "    \"name\":\n" +
                    "    {\n" +
                    "      \"first\": \"John\",\n" +
                    "      \"last\": \"Customer\"\n" +
                    "    },\n" +
                    "    \"address\":\n" +
                    "    {\n" +
                    "      \"line1\": \"465 Fairchild Drive\",\n" +
                    "      \"line2\": \"Suite #222\",\n" +
                    "      \"city\": \"Mountain View\",\n" +
                    "      \"state\": \"CA\",\n" +
                    "      \"zipcode\": \"94043\"\n" +
                    "    },\n" +
                    "    \"phone\":\n" +
                    "    {\n" +
                    "      \"number\": \"4159808222\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            String outCreateAccount = "{\n" +
                    "  \"SC\": 200,\n" +
                    "  \"EC\": \"0\",\n" +
                    "  \"accountID\": \"TabaPay_AccountID_22ch\"\n" +
                    "}";
            String accountDestinationNew = (String) JSONUtil.evaluateJSONPathString(outCreateAccount,"$.accountID");
            logger.info("accountDestinationNew:" + accountDestinationNew);
            if (accountDestinationNew == null) return new ServiceResponse("null","Not able to create destination account on Payment Service","200");
            accountDestination = accountDestinationNew;
        }
        //return accountDestination;


        //5 Repeat 3.9 for Origin Account
        //6 Repeat 4 for Origin Account

        //7. Create Transaction https://<FQDN>/v1/clients/<ClientIDISO>/transactions
        String inCreateTx = "{\n" +
                "  \"referenceID\": \"1\",\n" +
                "  \"type\": \"push\",\n" +
                "  \"accounts\":\n" +
                "  {\n" +
                "    \"sourceAccountID\": \"TabaPay_AccountID_22-c\",\n" +
                "    \"destinationAccountID\": \"TabaPay_AccountID_22-c\"\n" +
                "  },\n" +
                "  \"amount\": \"1.00\"\n" +
                "}\n";
        String outCreateTx = "{\n" +
                "  \"SC\": 200,\n" +
                "  \"EC\": \"0\",\n" +
                "  \"transactionID\": \"TabaPay_TransactionID_\",\n" +
                "  \"network\": \"Visa\",\n" +
                "  \"networkRC\": \"00\",\n" +
                "  \"status\": \"COMPLETED\",\n" +
                "  \"approvalCode\": \"000000\"\n" +
                "}";
        String txCompleted = (String) JSONUtil.evaluateJSONPathString(outCreateTx,"$.status");
        logger.info("txCompleted:" + txCompleted);
        logger.info("Validate if Transaction is complete ...");
        if (txCompleted == null) return new ServiceResponse("null","Transaction failed","200");
        if (!txCompleted.equals("COMPLETED")) return new ServiceResponse("null","Transaction not completed","200");
        return txCompleted;

        //8. Destroy Key?
    }*/
}
