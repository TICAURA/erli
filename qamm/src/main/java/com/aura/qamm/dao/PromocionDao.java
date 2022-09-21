package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.dto.Promocion;
import com.aura.qamm.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromocionDao {
    /**
    Logger LOGGER = LoggerFactory.getLogger(PromocionDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public List<Promocion> getPromociones() throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMOS(); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.execute();

            List<Promocion> promociones = new ArrayList<>();
            ResultSet resultSet = cStmt.getResultSet();
            while(resultSet.next()){
                promociones.add(Promocion.build(resultSet));
            }
            return promociones;

        }catch (SQLException e){
            LOGGER.error("Error obteniendo las promociones: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public Promocion getPromocion(int idPromocion) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMO(?); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idPromocion);
            cStmt.execute();


            ResultSet resultSet = cStmt.getResultSet();
            if(resultSet.next()) {
                return Promocion.build(resultSet);
            }else{
                return null;
            }


        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }
    */
    /**
    public List<Promocion> getPromocionPorColaborador(int claveColaborador) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMOS_POR_COLABORADOR(?) ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,claveColaborador);
            cStmt.execute();

            List<Promocion> promociones = new ArrayList<>();
            ResultSet resultSet = cStmt.getResultSet();
            while(resultSet.next()){
                Promocion promo = Promocion.build(resultSet);
                promo.setCobrado(resultSet.getBoolean("cobrado"));
                promo.setFechaCanjeo(resultSet.getDate("fecha_canjeo"));
                promo.setFechaCobro(resultSet.getDate("fecha_cobro"));

                promociones.add(Promocion.build(resultSet));
            }
            return promociones;


        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }*/

    /**
    public List<Promocion> getPromocionPorColaboradorUtilizadas(int claveColaborador) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMOS_POR_COLABORADOR_TODAS(?) ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,claveColaborador);
            cStmt.execute();

            List<Promocion> promociones = new ArrayList<>();
            ResultSet resultSet = cStmt.getResultSet();
            while(resultSet.next()){
                Promocion promo = Promocion.build(resultSet);
                promo.setCobrado(resultSet.getBoolean("cobrado"));
                promo.setFechaCanjeo(resultSet.getDate("fecha_canjeo"));
                promo.setFechaCobro(resultSet.getDate("fecha_cobro"));

                promociones.add(Promocion.build(resultSet));
            }
            return promociones;


        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }*/
    /**
    public void ejecutarPromocion(int idPromocion,int claveColaborador) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_EJECUTAR_PROMOCION(?,?); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idPromocion);
            cStmt.setInt(2,claveColaborador);
            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }


    public void canjearPromocion(int idPromocion,int claveColaborador) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_CANJEAR_PROMOCION(?,?); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idPromocion);
            cStmt.setInt(2,claveColaborador);
            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    /**
    public Promocion validarPromocion(int idPromocion,int claveColaborador)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROMO_BUSCAR_PROMO_COLABORADOR(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setInt(1,idPromocion);
            cStmt.setInt(2,claveColaborador);

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                Promocion promo = Promocion.build(rs);
                promo.setCobrado(rs.getBoolean("cobrado"));
                promo.setFechaCanjeo(rs.getDate("fecha_canjeo"));
                promo.setFechaCobro(rs.getDate("fecha_cobro"));
                return promo;
            }else{
                return null;
            }

        }catch (SQLException e){
            LOGGER.error("Error checando si la promocion existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }*/
    /**
    public Promocion obtenerPorUuid(String uuid)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROMO_OBTENER_POR_UUID(?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setString(1,uuid);
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                Promocion promo = Promocion.build(rs);
                return promo;
            }else{
                return null;
            }

        }catch (SQLException e){
            LOGGER.error("Error checando si la promocion existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }*/









}
