package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.dto.Notification;
import com.aura.qamm.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationDao {

    Logger LOGGER = LoggerFactory.getLogger(NotificationDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;


    public List<Notification> getNotifications(String email) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "SELECT * FROM notificaciones_push where clave_colaborador = (SELECT clave_colaborador FROM colaborador where e_mail_registro = ? ); ";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setString(1,email);

            cStmt.execute();

            List<Notification> notifications = new ArrayList<>();
            ResultSet resultSet = cStmt.getResultSet();
            while(resultSet.next()){
             notifications.add(resultSetToNotification(resultSet));
            }
            return notifications;

        }catch (SQLException e){
            LOGGER.error("Error obteniendo datos de la notificación: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public Notification getNotification(String email,String uuid) throws BusinessException {

        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "SELECT * FROM notificaciones_push where group_uuid = ? AND clave_colaborador = (SELECT clave_colaborador FROM colaborador where e_mail_registro = ? ); ";


        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setString(1,uuid);
            cStmt.setString(2,email);


            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();
            resultSet.next();
            return resultSetToNotification(resultSet);


        }catch (SQLException e){
            LOGGER.error("Error obteniendo datos de la notificación: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public void updateNotification(String email,String uuid) throws BusinessException {

        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call update_notificaciones_push(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setString(1,email);
            cStmt.setString(2,uuid);


            cStmt.execute();

            return;

        }catch (SQLException e){
            LOGGER.error("Error actualizando los datos la notificación: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    private Notification resultSetToNotification(ResultSet resultSet) throws SQLException{

        Notification notification = new Notification();
        notification.setId_notification(resultSet.getLong("id_notificacion"));
        notification.setTitle(resultSet.getString("title"));
        notification.setContents(resultSet.getString("contents"));
        notification.setUrl(resultSet.getString("url"));
        notification.setUuid(resultSet.getString("group_uuid"));
        notification.setSeen(resultSet.getBoolean("seen"));
        return notification;
    }


    public void deleteNotification(String email,String uuid) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "DELETE FROM notificaciones_push where group_uuid = ? AND clave_colaborador = (SELECT clave_colaborador FROM colaborador where e_mail_registro = ? ); ";

        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setString(1,uuid);
            cStmt.setString(2,email);


            cStmt.execute();

            return;


        }catch (SQLException e){
            LOGGER.error("Error borrando datos la notificación: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

}
