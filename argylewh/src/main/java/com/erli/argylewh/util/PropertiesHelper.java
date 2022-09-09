package com.erli.argylewh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class PropertiesHelper {
    Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);

    public Properties loadProperties(String propertiesFilename, String propsReference) {
        String userDirectory = new File("").getAbsolutePath();

        logger.info("userDirectory:" + userDirectory);

        Properties dbProperties = new Properties();
        String execPropertiesPath = userDirectory + "/" + propertiesFilename;
        logger.info("Properties Path of "+ propsReference + ":" + execPropertiesPath);
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            dbProperties.load(inputStream);
            for (Object execProperty : dbProperties.keySet()) {
                logger.info("Property of " + propsReference + " [" + execProperty + "] [" +
                        dbProperties.get(execProperty).toString() + "]");
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("Execution Properties File Not Found: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            logger.error("Execution Properties Error Read: " + ioe.getMessage());
            ioe.getMessage();
        }
        return dbProperties;
    }
}
