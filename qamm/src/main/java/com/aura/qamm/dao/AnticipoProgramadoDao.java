package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.dto.AnticipoProgramado;
import com.aura.qamm.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnticipoProgramadoDao {
    /**
    Logger LOGGER = LoggerFactory.getLogger(AuthDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;



    public List<AnticipoProgramado> getAnticiposProgramados(int idColaborador) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "SELECT * FROM anticipo_programando WHERE clave_colaborador = ?;";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {


            cStmt.setInt(1,idColaborador);

            cStmt.execute();
            List<AnticipoProgramado> anticipoProgramados = new ArrayList<>();
            ResultSet rs = cStmt.getResultSet();
            while(rs.next()) {

            anticipoProgramados.add(crearAnticipoProgramado(rs));
            }
            return anticipoProgramados;
        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }

    public AnticipoProgramado getAnticipoProgramado(int idColaborador, int anticipoProgramadoId) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "SELECT * FROM anticipo_programando WHERE  programacion_anticipo_id = ? and clave_colaborador = ?;";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,anticipoProgramadoId);
            cStmt.setInt(2,idColaborador);

            cStmt.execute();

            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                return crearAnticipoProgramado(rs);
            }else{
                throw new BusinessException("Error, no se pudo encontrar el anticipo programado.",404);
            }

        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }



    public int postAnticipoProgramado(int idColaborador, AnticipoProgramado anticipoProgramado) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROGRAMAR_ANTICIPO(?,?,?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setDate(1,new java.sql.Date(anticipoProgramado.getFechaAnticipo().getTime()));
            cStmt.setInt(2,anticipoProgramado.getPeriodo());
            cStmt.setInt(3,idColaborador);

            cStmt.execute();

            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                return rs.getInt("programacion_anticipo_id");
            }else{
                throw new BusinessException("Error creando anticipo programado.",404);
            }


        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }

    public void putAnticipoProgramado(int idColaborador, AnticipoProgramado anticipoProgramado) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROGRAMAR_ANTICIPO_UPDATE(?,?,?,?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setDate(1,new java.sql.Date(anticipoProgramado.getFechaAnticipo().getTime()));
            cStmt.setInt(2,anticipoProgramado.getPeriodo());
            cStmt.setInt(3,anticipoProgramado.getIdProgramacionAnticipo());
            cStmt.setInt(4,idColaborador);

            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }

    public void deleteAnticipoProgramado(int idColaborador, int anticipoProgramadoId) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "DELETE FROM anticipo_programando WHERE  programacion_anticipo_id = ? and clave_colaborador = ?;";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,anticipoProgramadoId);
            cStmt.setInt(2,idColaborador);

            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }

    private AnticipoProgramado crearAnticipoProgramado(ResultSet rs) throws SQLException{
        AnticipoProgramado anticipoProgramado = new AnticipoProgramado();
        anticipoProgramado.setIdProgramacionAnticipo(rs.getInt("programacion_anticipo_id"));
        anticipoProgramado.setFechaAnticipo(rs.getDate("primer_fecha_programada"));
        anticipoProgramado.setFechaSiguienteAnticipo(rs.getDate("segunda_fecha_programada"));
        anticipoProgramado.setFechaCreacion(rs.getDate("fecha_creacion"));
        anticipoProgramado.setPeriodo(rs.getInt("periodo"));
        return anticipoProgramado;
    }
    */
}
