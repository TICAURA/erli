package com.erli.argylewh.config;

import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.*;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource getDataSource() {

        Properties dbProperties = loadDBProperties();
        String db_url = (String) dbProperties.get("url");
        String db_user = (String) dbProperties.get("user");
        String db_pass = (String) dbProperties.get("password");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url(db_url);
        dataSourceBuilder.username(db_user);
        dataSourceBuilder.password(db_pass);

        //DataSource ds = dataSourceBuilder.build();
        //System.out.println("ds:" + ds);
        return dataSourceBuilder.build();
        //return ds;
    }

    private Properties loadDBProperties() {
        String userDirectory = new File("").getAbsolutePath();
        //String basePath = userDirectory + "/" + type + "/";
        logger.info("userDirectory:" + userDirectory);

        Properties dbProperties = new Properties();
        String execPropertiesPath = userDirectory + "/db.properties";
        logger.info("DB Properties Path:" + execPropertiesPath);
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            dbProperties.load(inputStream);
            for (Object execProperty : dbProperties.keySet()) {
                logger.info("DB Property [" + execProperty + "] [" +
                        dbProperties.get(execProperty).toString() + "]");
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("Database properties file not found:" + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            logger.error("Database properties read faiulure:" + ioe.getMessage());
            ioe.getMessage();
        }
        return dbProperties;
    }
}
