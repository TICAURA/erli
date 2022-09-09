package com.erli.argylewh.util;

import com.erli.argylewh.service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

@Service
public class ServiceInvoker {

    Logger logger = LoggerFactory.getLogger(ServiceInvoker.class);

    public String invokeBasicAuthBody(Properties serviceProperties, String body){
        //ServiceResponse serviceResponse = null;
        String srvResult = null;
        String errorMsg = null;

        String srvEndpoint = (String) serviceProperties.get("srvEndpoint");
        String srvMethod = (String) serviceProperties.get("srvMethod");
        String http_Content_Type = (String) serviceProperties.get("http_Content_Type");
        String httpAuthType = (String) serviceProperties.get("httpAuthType");

        String response = null;
        String error = null;
        String code = null;

        try {
            logger.info("srvEndpoint:" + srvEndpoint);
            URL url = new URL (srvEndpoint);

            //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            logger.info("srvMethod:" + srvMethod);
            connection.setRequestMethod(srvMethod);
            if (srvMethod.equals("POST")) connection.setDoOutput(true);
            //connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            logger.info("http_Content_Type:" + http_Content_Type);
            connection.setRequestProperty("Content-Type", http_Content_Type);

            //String httpAuth = "Bearer m3ZdIiRO5A0688V8DHXHDkBwpvPkXqk6SA";
            //logger.info("httpAuth:" + httpAuth);

            switch (httpAuthType){
                case "TokenBearer":
                    String httpAuth = (String) serviceProperties.get("http_Authorization");
                    logger.info("Authorization:" + httpAuth);
                    connection.setRequestProperty("Authorization", httpAuth);
                    break;
                case "Basic":
                    String srvUser = (String) serviceProperties.get("srvUser");
                    String srvPass = (String) serviceProperties.get("srvPass");
                    logger.info("srvUser:" + srvUser);
                    logger.info("srvPass:" + srvPass);
                    String encoding = Base64.getEncoder().encodeToString((srvUser + ":" + srvPass).getBytes("UTF-8"));
                    logger.info("Authorization:" + "Basic " + encoding);
                    connection.setRequestProperty  ("Authorization", "Basic " + encoding);
                    break;
                default:
                    logger.info("No authorization specified on WS File");
            }

            if (srvMethod.equals("POST")) {
                String jsonInputString = body;
                logger.info("body:" + body);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    logger.info("input:" + input);
                    os.write(input, 0, input.length);
                    logger.info("Envio de datos completado");
                } catch (Exception ei) {
                    logger.error("Se produjo un error de envio/escritura de body:" + ei.getMessage());
                    ei.printStackTrace();
                }
            }

            InputStream inputStream = null;
            int status = connection.getResponseCode();
            logger.info("HTTP status [" + status +"]");
            if (status != HttpURLConnection.HTTP_OK)  {
                logger.info("Respuesta No Completada, HTTP KO:[" + status +"]");
                inputStream = connection.getErrorStream();
                logger.info("ErrorStream [" + inputStream +"]");
                if (inputStream == null) inputStream = connection.getInputStream();
                logger.info("InputStream [" + inputStream +"]");
            }
            else{
                logger.info("Respuesta Completada, HTTP OK [" + status +"]");
                inputStream = connection.getInputStream();
            }
            logger.info("inputStream:" + inputStream);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));

            StringBuilder responseSB = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                responseSB.append(responseLine.trim());
            }
            response = responseSB.toString();
            code = status + "";

        }
        catch(Exception e) {
            error = e.getMessage();
            logger.error("Se produjo un error en la invocacion BA:" + error);
            e.printStackTrace();
        }
        logger.info("response:" + response);
        logger.info("error:" + error);
        logger.info("code:" + code);
        //serviceResponse =  new ServiceResponse(response, error, code);

        srvResult = "{\"response\":" + response + "}";

        return response;
    }
}
