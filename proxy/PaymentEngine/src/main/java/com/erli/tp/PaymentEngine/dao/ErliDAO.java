package com.erli.tp.PaymentEngine.dao;

import com.erli.tp.PaymentEngine.config.DataSourceConfig;
import com.erli.tp.PaymentEngine.model.Tabapay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
public class ErliDAO {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    Logger logger = LoggerFactory.getLogger(ErliDAO.class);

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
    }*/
}
