package com.erli.tp.PaymentEngine.util;

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
        logger.info("Properties path "+ propsReference + ":" + execPropertiesPath);
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            dbProperties.load(inputStream);
            for (Object execProperty : dbProperties.keySet()) {
                logger.info("Property " + propsReference + " [" + execProperty + "] [" +
                        dbProperties.get(execProperty).toString() + "]");
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("Properties file not found: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            logger.error("Properties File Error: " + ioe.getMessage());
            ioe.getMessage();
        }
        return dbProperties;
    }
}
