package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.dto.Colaborador;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.model.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthDao {
    Logger LOGGER = LoggerFactory.getLogger(AuthDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public AuthUser getUserByEmail(String email)throws UsernameNotFoundException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call AUTH_GET_USER(?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setString(1,email);

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                AuthUser authUser = new AuthUser();
                authUser.setUser(rs.getString("e_mail_registro"));
                authUser.setPassword(rs.getString("password"));
                authUser.setPersonId(rs.getString("p.pers_id"));
                authUser.setOnboardDate(rs.getString("c.fch_ingreso"));
                authUser.setClientId(rs.getString("c.clnt_id"));
                authUser.setCollaboratorId(rs.getString("c.clave_colaborador"));

                return authUser;
            }else{
                return null;
            }

        }catch (SQLException e){
            LOGGER.error("Error checando si el usuario existe: "+e.getMessage(),e);
            throw new UsernameNotFoundException("Error buscando al usuario: " + email+" error: "+e.getMessage());
        }
    }

    public int getClaveColaborador(String email)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call GET_CLAVE_COLABORADOR(?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setString(1,email);
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            if(rs.next()) {
                return rs.getInt("clave_colaborador");
            }else{
                throw new BusinessException("Error buscando la clave del colaborador.",404);
            }

        }catch (SQLException e){
            LOGGER.error("Error buscando la clave del colaborador. "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }
    public void changePassword(int idColaborador,String password ) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call AUTH_CHANGE_PASSWORD(?,?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idColaborador);
            cStmt.setString(2,password);

            cStmt.execute();


        }catch (SQLException e){
            LOGGER.error("Error actualizando la contraseña: "+e.getMessage(),e);
            throw new BusinessException("Error actualizando la contraseña : "+e.getMessage(),500);
        }
    }
}
