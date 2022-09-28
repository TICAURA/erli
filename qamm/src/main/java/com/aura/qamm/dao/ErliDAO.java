package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.model.payroll.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Map;

@Service
public class ErliDAO {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    Logger logger = LoggerFactory.getLogger(ErliDAO.class);

    //public String registraArgyleProfile(Map<Integer, ArgyleEntity> profileEntityMap){
    public String registraArgyleProfile(ArgyleProfile profile, UserPR userPR){
        String result = "";

        //String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_LINK_PROFILE(" +
                    "?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            //cStmt = setFields(profileEntityMap, cStmt);
            cStmt.setString(2, profile.getpId());
            cStmt.setString(3, profile.getpAccount());
            cStmt.setString(4, profile.getpEmployer());
            cStmt.setString(5, profile.getpFirstName());
            cStmt.setString(6, profile.getpLastName());
            cStmt.setString(7, profile.getpFullName());
            cStmt.setString(8, profile.getpEmail());
            cStmt.setString(9, profile.getpPhoneNumber());
            cStmt.setString(10, null); //profile.getpBirthDate() FIXME convert me!
            cStmt.setString(11, profile.getpPictureURL());
            cStmt.setInt(12, userPR.getPersId()); //P_pers_id ???
            logger.info("12 userPR.getPersId()" + userPR.getPersId());
            cStmt.setObject(13, userPR.getOnBoardingDate()); //P_fch_ingreso ???
            logger.info("13 userPR.getOnBoardingDate()" + userPR.getOnBoardingDate());
            cStmt.setInt(14, userPR.getClientId()); //P_clnt_id ???
            logger.info("14 userPR.getClientId()" + userPR.getClientId());
            cStmt.setString(15, profile.getpSsn());
            cStmt.setString(16, profile.getpMaritalStatus());
            cStmt.setString(17, profile.getpGender());
            cStmt.setString(18, profile.getpMetaData());
            cStmt.setString(19, userPR.getUser()); //new 19
            cStmt.setString(20, userPR.getEmail()); //new 20 email
            cStmt.setString(21, profile.getpMetaData());

            //cStmt.setString(22, userPR.getUser()); //user

            cStmt.execute();
            result = cStmt.getString(1);
            cStmt.close();
            logger.info("Link Profile Record result:" + result);
        }
        catch (Exception e){
            logger.error("DAO Exception Link Profile Record:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return result;
    }

    public String registraArgyleEmployment(ArgyleEmployment employment, UserPR userPR){
        String result = "";

        //String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_LINK_EMPLOYMENT(" +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            //cStmt = setFields(profileEntityMap, cStmt);
            cStmt.setString(2, employment.geteId());
            cStmt.setString(3, employment.geteEmployer());
            cStmt.setString(4, employment.geteStatus());
            cStmt.setString(5, employment.geteType());
            cStmt.setString(6, employment.geteJobTitle());
            cStmt.setObject(7, employment.geteHireDatetime()); //employment.geteHireDatetime() //FIXME Format ME
            cStmt.setString(8, null); //employment.geteTerminationDatetime() //FIXME Format ME
            cStmt.setString(9, employment.geteTerminationReason());
            cStmt.setString(10, employment.geteBasePayAmount()); //OK
            cStmt.setString(11, employment.geteBasePayPeriod());
            cStmt.setString(12, employment.geteBasePayCurrency());
            cStmt.setString(13, employment.getePayCycle());
            cStmt.setString(14, employment.getePlatformIdsEmployeeId());
            cStmt.setString(15, employment.getePlatformIdsPositionId());
            cStmt.setString(16, employment.getePlatformIdsUserId());
            cStmt.setString(17, employment.geteMetaData());
            cStmt.setString(18, employment.geteAccount());
            cStmt.setString(19, employment.geteJson());

            cStmt.setInt(20, userPR.getPersId());
            cStmt.setObject(21, userPR.getOnBoardingDate());
            cStmt.setInt(22, userPR.getClientId());

            cStmt.execute();
            result = cStmt.getString(1);
            cStmt.close();
            logger.info("Link Employment Record result:" + result);
        }
        catch (Exception e){
            logger.error("DAO Exception Link Employment Record:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return result;
    }

    public String registraArgylePayout(ArgylePayout payout, UserPR userPR){
        String result = "";

        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_LINK_PAYOUT(" +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setString(2, payout.getPyId());
            cStmt.setString(3, payout.getPyDocumentId());
            cStmt.setString(4, payout.getPyEmployer());
            cStmt.setString(5, payout.getPyStatus());
            cStmt.setString(6, payout.getPyType());
            cStmt.setObject(7, payout.getPyPayoutDate()); //payout.getPyPayoutDate() //FIXME Format ME
            cStmt.setObject(8, payout.getPyPayoutPeriodStart()); //payout.getPyPayoutPeriodStart()) //FIXME Format ME
            cStmt.setObject(9, payout.getPyPayoutPeriodStart()); //payout.getPyPayoutPeriodEnd() //FIXME Format ME
            cStmt.setString(10, payout.getPyCurrency());

            cStmt.setString(11, payout.getPyGrossPay());
            cStmt.setString(12, payout.getPyReimbursements());
            cStmt.setString(13, payout.getPyDeductions());
            cStmt.setString(14, payout.getPyTaxes());
            cStmt.setString(15, payout.getPyFees());
            cStmt.setString(16, payout.getPyNetPay());
            cStmt.setString(17, payout.getPyBonuses());
            cStmt.setString(18, payout.getPyCommission());
            cStmt.setString(19, payout.getPyOvertime());

            cStmt.setString(20, payout.getPyHours());
            cStmt.setString(21, payout.getPyEmpAddLine1());
            cStmt.setString(22, payout.getPyEmpAddLine2());
            cStmt.setString(23, payout.getPyEmpAddCity());
            cStmt.setString(24, payout.getPyEmpAddState());
            cStmt.setString(25, payout.getPyEmpAddPostalCode());
            cStmt.setString(26, payout.getPyCountry());
            cStmt.setString(27, payout.getPyMetaData());
            cStmt.setString(28, payout.getPyAccount());

            cStmt.setInt(29, userPR.getPersId());
            cStmt.setObject(30, userPR.getOnBoardingDate());
            cStmt.setInt(31, userPR.getClientId());

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            cStmt.close();
            logger.info("Link Payout Record resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Payout Link Record:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return result;
    }

    //TODO TEST ME
    public String registraArgylePayAllocations(ArgylePayAllocation payAllocation, UserPR userPR){
        String result = "";

        //String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_LINK_BANK_ACCOUNT_MI(" +
                    "?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            //cStmt = setFields(profileEntityMap, cStmt);
            cStmt.setInt(2, payAllocation.getRoutingNumber());
            cStmt.setLong(3, payAllocation.getAccountNumber());
            cStmt.setString(4, payAllocation.getAccountType());
            cStmt.setInt(5, payAllocation.getPersId());
            cStmt.setString(6, null); //TODO Add JSON

            cStmt.execute();
            result = cStmt.getString(1);
            cStmt.close();
            logger.info("Link PayAllocation Record result:" + result);
        }
        catch (Exception e){
            logger.error("DAO Exception Link PayAllocation Record:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Exception:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Exception:" + eCon.getMessage());
            }
        }

        return result;
    }

    public String bankAccount(Integer idCollaborator){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_CUENTA_BANCARIA_MI(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setInt(2, idCollaborator);

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            cStmt.close();
            logger.info("Bank Account resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Bank Account:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }

    public String getUserErli(Integer idCollaborator){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call GET_ARGYLE_USER_4TOKEN(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setInt(2, idCollaborator);

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            cStmt.close();
            logger.info("Bank Account resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Bank Account:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }

    /**
     * Confirm payment Request (after allocation)
     */
    public String confirmPayment(PaymentRequestAmount paymentRequestCF){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            cStmt = con.prepareCall("{? = call APP_MOBILE_CONFIRMA_SOLICITUD_ANTICIPO_MI(?,?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, paymentRequestCF.getCollaboratorId());

            logger.info("getRequestedAmount" + Double.parseDouble(paymentRequestCF.getRequestedAmount()));
            logger.info("getCommisionAmount" + Double.parseDouble(paymentRequestCF.getCommisionAmount()));
            logger.info("getTotalAmount" + Double.parseDouble(paymentRequestCF.getTotalAmount()));

            cStmt.setDouble(3, Double.parseDouble(paymentRequestCF.getRequestedAmount()));
            cStmt.setDouble(4, Double.parseDouble(paymentRequestCF.getCommisionAmount()));
            cStmt.setDouble(5, Double.parseDouble(paymentRequestCF.getTotalAmount()));
            if(paymentRequestCF.getPromotionAmount()!=null){
                cStmt.setDouble(6, paymentRequestCF.getPromotionAmount());
            }else{cStmt.setNull(6, Types.DECIMAL);}
            if(paymentRequestCF.getPromotionId()!=null){
                cStmt.setInt(7, paymentRequestCF.getPromotionId());
            }else{cStmt.setNull(7, Types.INTEGER);}
            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Confim Payment resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Confim Payment:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }

    /**
     * Save payment (after disperse)
     */
    public String confirmPaymentTP(Tabapay tabapay){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_API_PAGO_SOLICITA_TABAPAY(?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setInt(2, Integer.parseInt(tabapay.getErliTransaction()));
            cStmt.setString(3, tabapay.getRequestBody());
            //cStmt.setString(4, pago_stp.getCadenaSellada());
            cStmt.setString(4, tabapay.getTransactionID());
            cStmt.setString(5, tabapay.getResponseBody());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Confim Payment resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Confim Payment:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }
    /**
    public String confirmPaymentTP(Tabapay tabapay){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_API_PAGO_SOLICITA_TABAPAY(?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setInt(2, Integer.parseInt(tabapay.getErliTransaction()));
            cStmt.setString(3, tabapay.getRequestBody());
            //cStmt.setString(4, pago_stp.getCadenaSellada());
            cStmt.setString(4, tabapay.getTransactionID());
            cStmt.setString(5, tabapay.getResponseBody());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Confim Payment resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Confim Payment:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }
    */
    public String recordDeferred(Long cveColaborador, String lang){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_DIFIERE_REGISTRO_MI(?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setLong(2, cveColaborador);
            cStmt.setString(3, lang);
            //cStmt.setString(4, pago_stp.getCadenaSellada());
            //cStmt.setString(4, tabapay.getTransactionID());
            //cStmt.setString(5, tabapay.getResponseBody());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Record Deferred resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Record Deferred Exception:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }

    public String acumulator(Long cveColaborador, String lang){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_ACUMULADOS_MI(?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setLong(2, cveColaborador);
            cStmt.setString(3, lang);
            //cStmt.setString(4, pago_stp.getCadenaSellada());
            //cStmt.setString(4, tabapay.getTransactionID());
            //cStmt.setString(5, tabapay.getResponseBody());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Acumulator resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Acumulator Exception:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Closure Sentence Excepcion:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Closure Connection Excepcion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }

    private CallableStatement setFields(Map<Integer, ArgyleEntity> fieldMap, CallableStatement callableStatement){
        logger.info("fieldMap.size():" + fieldMap.size());
        for (int i = 2; i <= fieldMap.size(); i++){
            ArgyleEntity argyleEntity = fieldMap.get(i);
            logger.info(i + " argyleEntity:" + argyleEntity.toString());
            try {
                switch (argyleEntity.getType()) {
                    case "String":
                        callableStatement.setString(i, argyleEntity.getValue());
                        break;
                    case "Date":
                        callableStatement.setDate(i, null);
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e){
                logger.info("Exception " + e.getMessage());
                e.printStackTrace();
            }
        }
        return callableStatement;
    }

}
