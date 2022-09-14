package com.aura.qamm.service.erli;

import com.aura.qamm.dao.ErliDAO;
import com.aura.qamm.model.payment.CardAccount;
import com.aura.qamm.model.payroll.*;
import com.aura.qamm.util.JSONPathUtil;
import com.aura.qamm.service.ServiceInvoker;
import com.aura.qamm.util.PropertiesHelper;
import com.aura.qamm.util.TPSecurity;
import com.aura.qamm.util.tabapay.TabaPayHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ErliService {

    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    TabapayService tabapayService;

    @Autowired
    ErliDAO erliDAO;

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    TabaPayHelper tabapayHelper;

    Logger logger = LoggerFactory.getLogger(ErliService.class);

    public UserCredentials getUserCredentials(UserPR userPR){
        UserCredentials userCredentials = null;

        String resArgyleUser = erliDAO.getUserErli(userPR.getPersId());
        Map<String,String> resArgyleUserJP = JSONPathUtil.getAllPathWithValues(resArgyleUser);
        logger.info("resArgyleUserJP:" + resArgyleUserJP);
        String userArgyle = resArgyleUserJP.get("$['argyleUserId']");

        Properties servicePropertiesUT = propertiesHelper.loadProperties("user-tokens.properties", "User Tokens");
        //String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUT,"{\"user\":\"" + userPR.getUser() + "\"}");
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUT,"{\"user\":\"" + userArgyle + "\"}");
        Map<String,String> jPaths = JSONPathUtil.getAllPathWithValues(result);
        logger.info("jPaths:" + jPaths);
        logger.info("result:" + result);
        userCredentials = new UserCredentials();
        userCredentials.setAccess(jPaths.get("$['access']"));
        userCredentials.setRefresh(jPaths.get("$['refresh']"));
        return userCredentials;
    }

    public String getUserProfile(UserPR userPR){
        Properties servicePropertiesUP = propertiesHelper.loadProperties("user-profile.properties", "Cards");
        String srvEndpoint = (String) servicePropertiesUP.get("srvEndpoint");
        srvEndpoint = srvEndpoint + userPR.getUser();
        servicePropertiesUP.put("srvEndpoint",srvEndpoint);
        logger.info("overrriden srvEndpoint:" + srvEndpoint);
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,"");
        return result;
    }

    public String queryCard(CardAccount cardAccount){
        String result = new String();

        result = tabapayService.queryCard(cardAccount);
        logger.info("result:" + result);

        result = tabapayService.queryCard(cardAccount);

        return result;
    }

    public String queryKey(){
        String result = new String();

        result = tabapayService.queryKey();
        logger.info("result:" + result);

        return result;
    }

    public String createAccount(){
        String result = new String();

        result = tabapayService.createAccount(null);
        logger.info("result:" + result);

        return result;
    }

    /**
    public String createTransaction(){
        String result = new String();

        result = tabapayService.createTransaction(null);
        logger.info("result:" + result);

        return result;
    }*/

    public String paymentDistribution(PayDistribution paymentDist){
        Properties servicePropertiesUP = propertiesHelper.loadProperties("payment-dist.properties", "Payment Dist");

        Integer collaboratorId = Integer.parseInt(paymentDist.getUser());
        String bankAccount = erliDAO.bankAccount(collaboratorId);
        //String bankAccount = erliDAO.bankAccount(400000562);
        Map<String,String> jPaths = JSONPathUtil.getAllPathWithValues(bankAccount);
        String sbanco = jPaths.get("$['banco']");
        //String saccountTypeLink = jPaths.get("$['accountTypeLink']");
        //String saccountNumberLink = jPaths.get("$['accountNumberLink']");
        //String sroutingNumberLink = jPaths.get("$['routingNumberLink']");

        String saccountTypeTP = jPaths.get("$['accountTypeLink']");
        String saccountNumberTP = jPaths.get("$['accountNumberLink']");
        String sroutingNumberTP = jPaths.get("$['routingNumberLink']");

        String saccountTypeLink = jPaths.get("$['accountTypeERLI']");
        String saccountNumberLink = jPaths.get("$['accountNumberERLI']");
        String sroutingNumberLink = jPaths.get("$['routingNumberERLI']");

        logger.info("sbanco:" + sbanco);
        logger.info("saccountTypeLink:" + saccountTypeLink);
        logger.info("saccountNumberLink:" + saccountNumberLink);
        logger.info("sroutingNumberLink:" + sroutingNumberLink);

        String firstName = jPaths.get("$['firstName']");
        String lastName = jPaths.get("$['lastName']");
        String telCountry = jPaths.get("$['countryCode']");
        String telNumber = jPaths.get("$['phoneNumber']");


        logger.info("firstName:" + firstName);
        logger.info("lastName:" + lastName);
        logger.info("telCountry:" + telCountry);
        logger.info("telNumber:" + telNumber);

        //logger.info("bankAccount::" + bankAccount);

        //Assemble allocation body
        String body = "{\n" +
                "    \"bank_account\": {\n" +
                //"        \"bank_name\": \"Banco del Patito\",\n" +
                //"        \"bank_name\": \"\",\n" +
                //"        \"routing_number\": \"084101234\",\n" +
                //"        \"account_number\": \"9483746361234\",\n" +
                //"        \"account_type\": \"checking\"\n" +
                "        \"routing_number\": \"" + sroutingNumberLink + "\",\n" +
                "        \"account_number\": \"" + saccountNumberLink + "\",\n" +
                "        \"account_type\": \"" + saccountTypeLink + "\"\n" +
                "    },\n" +
                "    \"default_allocation_type\": \"amount\",\n" +
                //"    \"percent_allocation\": {\n" +
                //"            \"value\": \"30\",\n" +
                //"            \"min_value\": \"10\",\n" +
                //"            \"max_value\": \"40\"\n" +
                //"    },\n" +
                "    \"amount_allocation\": {\n" +
                "            \"value\":     \"" + paymentDist.getAmount() + "\",\n" +
                "            \"min_value\": \""  + paymentDist.getAmount()  + "\",\n" +
                "            \"max_value\": \"" + paymentDist.getAmount() + "\"\n" +
                "    },\n" +
                "    \"entire_allocation\": false,\n" +
                //"    \"allow_add_allocation\": true,\n" +
                //"    \"enable_suggestions\": true,\n" +
                "    \"allow_editing\": false\n" +
                "}";
        logger.info("body:" + body);
        String result = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP,body);

        //TODO CHECK ALLOCATION IF SUCCESS BEFORE PERSIST
        //TODO Replace 399
        PaymentRequestAmount paymentRequestAmount = new PaymentRequestAmount();
        paymentRequestAmount.setRequestedAmount(paymentDist.getAmount());
        //paymentRequestAmount.setCommisionAmount(paymentDist.getCommision()); //TODO GET COMISION FROM FRONT
        paymentRequestAmount.setCommisionAmount("399"); //OLD
        int total = new Double(paymentDist.getAmount()).intValue() + 399;
        //Integer total = Integer.parseInt(paymentDist.getAmount()) + 399;
        //paymentRequestAmount.setTotalAmount(total.toString()); //TODO CALCULATE ME
        paymentRequestAmount.setTotalAmount(String.valueOf(total)); //TODO CALCULATE ME
        paymentRequestAmount.setCollaboratorId(Integer.parseInt(paymentDist.getUser()));
        paymentRequestAmount.setPromotionId(null);

        logger.info("paymentRequestAmount:" + paymentRequestAmount.toString() );

        String dbConfirmPayment = erliDAO.confirmPayment(paymentRequestAmount);
        Map<String,String> dbConfirmPaymentJPs = JSONPathUtil.getAllPathWithValues(dbConfirmPayment);
        logger.info("dbConfirmPaymentJPs:" + dbConfirmPaymentJPs.toString());

        String idErliTransaction = dbConfirmPaymentJPs.get("$['id_anti']");


        //TODO CHECK ALLOCATION PERIST IF SUCCESS BEFORE DISPERSOR CALL

        //INVOKE TABAPAY
        Random rand = new Random();
        int upperbound = 10000;
        int int_random = rand.nextInt(upperbound);
        //saccountNumberTP = "100000000";

        //String centsAmountString = paymentDist.getAmount()  + ".00";
        //String centsAmountString = paymentDist.getAmount()  + "00";
        String centsAmountString = paymentDist.getAmount();
        //Integer centsAmount = Integer.parseInt(centsAmountString)+100;
        Integer centsAmount =  new Double(centsAmountString).intValue();
        String toBeSigned = int_random + ":" +
                "RTP_OUT:" +
                "200248573666320" + ":" + //merchantAccount
                saccountNumberTP + ":" +//destinationAccount
                sroutingNumberTP + ":" + //destination Routing Number
                centsAmountString; //amount in cents
        String signed = this.textSign(toBeSigned);
        logger.info("toBeSigned:" + toBeSigned);
        logger.info("signed:" + signed);

        //String tpBody = "{\"referenceID\":\"" + int_random + "\",\"type\":\"push\",\"accounts\":{\"sourceAccountID\":\"D0smSJAUAQyMUacBkVpXdA\"," +
        //String tpBody = "{\"referenceID\":\"" + int_random + "\",\"type\":\"push\",\"accounts\":{\"sourceAccountID\":\"DAAIBnYUiKb6PcekmjQVzw\"," +
        String tpBody = "{\"idErliTransaction\":\"" + int_random + "\",\"type\":\"push\",\"accounts\":{\"sourceAccountID\":\"DAAIBnYUiKb6PcekmjQVzw\"," +
                "\"destinationAccount\":" +
                //"{\"bank\":{\"routingNumber\": \"" + sroutingNumberTP + "\",\"accountNumber\":\"" + saccountNumberTP + "\",\"accountType\":\"" + saccountTypeTP + "\"}," +
                "{\"bank\":{\"routingNumber\": \"" + sroutingNumberTP + "\",\"accountNumber\":\"" + saccountNumberTP + "\",\"accountType\":\"S\"}," +
                "\"owner\":{\"name\":{\"first\":\"" + firstName + "\",\"middle\":\"\",\"last\":\"" + lastName  + "\",\"suffix\": \"\"}," +
                "\"address\":{\"line1\":\"\",\"line2\":\"\",\"city\":\"\"," +
                "\"state\":\"\",\"zipcode\":\"\",\"country\":\"\"},\"phone\":" +
                "{\"countryCode\":\"" + telCountry.replace("+","") + "\",\"number\":\"" + telNumber + "\"}}}},\"amount\":\"" +
                 paymentDist.getAmount() + "\",\"achOptions\": \"R\"" + "," +
                "\"rtp\":{\"apiKey\":\"4ac39e5ce45461c569b53922c85f827742ba40ca2565724c16\", \"signature\":\"" + signed + "\"}" +
                "}";
        /**
        String apiProps = "exec-transaction.properties";
        Properties servicePropertiesEntities = propertiesHelper.loadProperties(
                apiProps, "Create Transaction");
        String resultPay = serviceInvoker.invokeBasicAuthBody(servicePropertiesEntities,tpBody);
         */
        String apiProps = "paymentEngine.properties";
        Properties servicePropertiesEntities = propertiesHelper.loadProperties(
                apiProps, "Create Delegated Transaction");
        String resultPay = serviceInvoker.invokeBasicHTTPAuthBody(servicePropertiesEntities,tpBody);

        Map<String,String> resultPayJPs = JSONPathUtil.getAllPathWithValues(resultPay);
        String idTabapay = resultPayJPs.get("$['transactionID']");

        //TODO PERIST DISPERSE RESULT
        Tabapay tabapay = new Tabapay();
        tabapay.setTransactionID(idTabapay); //TODO SET ME with transactionID
        tabapay.setRequestBody(tpBody);
        tabapay.setErliTransaction(idErliTransaction);
        tabapay.setResponseBody(resultPay);
        logger.info("tabapay:" + tabapay.toString());
        erliDAO.confirmPaymentTP(tabapay);

        //String result = "OKAS";
        return result;
    }

    public String registraLink(UserPR userPR){
        String result = new String();

        //Get account
        //String account = getLocatedEntity("accounts.properties", "Account",
        //        "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]");

        //Get profile
        logger.info("profile ****** INI");
        String profile = getLocatedEntity("profiles.properties", "Profile",
                "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]");
        logger.info("profile ****** END");
        //logger.info("profile:" + profile + ".");
        //Extract profile data
        if (profile != null && !profile.equals("[]") && !profile.equals("")) {
            Map<String, String> profileMap = JSONPathUtil.getAllPathWithValues(profile);
            String pId = profileMap.get("$['id']");
            String pAccount = profileMap.get("$['account']");
            String pEmployer = profileMap.get("$['employer']");
            String pFirstName = profileMap.get("$['first_name']");
            String pLastName = profileMap.get("$['last_name']");
            String pFullName = profileMap.get("$['full_name']");
            String pEmail = profileMap.get("$['email']");
            String pPhoneNumber = profileMap.get("$['phone_number']");

            String sBirthDate = profileMap.get("$['birth_date']");
            LocalDate pBirthDate = LocalDate.parse("22-04-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            String pPictureURL = profileMap.get("$['picture_url']");
            String pSsn = profileMap.get("$['ssn']");
            String pMaritalStatus = profileMap.get("$['marital_status']");
            String pGender = profileMap.get("$['gender']");

            logger.info("pId:" + pId);
            logger.info("pAccount:" + pAccount);
            logger.info("pEmployer:" + pEmployer);
            logger.info("pFirstName:" + pFirstName);
            logger.info("pLastName:" + pLastName);
            logger.info("pFullName:" + pFullName);
            logger.info("pEmail:" + pEmail);
            logger.info("pPhoneNumber:" + pPhoneNumber);
            logger.info("pBirthDate:" + pBirthDate);
            logger.info("pPictureURL:" + pPictureURL);
            logger.info("pSsn:" + pSsn);
            logger.info("pMaritalStatus:" + pMaritalStatus);
            logger.info("pGender:" + pGender);

            ArgyleProfile argyleProfile = new ArgyleProfile();
            argyleProfile.setpId(pId);
            argyleProfile.setpAccount(pAccount);
            argyleProfile.setpEmployer(pEmployer);
            argyleProfile.setpFirstName(pFirstName);
            argyleProfile.setpLastName(pLastName);
            argyleProfile.setpFullName(pFullName);
            argyleProfile.setpEmail(pEmail);
            argyleProfile.setpPhoneNumber(pPhoneNumber);
            argyleProfile.setpBirthDate(pBirthDate);
            argyleProfile.setpSsn(pSsn);
            argyleProfile.setpMaritalStatus(pMaritalStatus);
            argyleProfile.setpGender(pGender);
            argyleProfile.setpMetaData(profile);
            argyleProfile.setpJson(profile);

            String profileResult = erliDAO.registraArgyleProfile(argyleProfile, userPR);
            logger.info("profileResult Insert Result:" + profileResult);

        }
        else logger.error("Profiles not Found");


        //Get employments
        logger.info("employment ****** INI");
        String employments = getLocatedEntity("employments.properties", "Employer",
                "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]");
        logger.info("employment ****** END");
        //Extract employer data
        if (employments != null && !employments.equals("[]") && !employments.equals("")) {
            Map<String, String> employmentMap = JSONPathUtil.getAllPathWithValues(employments);

            String eId = employmentMap.get("$['id']");
            String eEmployer = employmentMap.get("$['employer']");
            String eStatus = employmentMap.get("$['status']");
            String eType = employmentMap.get("$['type']");
            String eJobTitle = employmentMap.get("$['job_title']");
            String eHireDatetime = employmentMap.get("$['hire_datetime']");
            String eTerminationDatetime = employmentMap.get("$['termination_datetime']");
            String eTerminationReason = employmentMap.get("$['termination_reason']");
            String eBasePayAmount = employmentMap.get("$['base_pay']['amount']");
            String eBasePayPeriod = employmentMap.get("$['base_pay']['period']");
            String eBasePayCurrency = employmentMap.get("$['base_pay']['currency']");
            String ePayCycle = employmentMap.get("$['pay_cycle']");
            String ePlatformIdsEmployeeId = employmentMap.get("$['platform_ids']['employee_id']");
            String ePlatformIdsPositionId = employmentMap.get("$['platform_ids']['position_id']");
            String ePlatformIdsUserId = employmentMap.get("$['platform_ids']['platform_user_id']");
            String eAccount = employmentMap.get("$['account']");

            logger.info("eId:" + eId);
            logger.info("eEmployer:" + eEmployer);
            logger.info("eStatus:" + eStatus);
            logger.info("eType:" + eType);
            logger.info("eJobTitle:" + eJobTitle);
            logger.info("eHireDatetime:" + eHireDatetime);
            logger.info("eTerminationDatetime:" + eTerminationDatetime);
            logger.info("eTerminationReason:" + eTerminationReason);
            logger.info("eBasePayAmount:" + eBasePayAmount);
            logger.info("eBasePayPeriod:" + eBasePayPeriod);
            logger.info("eBasePayCurrency:" + eBasePayCurrency);
            logger.info("ePayCycle:" + ePayCycle);
            logger.info("ePlatformIdsEmployeeId:" + ePlatformIdsEmployeeId);
            logger.info("ePlatformIdsPositionId:" + ePlatformIdsPositionId);
            logger.info("ePlatformIdsUserId:" + ePlatformIdsUserId);
            logger.info("eAccount:" + eAccount);

            ArgyleEmployment argyleEmployment = new ArgyleEmployment();
            argyleEmployment.seteId(eId);
            argyleEmployment.seteEmployer(eEmployer);
            argyleEmployment.seteStatus(eStatus);
            argyleEmployment.seteType(eType);
            argyleEmployment.seteJobTitle(eJobTitle);
            LocalDate hireDatetime = LocalDate.parse("22-04-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            //argyleEmployment.seteHireDatetime(eHireDatetime);
            argyleEmployment.seteHireDatetime(hireDatetime);
            argyleEmployment.seteTerminationDatetime(eTerminationDatetime);
            argyleEmployment.seteTerminationReason(eTerminationReason);
            argyleEmployment.seteBasePayAmount(eBasePayAmount);
            argyleEmployment.seteBasePayPeriod(eBasePayPeriod);
            argyleEmployment.seteBasePayCurrency(eBasePayCurrency);
            argyleEmployment.setePayCycle(ePayCycle);
            argyleEmployment.setePlatformIdsEmployeeId(ePlatformIdsEmployeeId);
            argyleEmployment.setePlatformIdsPositionId(ePlatformIdsPositionId);
            argyleEmployment.setePlatformIdsUserId(ePlatformIdsUserId);

            argyleEmployment.seteMetaData(employments);
            argyleEmployment.seteAccount(eAccount);
            argyleEmployment.seteJson(employments);

            String employment = erliDAO.registraArgyleEmployment(argyleEmployment, userPR);
            logger.info("employment Insert Result:" + employment);
        }
        else logger.error("Employment not Found");

        //Get payouts
        logger.info("payout ****** INI");
        String payouts = getLocatedEntity("payouts.properties", "Payout",
                "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]");
        logger.info("payout ****** END");
        //Extract payouts data
        if (payouts != null && !payouts.equals("[]") && !payouts.equals("")) {
            Map<String, String> payoutsMap = JSONPathUtil.getAllPathWithValues(payouts);

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

            ArgylePayout argylePayout = new ArgylePayout();

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
            argylePayout.setPyMetaData(payouts);

            String payoutsResult = erliDAO.registraArgylePayout(argylePayout, userPR);
            logger.info("payouts Insert Result:" + payoutsResult);
        }
        else logger.error("Payouts not Found");


        //Get pay allocations
        logger.info("payallocs ****** INI");
        String payAlloc = getLocatedEntity("payAllocs.properties", "PayAllocs",
                "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]");
        logger.info("payallocs ****** END");

        //Extract pay allocation data
        if (payAlloc != null && !payAlloc.equals("[]") && !payAlloc.equals("")) {
            Map<String, String> payAllocMap = JSONPathUtil.getAllPathWithValues(payAlloc);

            String pALLId = payAllocMap.get("$['id']");
            String pALLRoutingNumber = payAllocMap.get("$['bank_account']['routing_number']");
            String pALLAccountNumber = payAllocMap.get("$['bank_account']['account_number']");
            String pALLAccountType = payAllocMap.get("$['bank_account']['account_type']");

            logger.info("pALLId:" + pALLId);
            logger.info("pALLRoutingNumber:" + pALLRoutingNumber);
            logger.info("pALLAccountNumber:" + pALLAccountNumber);
            logger.info("pALLAccountType:" + pALLAccountType);

            ArgylePayAllocation argylePayAllocation = new ArgylePayAllocation();
            argylePayAllocation.setAccountNumber(Integer.parseInt(pALLAccountNumber));
            argylePayAllocation.setAccountType(pALLAccountType);
            argylePayAllocation.setRoutingNumber(Integer.parseInt(pALLRoutingNumber));
            argylePayAllocation.setPersId(userPR.getPersId());
            argylePayAllocation.setJson(payAlloc);

            String profileResult = erliDAO.registraArgylePayAllocations(argylePayAllocation, userPR);
            logger.info("PayAllocs Result Insert Result:" + profileResult);

        }
        else logger.error("PayAllocs not Found");


        //TODO Update in DB cross reference of
        String user = userPR.getUser();
        String collaborator = userPR.getCollaborator();

        result = "{\"resCode\":\"1\", \"usrMsg\":\"Success user link\"}";

        return result;
    }

    /**
     * Locates entity on List, if appears returs individual item in Detail API
     * @param apiProps          Properteies Element
     * @param apiRef            Reference of Entity
     * @param pathOfIdLocator   Match Expression on List
     * @return                  Detail Element
     */
    private String getLocatedEntity(String apiProps, String apiRef, String pathOfIdLocator){
        String result = new String();
        int page = 0;
        String hasNext = ""; //STARTER
        String valPathOfIdLocator = "[]";//STARTER
        //String pathOfIdLocator = "$['results'][?(@.account == \"" + userPR.getAccount() + "\")]";
        String pathOfNext = "$['next']";

        List<String> listEntityPaths = new ArrayList<String>();
        listEntityPaths.add(pathOfIdLocator);
        listEntityPaths.add(pathOfNext);

        //Get all entitys from Argyle
        Properties servicePropertiesEntities = propertiesHelper.loadProperties(
                apiProps, apiRef + "-s");

        while (hasNext != null){
            logger.info("hasNext is not null");
            if (!valPathOfIdLocator.equals("[]")) break;
            logger.info("\n***** Page:" + page);
            //OVERRIDE ENDPOINT AFTER 1st Attempt
            if (page > 0) servicePropertiesEntities.setProperty("srvEndpoint",hasNext);

            String entities = serviceInvoker.invokeBasicAuthBody(servicePropertiesEntities,"");
            logger.info("apiRef entities:" + entities);

            Map<String,String> evaluatedPaths = JSONPathUtil.evaluateJSONPath(entities,listEntityPaths);
            valPathOfIdLocator = evaluatedPaths.get(pathOfIdLocator);
            hasNext = evaluatedPaths.get(pathOfNext);

            logger.info("hasNext:" + hasNext);
            logger.info("pathOfIdLocator:" + pathOfIdLocator);
            logger.info("valPathOfIdLocator:" + valPathOfIdLocator);
            page++;

        }

        //GET DETAILS IF USER FOUND
        //Get detail entity from Argyle
        if (valPathOfIdLocator != null && !valPathOfIdLocator.equals("[]")) {
            logger.info(apiRef + " entityLocated:" + valPathOfIdLocator);
            Map<String, String> entityLocatedPaths = JSONPathUtil.getAllPathWithValues(valPathOfIdLocator);
            logger.info(apiRef + " entityLocatedPaths:" + entityLocatedPaths);
            String entityLocatedId = entityLocatedPaths.get("$[0]['id']"); //Unique element in array
            logger.info(apiRef + " entityLocatedId:" + entityLocatedId);
            Properties servicePropertiesUP2 = propertiesHelper.loadProperties(
                    apiProps, apiRef);
            String entityEndpoint = (String) servicePropertiesUP2.get("srvEndpoint");
            entityEndpoint = entityEndpoint + "/" + entityLocatedId;
            servicePropertiesUP2.setProperty("srvEndpoint", entityEndpoint);

            String entitySpecific = serviceInvoker.invokeBasicAuthBody(servicePropertiesUP2, "");
            logger.info(apiRef + " entitySpecific:" + entitySpecific);
            result = entitySpecific;
        }

        return result;
    }

    //public String registraPayment(PaymentRequest paymentRequest){
    public String
    registraPayment(Object paymentRequest){
        String result = new String();

        //TODO Update pay in DB

        result = "{\"resCode\":\"1\", \"usrMsg\":\"Success record of payment\"}";

        return result;
    }

    public String getPayAllocations(String idAccount){
        //String result = "{\"res\"=\"OK\"}";
        logger.info("payAllocation ****** START");
        String payalloc = getLocatedEntity("pay-allocations.properties", "Pay Allocations",
                "$['results'][?(@.account == \"" + idAccount + "\")]");
        logger.info("payAllocation ****** END");

        return payalloc;
    }

    public String paymentExec(Object pay){
        String apiProps = "exec-transaction.properties";
        Properties servicePropertiesEntities = propertiesHelper.loadProperties(
                apiProps, "Create Transaction");
        String resultPay = serviceInvoker.invokeBasicAuthBody(servicePropertiesEntities,pay.toString());
        return  resultPay;
    }

    //TODO ADD DISPERSE EXEC
    public String textSign(String tobeSignedString){
        //return tabapayHelper.signRequest(tobeSignedString);

        String signed = null;
        /**
        try {
            File privateKey = new File("priv.pem");
            InputStream targetStream = new FileInputStream(privateKey);
            signed = tabapayHelper.encriptarFirma("priv.pem", tobeSignedString);
            //signed = tabapayHelper.encriptarFirma(targetStream, tobeSignedString);
            //signed = tabapayHelper.encriptarFirma(targetStream, tobeSignedString);
        }
        catch (Exception e){
            logger.error("Sign Exception:" + e.getMessage());
            e.printStackTrace();
        }*/
        try{
            //PublicKey publicKey = TPSecurity.getPublicKey("hola tu","/Users/soniko/Downloads/4ac39e5ce45461c569b53922c85f827742ba40ca2565724c16_privatekey.txt");
            //PrivateKey privateKey = TPSecurity.getPublicKey("hola tu","/Users/soniko/Downloads/key2.pem");
            //PrivateKey privateKey = TPSecurity.getPrivateKey("/Users/soniko/Downloads/key2.pem");
            PrivateKey privateKey = TPSecurity.getPrivateKey("key2.pem");
            //String encrypted = TPSecurity.signString("ora si, hola tu",privateKey);
            signed = TPSecurity.signString(tobeSignedString,privateKey);

            //PublicKey publicKey = TPSecurity.getPublicKey("/Users/soniko/Downloads/4ac39e5ce45461c569b53922c85f827742ba40ca2565724c16_publickey.txt");

            logger.info("privateKey:" + privateKey);
        }
        catch (Exception e){
            logger.error("Sign Exception:" + e.getMessage());
            e.printStackTrace();
        }
        return signed;
    }

    public String testUnSign(String signed){
        return tabapayHelper.unsignRequest(signed);
    }

}

