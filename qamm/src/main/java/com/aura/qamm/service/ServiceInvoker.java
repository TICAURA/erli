package com.aura.qamm.service;

import javax.net.ssl.HttpsURLConnection;
//import javax.xml.ws.BindingType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceInvoker {
    Logger logger = LoggerFactory.getLogger(ServiceInvoker.class);
    String serviceResponse = null;
    public String invoke(Properties serviceProperties, String body){
    //public String invoke(String endpoint, String body){

        String endpoint = (String) serviceProperties.get("endpoint");
        String http_method = (String) serviceProperties.get("http_method");
        String http_Content_Type = (String) serviceProperties.get("http_Content_Type");
        //String http_Accept = (String) serviceProperties.get("http_Accept");
        //String authorization = (String) serviceProperties.get("Authorization");

        //String http_method = "POST";
        //String http_Content_Type = "application/json";
        //String http_Accept = "application/json";

        try {
            logger.info("body:" + body);
            logger.info("Endpoint:" + endpoint);
            URL url = new URL(endpoint);
            //HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            logger.info("Apertura de Conexion HTTPs OK");

            con.setRequestMethod(http_method);
            con.setRequestProperty("Content-Type", http_Content_Type);
            //con.setRequestProperty("Accept", http_Accept);
            //con.setRequestProperty("Authorization", authorization);
            con.setDoOutput(true);

            String jsonInputString = body;
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                logger.info("Envio de datos completado");
            }
            catch (Exception ei){
                logger.error("Se produjo un error de envio/escritura de body:" + ei.getMessage());
                ei.printStackTrace();
            }

            InputStream inputStream = null;
            int status = con.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)  {
                logger.info("Respuesta completada HTTP OK [" + status +"]");
                inputStream = con.getErrorStream();
            }
            else{
                logger.error("Respuesta HTTP KO [" + status +"]");
                inputStream = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            logger.info("Recepcion de Datos de Response OK");
            serviceResponse = response.toString();
            logger.info("serviceResponse:" + serviceResponse);
        }
        catch (Exception e){
            logger.error("Se produjo un error en la invocacion:" + e.getMessage());
            e.printStackTrace();
        }
        return serviceResponse;
    }

    public String invokeHTTPS(Properties serviceProperties, String body){
        //public String invoke(String endpoint, String body){

        String endpoint = (String) serviceProperties.get("endpoint");
        String http_method = (String) serviceProperties.get("http_method");
        String http_Content_Type = (String) serviceProperties.get("http_Content_Type");
        String http_Accept = (String) serviceProperties.get("http_Accept");
        String authorization = (String) serviceProperties.get("Authorization");

        //String http_method = "POST";
        //String http_Content_Type = "application/json";
        //String http_Accept = "application/json";

        try {
            logger.info("body:" + body);
            logger.info("Endpoint:" + endpoint);
            URL url = new URL(endpoint);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            logger.info("Apertura de Conexion HTTPs OK");

            con.setRequestMethod(http_method);
            con.setRequestProperty("Content-Type", http_Content_Type);
            con.setRequestProperty("Accept", http_Accept);
            if(authorization != null && !authorization.equals("") ) {
                con.setRequestProperty("Authorization", authorization);
            }
            con.setDoOutput(true);

            String jsonInputString = body;
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                logger.info("Envio de datos completado");
            }
            catch (Exception ei){
                logger.error("Se produjo un error de envio/escritura de body:" + ei.getMessage());
                ei.printStackTrace();
            }

            InputStream inputStream = null;
            int status = con.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)  {
                logger.info("Respuesta completada HTTP OK [" + status +"]");
                inputStream = con.getErrorStream();
            }
            else{
                logger.error("Respuesta HTTP KO [" + status +"]");
                inputStream = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            logger.info("Recepcion de Datos de Response OK");
            serviceResponse = response.toString();
            logger.info("serviceResponse:" + serviceResponse);
        }
        catch (Exception e){
            logger.error("Se produjo un error en la invocacion:" + e.getMessage());
            e.printStackTrace();
        }
        return serviceResponse;
    }

    public String invokeHTTP(Properties serviceProperties){
        //public String invoke(String endpoint, String body){

        String endpoint = (String) serviceProperties.get("endpoint");
        String http_method = (String) serviceProperties.get("http_method");
        String http_Content_Type = (String) serviceProperties.get("http_Content_Type");
        String http_Accept = (String) serviceProperties.get("http_Accept");
        String authorization = (String) serviceProperties.get("Authorization");

        //String http_method = "POST";
        //String http_Content_Type = "application/json";
        //String http_Accept = "application/json";

        try {

            logger.info("Endpoint:" + endpoint);
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            logger.info("Apertura de Conexion HTTPs OK");

            con.setRequestMethod(http_method);
            con.setRequestProperty("Content-Type", http_Content_Type);
            con.setRequestProperty("Accept", http_Accept);
            if(authorization != null && !authorization.equals("") ) {
                con.setRequestProperty("Authorization", authorization);
            }


            InputStream inputStream = null;
            int status = con.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)  {
                logger.info("Respuesta completada HTTP OK [" + status +"]");
                inputStream = con.getErrorStream();
            }
            else{
                logger.error("Respuesta HTTP KO [" + status +"]");
                inputStream = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            logger.info("Recepcion de Datos de Response OK");
            serviceResponse = response.toString();
            logger.info("serviceResponse:" + serviceResponse);
        }
        catch (Exception e){
            logger.error("Se produjo un error en la invocacion:" + e.getMessage());
            e.printStackTrace();
        }
        return serviceResponse;
    }

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

    public String invokeBasicHTTPAuthBody(Properties serviceProperties, String body){
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

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            logger.info("srvMethod:" + srvMethod);
            connection.setRequestMethod(srvMethod);
            if (srvMethod.equals("POST")) connection.setDoOutput(true);
            //connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            logger.info("http_Content_Type:" + http_Content_Type);
            connection.setRequestProperty("Content-Type", http_Content_Type);

            //String httpAuth = "Bearer m3ZdIiRO5A0688V8DHXHDkBwpvPkXqk6SA";
            //logger.info("httpAuth:" + httpAuth);

            /**
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
            }*/

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

    public String invokeTP(Properties serviceProperties, String body){
        String response = "";
        try
        {
            String srvEndpoint = (String) serviceProperties.get("srvEndpoint");
            String srvMethod = (String) serviceProperties.get("srvMethod");
            String http_Content_Type = (String) serviceProperties.get("http_Content_Type");


            //URL urlService = new URL( "https://api.sandbox.tabapay.net:10443/v1/clients/y8M2eZgRCQ1AtEEO1i85HQ/cards" );
            URL urlService = new URL( srvEndpoint );
            logger.info("fixed endpoint:" + "https://api.sandbox.tabapay.net:10443/v1/clients/y8M2eZgRCQ1AtEEO1i85HQ/cards");
            logger.info("props endpoint:" + srvEndpoint);
            //https://api.sandbox.tabapay.net:10443/v1/clients/y8M2eZgRCQ1AtEEO1i85HQ/cards
            //https://api.sandbox.tabapay.net:10443/v1/clients/y8M2eZgRCQ1AtEEO1i85HQ/cards
            //URL urlService = new URL( srvEndpoint);

            HttpsURLConnection connectionService =
                    (HttpsURLConnection) urlService.openConnection();

            //connectionService.setRequestMethod( "POST" );
            connectionService.setRequestMethod(srvMethod);
            connectionService.setRequestProperty(
                    "Authorization", "Bearer " + "m3ZdIiRO5A0688V8DHXHDkBwpvPkXqk6SA"
            );
            connectionService.setRequestProperty(
                    "Content-type", "application/json"
            );

            byte[] abDataRequest =
                    "{\"card\":{\"accountNumber\":\"9999999999999999\"}}".getBytes( "UTF-8" );

            connectionService.setDoOutput( true );
            OutputStream outsRequest = connectionService.getOutputStream();
            outsRequest.write( abDataRequest, 0, abDataRequest.length );
            outsRequest.close();

            int iStatusCode = connectionService.getResponseCode();
            System.out.println( "TabaPay API Call, SC=" + iStatusCode );

            InputStream insResponse = iStatusCode == 200
                    ? connectionService.getInputStream()
                    : connectionService.getErrorStream();

            byte[] abResponse  = new byte[1024];
            int    iLengthRead = insResponse.read( abResponse );
            insResponse.close();

            System.out.println( new String( abResponse, 0, iLengthRead, "UTF-8" ) );
            //response = abResponse;
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
        }
        return response;
    }
}
