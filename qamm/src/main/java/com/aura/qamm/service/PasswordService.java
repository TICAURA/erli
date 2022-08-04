package com.aura.qamm.service;

import com.aura.qamm.config.JwtTokenUtil;
import com.aura.qamm.dao.AuthDao;
import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.*;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.model.AuthUser;
import com.aura.qamm.transformer.QuincenaTransformer;
import com.aura.qamm.util.FileHelper;
import com.aura.qamm.util.JSONPathUtil;
import com.aura.qamm.util.PropertiesHelper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriterFactory;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class PasswordService {

    @Value("${url.admin}")
    private String urlAdmin;

    @Value("${sender.email}")
    private String sender;

    @Autowired
    private PropertiesHelper propertiesHelper;
    @Autowired
    private FileHelper fileHelper;
    @Autowired
    private ServiceInvoker serviceInvoker;
    @Autowired
    private QuincenaDAO quincenaDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(PasswordService.class);


    public QUser hashPasswordRegistra(QUser qUser){
        qUser.setPassword(passwordEncoder.encode(qUser.getPassword()));
        return qUser;
    }



    public void updatePassword(Password password,int idColaborador,String email) throws BusinessException {
        AuthUser authUser = authDao.getUserByEmail(email);


        if(passwordEncoder.matches(password.getOldPW(),authUser.getPassword())){
            password.setNewPW(passwordEncoder.encode(password.getNewPW()));
            authDao.changePassword(idColaborador,password.getNewPW());
        }else{
            throw new BusinessException("Error las contraseñas no coinciden.",401);
        }

    }
    public ResetPWResponse recuperarPassword(String email){
        logger.info("Reset Password.");

        ResetPWResponse response = new ResetPWResponse();

        //Load token properties
        Properties spTokenProps = propertiesHelper.loadProperties("spToken.properties", "Autorizacion Servicio Mail");
        String spTokenInputTemplate = fileHelper.readFile("spTokenInput.json");
        logger.info("spTokenInputTemplate:" + spTokenInputTemplate);
        String serviceResponse = serviceInvoker.invoke(spTokenProps,spTokenInputTemplate);
        logger.info("serviceResponse:" + serviceResponse);

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("$['access_token']");
        Map<String,String> values = JSONPathUtil.evaluateJSONPath(serviceResponse,jsonPaths);
        String token = values.get("$['access_token']");
        logger.info("token:" + token);

        JsonArrayBuilder receivers = Json.createArrayBuilder();

        JsonObjectBuilder receiver = Json.createObjectBuilder()
                .add("email",email);
        receivers.add(receiver);


        JsonObjectBuilder from = Json.createObjectBuilder()
                .add("email",sender);

        JsonObjectBuilder data = Json.createObjectBuilder()
                .add("subject","Reseteo password")
                .add("text",getMensajeReset(email))
                .add("from",from)
                .add("to",receivers);

        JsonObjectBuilder emailJson = Json.createObjectBuilder()
                .add("email",data);

        JsonWriterFactory writerFactory = Json.createWriterFactory(new HashMap<String,Object>());

        String jsonString;

        try(Writer writer = new StringWriter()) {
            writerFactory.createWriter(writer).write(emailJson.build());
            jsonString = writer.toString();

            logger.info("spCallInputTemplate:" + jsonString);

            getResponseFromRequestREST("https://api.sendpulse.com/smtp/emails", jsonString, token, response);
        } catch (IOException e) {
            logger.error("**** Error al enviar correo de recuperación de password ",e.getMessage());
            response.setMsgErrorEsp("Error al enviar correo de recuperación de contraseña");
            response.setMsgErrorEng("Error sending password recovery email");
        }

        return response;
    }


    private String getResponseFromRequestREST(String urlRest, String bodyRequest, String tokenAuthorization, ResetPWResponse resetResponse) throws IOException {
        StringBuilder response = null;
        try {
            URL url = new URL(urlRest);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(Boolean.TRUE);
            if(tokenAuthorization != null && !tokenAuthorization.trim().equals("")) {
                connection.setRequestProperty ("authorization", "Bearer "+tokenAuthorization);
            }

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] bytes = bodyRequest.getBytes(StandardCharsets.UTF_8);
                outputStream.write(bytes, 0, bytes.length);
                logger.info("Petición enviada.");
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8))) {
                response = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) response.append(line);
                logger.info("RESPONSE = " + response.toString());
                resetResponse.setMsgExitoEsp("La petición se envio de manera correcta");
                resetResponse.setMsgExitoEng("The request was sent correctly");
            }
        } catch (Exception e) {
            logger.error("OCURRIO UN ERROR AL REALIZAR PETICION");
            resetResponse.setMsgErrorEsp("Ocurrio un error al realizar la petición");
            resetResponse.setMsgErrorEng("An error occurred while making the request");
            e.printStackTrace();
        }
        return response != null ? response.toString() :null;
    }

    private String getMensajeReset(String email) {
        StringBuilder strBuild = new StringBuilder();

        strBuild.append("Recibimos una solicitud de cambio de contraseña, En el caso de que efectivamente lo hayas solicitado, por favor, sigue el siguiente link.  ");
        strBuild.append(urlAdmin);

        strBuild.append(jwtTokenUtil.generateResetPasswordToken(email));
        //byte[] encodedString = Base64.encodeBase64(email.getBytes());
        //strBuild.append(new String(encodedString));

        return strBuild.toString();
    }




}
