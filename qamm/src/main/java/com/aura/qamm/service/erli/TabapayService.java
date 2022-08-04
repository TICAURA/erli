package com.aura.qamm.service.erli;

import com.aura.qamm.model.payment.Bank;
import com.aura.qamm.model.payment.CardAccount;
import com.aura.qamm.model.payment.SrvResponse;
import com.aura.qamm.model.payroll.EncryptedItem;
import com.aura.qamm.service.ServiceInvoker;
import com.aura.qamm.util.JSONPathUtil;
import com.aura.qamm.util.PropertiesHelper;
import com.aura.qamm.util.tabapay.TabaPayHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class TabapayService {
    @Autowired
    PropertiesHelper propertiesHelper;
    @Autowired
    ServiceInvoker serviceInvoker;
    @Autowired
    TabaPayHelper tabaPayHelper;

    @Value( "${tabapay.defKey}" )
    private String tabapay_defKey;
    @Value( "${tabapay.idKey}" )
    private String tabapay_idKey;

    Logger logger = LoggerFactory.getLogger(TabapayService.class);

    public String queryCard(CardAccount cardAccount){
        Properties servicePropertiesUP = propertiesHelper.loadProperties("tp_card.properties", "Query Card");

        //String encCard = tabaPayHelper.encryptText(card.getCardNumber());
        String cardData = cardAccount.getCardNumber() + "|" + cardAccount.getExpirationDate() + "|" + cardAccount.getSecurityCode();
        //String cardData = cardAccount.getCardNumber() + "|" + cardAccount.getExpirationDate() + "|";
        //EncryptedItem encCardData = tabaPayHelper.encryptText(cardData);
        //String encCardData = tabaPayHelper.encryptText(cardData);
        //String encCardData = tabaPayHelper.encryptText("4000056655665556|202204|123");
        String encCardData = null;
        String body = "{\"card\":" +
                "{" +
                "\"keyId\":\"" + tabapay_idKey + "\"," +
                "\"data\":\"" + encCardData + "\"" +
                "}}";
        //String encBody = tabaPayHelper.encryptText(body);
        logger.info("cardData:" + cardData);
        logger.info("encCard:" + encCardData);
        logger.info("body:" + body);
        //logger.info("encBody:" + encItem.getEncString());
        //String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,encBody);
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,body);
        logger.info("result:" + result);
        //String result = serviceInvoker.invokeTP(servicePropertiesUP, null);
        return result;
    }

    //public String queryBank(Bank bank){
    public String queryBank(String routingNumber){
        logger.info("queryBank routingNumber" + "[" + routingNumber + "]");
        if (routingNumber == null) return "{\"resCode\":\"-1\", \"usrMsg\":\"routingNumber\"}";
        if (routingNumber.trim() == "") return "{\"resCode\":\"-2\", \"usrMsg\":\"routingNumber\"}";
        //TODO Validate number return "{\"resCode\":\"-3\", \"usrMsg\":\"routingNumber\"}";

        Properties servicePropertiesUP = propertiesHelper.loadProperties("tp_bank.properties", "Query Bank");
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,
                //"{\n" +
               // "    \"routingNumber\": \"" + bank.getRoutingNumber() + "\"\n" +
               // "  }\n");
                //TODO Generate Request Template
                "{\"routingNumber\":\"" + routingNumber + "\"}");
        return result;
    }

    public String queryKey(){
        Properties servicePropertiesQK = propertiesHelper.loadProperties("tp_key.properties", "Query Bank");
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesQK,
                "");
        return result;
    }

    public String createAccount(String accountRequest){
        Properties servicePropertiesCA = propertiesHelper.loadProperties("tp_create_account.properties", "Create Account");
        //String endCardData = tabaPayHelper.encryptText("4000056655665556|202204|123");

        Map<String,String> accountRequestPaths = JSONPathUtil.getAllPathWithValues(accountRequest);

        String referenceID = accountRequestPaths.get("$['referenceID']");
        String routingNumber = accountRequestPaths.get("$['bank']['routingNumber']");
        String accountNumber = accountRequestPaths.get("$['bank']['accountNumber']");
        String accountType = accountRequestPaths.get("$['bank']['accountType']");

        String firstName = accountRequestPaths.get("$['owner']['name']['first']");
        String lastName = accountRequestPaths.get("$['owner']['name']['last']");
        String addLine1 = accountRequestPaths.get("$['owner']['address']['line1']");
        String addLine2 = accountRequestPaths.get("$['owner']['address']['line2']");
        String addCity = accountRequestPaths.get("$['owner']['address']['city']");
        String addState = accountRequestPaths.get("$['owner']['address']['state']");
        String addZipcode = accountRequestPaths.get("$['owner']['address']['zipcode']");
        String phone = accountRequestPaths.get("$['owner']['phone']['number']");



        String body = "{" +
                "\"referenceID\":\"" + referenceID + "\"," + //TODO Generate me in DB

                //"\"card\":" +
                //"{" +
                //"\"keyID\":\"" + tabapay_idKey + "\"," +
                //"\"data\":\"" + endCardData + "\"" +
                //"}," +
                /**
                "\"bank\":" +
                "{" +
                "\"routingNumber\":\"" + "011000206" + "\"," +
                "\"accountNumber\":\"" + "11234567890" + "\"," +
                "\"accountType\":\"" + "S" + "\"" +
                "}," +*/

                "\"bank\":" +
                "{" +
                "\"routingNumber\":\"" + routingNumber + "\"," +
                "\"accountNumber\":\"" + accountNumber + "\"," +
                "\"accountType\":\"" + accountType + "\"" +
                "}," +

                "\"owner\":" +
                "{" +
                "\"name\":" +
                "{" +
                "\"first\":\"" + firstName + "\"," +
                "\"last\":\"" + lastName + "\"" +
                "}," +
                "\"address\":" +
                "{" +
                "\"line1\":\"" + addLine1 + "\"," +
                "\"line2\":\"" + addLine2 + "\"," +
                "\"city\":\"" + addCity + "\"," +
                "\"state\":\"" + addState + "\"," +
                "\"zipcode\":\"" + addZipcode + "\"" +
                "}," +
                "\"phone\":" +
                "{" +
                "\"number\":\"" + phone + "\"" +
                "}" +
                "}" +
                //" \"rtp\":{\"apiKey\",\"APIKeyFromCBWPortal\", \"signature\":\"" + signed + "\"}" +
                "}";

                /**
                "\"owner\":" +
                "{" +
                "\"name\":" +
                "{" +
                "\"first\":\"John\"," +
                "\"last\":\"Customer\"" +
                "}," +
                "\"address\":" +
                "{" +
                "\"line1\":\"465 Fairchild Drive\"," +
                "\"line2\":\"Suite #222\"," +
                "\"city\":\"Mountain View\"," +
                "\"state\":\"CA\"," +
                "\"zipcode\":\"94043\"" +
                "}," +
                "\"phone\":" +
                "{" +
                "\"number\":\"4159808222\"" +
                "}" +
                "}" +
                "}";
                 */
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesCA, body);
        return result;
    }

    public String createTransaction(String transactionRequest, String destinationAccount){
        Properties servicePropertiesCA = propertiesHelper.loadProperties("tp_create_transaction.properties", "Create Account");
        //String endCardData = tabaPayHelper.encryptText("4000056655665556|202204|123");

        Map<String,String> transactionRequestPaths = JSONPathUtil.getAllPathWithValues(transactionRequest);
        String referenceID = transactionRequestPaths.get("$['transaction']['referenceID']");
        String type = transactionRequestPaths.get("$['transaction']['type']");
        String sourceAccount = transactionRequestPaths.get("$['transaction']['sourceAccountID']");
        //String destinationAccount = transactionRequestPaths.get("$['transaction']['destinationAccountID']");
        String amount = transactionRequestPaths.get("$['transaction']['amount']");
        String ach = transactionRequestPaths.get("$['transaction']['achOptions']");

        /**
        String signed = referenceID + ":" +
                "RTP_OUT:" +
                "MerchantAccountNumber:" + //merchantAccount
                destinationAccount ":" +//destinationAccount
                routingNumber ":" +//destination Routing Number
                ""; //amount in cents*/

        String body = "{" +
                "\"referenceID\":\"" + referenceID + "\"," + //TODO Generate me in DB
                "\"type\":\"" + type + "\"," +
                "\"accounts\":" +
                "{" +
                "\"sourceAccountID\":\"" + sourceAccount + "\"," + //TODO GET FROM Properties ERLI-Related with tabapay
                "\"destinationAccountID\":\"" + destinationAccount + "\"" +
                "}," +
                "\"amount\":\"" + amount + "\"," +
                "\"achOptions\":\"" + ach + "\"," +
                //" \"rtp\":{\"apiKey\",\"APIKeyFromCBWPortal\", \"signature\":\"" + signed + "\"}" +
                "}";

        /**
        String body = "{" +
                "\"referenceID\":\"" + referenceID + "\"," + //TODO Generate me in DB
                "\"type\":\"" + type + "\"," +
                "\"accounts\":" +
                "{" +
                //"\"sourceAccountID\":\"D9c0T5TVkYKU9owjcD-OFQ\"," +
                "\"sourceAccountID\":\"D0smSJAUAQyMUacBkVpXdA\"," +
                "\"destinationAccountID\":\"i980XynEEaYvVxBd-zRFTw\"" +
                "}," +
                "\"amount\":\"1.00\"," +
                "\"achOptions\":\"R\"" +
                "}";*/
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesCA, body);
        return result;
    }

}
