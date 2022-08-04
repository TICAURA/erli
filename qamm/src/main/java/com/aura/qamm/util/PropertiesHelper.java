package com.aura.qamm.util;

import com.aura.qamm.service.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class PropertiesHelper {
    Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);

    public Properties loadProperties(String propertiesFilename, String propsReference) {
        String userDirectory = new File("").getAbsolutePath();

        logger.info("userDirectory:" + userDirectory);

        Properties dbProperties = new Properties();
        String execPropertiesPath = userDirectory + "/" + propertiesFilename;
        logger.info("Ruta de Propiedades de "+ propsReference + ":" + execPropertiesPath);
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            dbProperties.load(inputStream);
            for (Object execProperty : dbProperties.keySet()) {
                logger.info("Propiedad de " + propsReference + " [" + execProperty + "] [" +
                        dbProperties.get(execProperty).toString() + "]");
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("No se encontro el archivo de propiedades de ejecucion: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            logger.error("Se produjo un error de lectura de Propiedades de ejecucion: " + ioe.getMessage());
            ioe.getMessage();
        }
        return dbProperties;
    }
}
