package com.aura.qamm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.aura.qamm.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.qamm.config.DataSourceConfig;

@Service
public class QuincenaDAO {
    Logger logger = LoggerFactory.getLogger(QuincenaDAO.class);

    private static final String EXITO_EVENTO = "exito";
    private static final String ERROR_EVENTO = "error";
    
    @Autowired
    private DataSourceConfig dataSourceConfig;

    public String consultaSaldo(Integer idColaborador){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
                    //"jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_SALDO_ACTUAL(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, idColaborador);
            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();
            //cStmt.close();
            logger.info("Consulta Saldo resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Consulta Saldo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String consultaComisionAnticipo(Integer idColaborador, Double importeSolicitado){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_COMISION_ANTICIPO(?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, idColaborador);
            cStmt.setDouble(3, importeSolicitado);
            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Consulta Comision Anticipo resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Comision Anticipo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String consultaCuentaBancaria(Integer idColaborador){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_CUENTA_BANCARIA(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, idColaborador);
            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Consulta CuentaBancaria resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Comision Anticipo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String confirmaSolitiudAnticipo(ImporteAnticipo importeAnticipo){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            //cStmt = con.prepareCall("{? = call APP_MOBILE_CONFIRMA_SOLICITUD_ANTICIPO(?,?,?,?)}");
            //cStmt = con.prepareCall("{? = call APP_MOBILE_CONFIRMA_SOLICITUD_ANTICIPO_2(?,?,?,?,?,?)}");
            cStmt = con.prepareCall("{? = call APP_MOBILE_CONFIRMA_SOLICITUD_ANTICIPO_MI(?,?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, importeAnticipo.getIdColaborador());
            cStmt.setDouble(3, Double.parseDouble(importeAnticipo.getImporteSolicitado()));
            cStmt.setDouble(4, Double.parseDouble(importeAnticipo.getImporteComision()));
            cStmt.setDouble(5, Double.parseDouble(importeAnticipo.getImporteTotal()));
            if(importeAnticipo.getMontoPromocion()!=null){
            cStmt.setDouble(6, importeAnticipo.getMontoPromocion());
            }else{cStmt.setNull(6, Types.DECIMAL);}
            if(importeAnticipo.getIdPromocion()!=null){
            cStmt.setInt(7, importeAnticipo.getIdPromocion());
            }else{cStmt.setNull(7, Types.INTEGER);}
            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();
            //cStmt.close();
            logger.info("Confirma Solitiud Anticipo resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Comision Anticipo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String consultaMovimientos(Integer idColaborador){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_MOVIMIENTOS(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setInt(2, idColaborador);
            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();
            //cStmt.close();
            logger.info("Consulta Movimientos resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Comision Anticipo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String autentica(String email){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        logger.info("[DAO] [Auth] email:" + email);
        //logger.info("password" + password);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_AUTENTICAR2HASH(?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setString(2, email);
            //cStmt.setString(3, password);
            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();
            //con.clo
            logger.info("Autentica resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Autentica:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String registra(QUser qUser){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;

        logger.info("qUser:" + qUser);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_REGISTRA_USUARIO(?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setString(2, qUser.getEmail());
            cStmt.setString(3, qUser.getRfc());
            //cStmt.setDouble(4, Double.parseDouble(qUser.getCelular()));
            cStmt.setString(4, qUser.getCelular());
            cStmt.setString(5, qUser.getPassword());

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();

            logger.info("Registra resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Registra:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public List<Movimiento> getMovimientos(Integer idColaborador){
        String resultJSON = null;
        Connection con = null;
        //CallableStatement cStmt = null;
        PreparedStatement pStmt = null;
        List<Movimiento> movimientos = new ArrayList<>();

        //logger.info("idColaborador:" + idColaborador);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            /**
            String sqlQuery =
                    "SELECT a.clave_autorizacion, a.fch_solicita, a.fch_transferencia, " +
                    " a.total, a.importe, a.comision, a.promocion, a.promocion_id, a.id_anti, c.periodicidad, c1.centro_costos_clnt_id, c1.clnt_id " +
                    " FROM anticipo a " +
                    " INNER JOIN colaborador c ON ( a.pers_id = c.pers_id AND a.fch_ingreso = c.fch_ingreso AND a.clnt_id = c.clnt_id  ) " +
                    " INNER JOIN cliente c1 ON ( c.clnt_id = c1.clnt_id  )  " +
                    " WHERE c.clave_colaborador = ? " +
                    " AND c.es_activo IS TRUE " +
                    " AND a.fch_solicita >= (select ca.fch_inicio " +
                    " FROM `cortes_anticipo` ca" +
                    " WHERE now() between ca.fch_inicio AND ca.fch_fin" +
                    " AND ca.centro_costos_clnt_id = c1.centro_costos_clnt_id " +
                    " AND ca.es_activo IS TRUE " +
                    " AND ca.periodicidad = c.periodicidad) " +
                    " ORDER BY a.id_anti DESC;";*/

            String sqlQuery = "SELECT  e.s_clave_rastreo as clave_autorizacion , a.fch_solicita, a.fch_transferencia, \n" +
                    "a.total, a.importe, a.comision, a.promocion, a.promocion_id, a.id_anti, c.periodicidad, c1.centro_costos_clnt_id, c1.clnt_id \n" +
                    "FROM anticipo a \n" +
                    "INNER JOIN colaborador c ON ( a.pers_id = c.pers_id AND a.fch_ingreso = c.fch_ingreso AND a.clnt_id = c.clnt_id  ) \n" +
                    "INNER JOIN cliente c1 ON ( c.clnt_id = c1.clnt_id  )  \n" +
                    "INNER JOIN evento_pago e ON ( a.id_anti = e.id_anti  )  \n" +
                    "WHERE c.clave_colaborador = ? \n" +
                    "AND c.es_activo IS TRUE \n" +
                    "ORDER BY a.id_anti DESC;";

            pStmt = con.prepareCall(sqlQuery);
            //pStmt.registerOutParameter(1, Types.OTHER);
            pStmt.setInt(1, idColaborador);
            pStmt.executeQuery();
            ResultSet rs = pStmt.executeQuery();
            //logger.info("MOV START ITERATE");
            while(rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setClaveAutorizacion(rs.getString(1));
                movimiento.setFechaSolicita(rs.getString(2));
                movimiento.setFechaTransferencia(rs.getString(3));
                movimiento.setImporteTotal(rs.getDouble(4));
                movimiento.setImporteSolicitado(rs.getDouble(5));
                movimiento.setImporteComision(rs.getDouble(6));
                movimiento.setPromocion(rs.getDouble(7));
                movimiento.setPromocionId(rs.getInt(8));
                //logger.info("Importe Double" + rs.getDouble(4));
                //logger.info("Importe Float" + rs.getFloat(4));
                //logger.info("Importe String" + rs.getString(4));
                movimiento.setIdMovimiento(rs.getLong(9));
                movimiento.setPeriodicidad(rs.getString(10));
                movimiento.setCentroCostos(rs.getString(11));
                movimiento.setIdEmpresa(rs.getString(12));
                movimientos.add(movimiento);
                //logger.info(movimiento.toString());
            }
            //logger.info("MOV END ITERATE");
            rs.close();

            //pStmt.close();
            //cStmt.close();
            logger.info("Consulta getMovimientos movimientos:" + movimientos);
        }
        catch (Exception e){
            logger.error("Excepcion en DAO Movimientos:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return movimientos;
    }

    public List<Movimiento> getMovimientosH(Integer idColaborador){
        String resultJSON = null;
        Connection con = null;
        //CallableStatement cStmt = null;
        PreparedStatement pStmt = null;
        List<Movimiento> movimientos = new ArrayList<>();

        //logger.info("idColaborador:" + idColaborador);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            String sqlQuery =
                    " SELECT a.clave_autorizacion, a.fch_solicita, a.fch_transferencia, a.total, " +
                    " a.importe, a.comision, a.promocion, a.promocion_id, a.id_anti, c.periodicidad, c1.centro_costos_clnt_id, c1.clnt_id " +
                            " FROM anticipo a " +
                            " INNER JOIN colaborador c ON ( a.pers_id = c.pers_id AND a.fch_ingreso = c.fch_ingreso AND a.clnt_id = c.clnt_id  ) " +
                            " INNER JOIN cliente c1 ON ( c.clnt_id = c1.clnt_id  ) " +
                            " WHERE c.clave_colaborador = ? " +
                            " AND c.es_activo IS TRUE " +
                            " AND a.fch_solicita < (select ca.fch_inicio " +
                            " FROM `cortes_anticipo` ca " +
                            " WHERE now() between ca.fch_inicio AND ca.fch_fin " +
                            " AND ca.centro_costos_clnt_id = c1.centro_costos_clnt_id " +
                            " AND ca.es_activo IS TRUE " +
                            " AND ca.periodicidad = c.periodicidad) " +
                            " ORDER BY a.id_anti DESC;";

            pStmt = con.prepareCall(sqlQuery);
            //pStmt.registerOutParameter(1, Types.OTHER);
            pStmt.setInt(1, idColaborador);
            pStmt.executeQuery();
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setClaveAutorizacion(rs.getString(1));
                movimiento.setFechaSolicita(rs.getString(2));
                movimiento.setFechaTransferencia(rs.getString(3));
                movimiento.setImporteTotal(rs.getDouble(4));
                movimiento.setImporteSolicitado(rs.getDouble(5));
                movimiento.setImporteComision(rs.getDouble(6));
                movimiento.setPromocion(rs.getDouble(7));
                movimiento.setPromocionId(rs.getInt(8));

                //logger.info("Importe " + rs.getDouble(4));
                movimiento.setIdMovimiento(rs.getLong(9));
                movimiento.setPeriodicidad(rs.getString(10));
                movimiento.setCentroCostos(rs.getString(11));
                movimiento.setIdEmpresa(rs.getString(12));
                movimientos.add(movimiento);
            }
            rs.close();

            //pStmt.close();
            //cStmt.close();
            logger.info("Consulta getMovimientos movimientosH:" + movimientos);
        }
        catch (Exception e){
            logger.error("Excepcion en DAO Movimientos H:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return movimientos;
    }

    public String getTerminos(String parametro){
        String resultTerms = null;
        Connection con = null;
        //CallableStatement cStmt = null;
        PreparedStatement pStmt = null;

        //logger.info("idColaborador:" + idColaborador);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            String sqlQuery =
                    " SELECT JSON_UNQUOTE (JSON_EXTRACT(F_GET_PARAMETRO(?, NULL), '$.valor')) ";

            pStmt = con.prepareCall(sqlQuery);
            //pStmt.registerOutParameter(1, Types.OTHER);
            pStmt.setString(1, parametro);
            pStmt.executeQuery();
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                resultTerms = rs.getString(1);
                //logger.info("Terminos resultTerms:" + resultTerms);
            }
            rs.close();
            //pStmt.close();
            //cStmt.close();
            logger.info("Terminos resultTerms:" + resultTerms);
        }
        catch (Exception e){
            logger.error("Excepcion en DAO Terminos:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultTerms;
    }

    

    public String updateP(Password password,int idColaborador){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;

        //logger.info("qUser:" + qUser);
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = CALL APP_MOBILE_CAMBIA_PASSWORD(?,?,?) }");
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setString(2, password.getNewPW());
            cStmt.setString(3, password.getOldPW());
            cStmt.setInt(4, idColaborador);

            cStmt.execute();
            resultJSON = cStmt.getString(1);
            //cStmt.close();

            logger.info("ActualizaP resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO ActualizaP:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }

    public String confirmaSolitiudAnticipoSTP(SolicitudAnticipo solicitudAnticipo, Pago_STP pago_stp){
        String resultJSON = null;
        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //con = DriverManager.getConnection(
            //        "jdbc:mysql://119.8.3.41:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
                    //"jdbc:mysql://94.74.70.240:3306/quincena_amm", "root", "c0ns0l1daMX_2021");
            cStmt = con.prepareCall("{? = call APP_MOBILE_API_PAGO_SOLICITA_STP(?,?,?,?,?)}");
            cStmt.registerOutParameter(1, Types.OTHER);

            cStmt.setInt(2, Integer.parseInt(solicitudAnticipo.getId_anti()));
            cStmt.setString(3, pago_stp.getCadenaOriginal());
            cStmt.setString(4, pago_stp.getCadenaSellada());
            cStmt.setString(5, solicitudAnticipo.getFolio_origen());
            cStmt.setString(6, pago_stp.getResultadoSTP());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Confirma Solitiud Anticipo STP resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Comision Anticipo STP:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }
    
    public String crearEventoLog(EventoLog eventoLog) {
    	
    	String pattern = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
    	
    	String patternShort = "yyyy-MM-dd";
    	SimpleDateFormat dateFormatterShort = new SimpleDateFormat(patternShort);
    	
    	Connection con = null;
    	PreparedStatement pStmt = null;
    	
    	try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            
            Colaborador colaborador = consutaColaboradorPorClave(eventoLog.getIdColaborador()); 
            
            if (colaborador==null) {
            	return ERROR_EVENTO;
            }
            
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("INSERT INTO `quincena_amm`.`eventos_logger` ")
            	.append("(`fch_evento`, `tipo_evento`, `pers_id`, ")
            	.append("`fch_ingreso`, `clnt_id`, `georeferencia`) ")
            	.append("VALUES ('")
            	.append(dateFormatter.format(new Date())).append("', ")
            	.append("'").append(eventoLog.getTipo()).append("', ")
            	.append(colaborador.getPersonaId()).append(", ")
            	.append("'").append(dateFormatterShort.format(colaborador.getFechaIngreso())).append("', ")
            	.append(colaborador.getClienteId()).append(", ")
            	.append("'").append(eventoLog.getGeoreferencia()).append("')");
            
            logger.info("/*** Insert evento :: "+queryStr.toString());
            
            pStmt = con.prepareCall(queryStr.toString());            
            pStmt.executeUpdate();
        }
        catch (Exception e){
            logger.error("Excepcion en DAO crearEventoLog: " + e.getMessage());
            e.printStackTrace();
            return ERROR_EVENTO;
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
                return ERROR_EVENTO;
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
                return ERROR_EVENTO;
            }
        }
    	
    	return EXITO_EVENTO;    	
    }
    
    private Colaborador consutaColaboradorPorClave(String claveColaborador){
        
        Connection con = null;        
        PreparedStatement pStmt = null;
        
        Colaborador colaborador = null;
        
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();            
            
            StringBuilder queryStr = new StringBuilder();   
            queryStr.append("SELECT c.pers_id, c.fch_ingreso, c.clnt_id ")
            	.append("FROM quincena_amm.colaborador c ")
            	.append("WHERE c.clave_colaborador =")
            	.append(claveColaborador);                        
            
            logger.info("/*** Query Colaborador :: "+queryStr.toString());
            
            pStmt = con.prepareCall(queryStr.toString());
            
            pStmt.executeQuery();
            ResultSet rs = pStmt.executeQuery();
                        
            while(rs.next()) {
            	colaborador = new Colaborador();
            			
                colaborador.setPersonaId(rs.getInt(1));
                logger.info("/*** fecha Colaborador :: "+rs.getDate(2));
                colaborador.setFechaIngreso(rs.getDate(2));
                colaborador.setClienteId(rs.getInt(3));
            }
            rs.close();            
        }
        catch (Exception e){
            logger.error("Excepcion en DAO Movimientos H:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return colaborador;
    }
    public String saveResponse(TestResultRequest testResultRequest){
        String resultJSON = null;

        Connection con = null;
        CallableStatement cStmt = null;
        try {
            DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();

            String query="{? = call APP_MOBILE_API_PAGO_RESPUESTA_ASINCRONA_BP(?,?,?,?,?,?)}";

            cStmt = con.prepareCall(query);
            cStmt.registerOutParameter(1, Types.OTHER);
            cStmt.setString(2, testResultRequest.toString());
            cStmt.setString(3, testResultRequest.getId());
            cStmt.setString(4, testResultRequest.getEmpresa());
            cStmt.setString(5, testResultRequest.getFolioOrigen());
            cStmt.setString(6, testResultRequest.getEstado());
            cStmt.setString(7, testResultRequest.getCausaDevolucion());

            cStmt.execute();
            resultJSON = cStmt.getString(1);

            logger.info("Registro Respuesta asyncrona STP resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Consulta Saldo:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (cStmt != null) { cStmt.close(); }
            }
            catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try { if (con != null) { con.close(); }
            }
            catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }

        return resultJSON;
    }
}
