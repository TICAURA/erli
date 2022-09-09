package com.erli.argylewh.dao;

import com.erli.argylewh.config.DataSourceConfig;
import com.erli.argylewh.model.ArgyleEvent;
import com.erli.argylewh.model.ArgylePayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDate;

@Service
public class WebHookDAO {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    Logger logger = LoggerFactory.getLogger(WebHookDAO.class);

    public String registraArgylePayout(ArgylePayout payout){
        String result = "";

        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call APP_MOBILE_WEB_HOOK_PAYOUT(" +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?," +
                    //"?,?,?,?,?,?,?,?,?,?)}");
                    "?,?,?,?,?,?,?)}");
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

            //cStmt.setInt(29, userPR.getPersId());
            //cStmt.setObject(30, userPR.getOnBoardingDate());
            //cStmt.setInt(31, userPR.getClientId());

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            cStmt.close();
            logger.info("Link Payout Record resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Payout Record:" + e.getMessage());
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

    public String registraEvento(ArgyleEvent event){
        String result = "";

        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            cStmt = con.prepareCall("{? = call WHOOK_INSERTA_EVENTOS(" +
                    "?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            /**
             * FUNCTION `WHOOK_INSERTA_EVENTOS`(
             *    P_JSON_COMPLETO VARCHAR(2640)
             *  , P_FCH_EVENTO DATETIME
             *  , P_ARGYLE_ACCOUNT VARCHAR(128)
             *  , P_ARGYLE_USER VARCHAR(128)
             *  , P_TIPO_EVENTO VARCHAR(64)
             *   ) RETURNS json
             */

            LocalDate sysDate = LocalDate.now();
            cStmt.setString(2, event.getJson());
            cStmt.setObject(3, sysDate);
            cStmt.setString(4, event.getAccount());
            cStmt.setString(5, event.getUser());
            cStmt.setString(6, event.getEvent());


            cStmt.execute();
            resultJSON = cStmt.getString(1);
            cStmt.close();
            logger.info("Event Record resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("DAO Exception Event Record:" + e.getMessage());
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
}
