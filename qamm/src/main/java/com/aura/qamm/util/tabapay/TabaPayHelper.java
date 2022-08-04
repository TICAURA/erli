package com.aura.qamm.util.tabapay;

//import com.aura.qamm.model.payroll.EncryptedItem;
//import com.aura.qamm.service.erli.TabapayService;
//import com.tabapay.api.helpers.security.rsa.CryptoRSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import javax.crypto.Cipher;
import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;

//import java.security.MessageDigest;

import java.security.cert.Certificate;
//import java.util.Arrays;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//import com.tabapay.samples.CallTabaPay;
//import com.tabapay.samples.CallTabaPay.KeyData;
@Service
public class TabaPayHelper {

    @Value( "${tabapay.defKey}" )
    private String tabapay_defKey;

    Logger logger = LoggerFactory.getLogger(TabaPayHelper.class);

    /**
    public String encryptText(String plainText) {
        logger.info("plainText:" + plainText);
        String sEncodedEncryptedData = null;
        //EncryptedItem encryptItem = null;

        try {
            //sEncodedEncryptedData = CryptoRSA.encryptUsingPublicKey(
            //        "-----BEGIN PUBLIC KEY-----" + tabapay_defKey + "-----END PUBLIC KEY-----", plainText);
            sEncodedEncryptedData = CryptoRSA.encryptUsingPublicKey(
                             tabapay_defKey, plainText);

            logger.info("sEncodedEncryptedData:" + sEncodedEncryptedData);
            //encryptItem = new EncryptedItem(sEncodedEncryptedData, tabapay_defKey);

        }
        catch(Exception e){
            logger.info("Exception e:" + e.getMessage());
            e.printStackTrace();
        }

        return sEncodedEncryptedData;
    }*/

    public String signRequest(String tobeSignedString){
        try {
            //Loading the Private Key for Signing
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream("/Users/soniko/2022/erli_vic_fix/qamm/key/sender_keystore.p12"), "changeit".toCharArray());
            PrivateKey privateKey =
                    (PrivateKey) keyStore.getKey("senderKeyPair", "changeit".toCharArray());

            byte[] stringBytes = tobeSignedString.getBytes(StandardCharsets.UTF_8);
            //MessageDigest md = MessageDigest.getInstance("SHA-256");
            //byte[] messageHash = md.digest(messageBytes);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //byte[] digitalSignature = cipher.doFinal(messageHash);
            byte[] cipheredString = cipher.doFinal(stringBytes);

            System.out.println("cipheredString [" + cipheredString + "]");

            //return digitalSignature.toString();
            return Base64.getEncoder().encodeToString(cipheredString);
            //return Base64.getEncoder().encodeToString(cipheredString);
        }
        catch (Exception e){
            logger.error("Exception " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    //public String unsignRequest(String cipheredString, String hashed){
    public String unsignRequest(String cipheredString){
        //Loading a Public Key for Verification
        String unciphered = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream("/Users/soniko/2022/erli_vic_fix/qamm/key/receiver_keystore.p12"), "changeit".toCharArray());
            Certificate certificate = keyStore.getCertificate("receiverKeyPair");
            PublicKey publicKey = certificate.getPublicKey();

            byte[] bytes = Base64.getDecoder().decode(cipheredString);

            Cipher decriptCipher = Cipher.getInstance("RSA");
            decriptCipher.init(Cipher.DECRYPT_MODE, publicKey);

            unciphered  = new String(decriptCipher.doFinal(bytes), StandardCharsets.UTF_8);
            logger.info("unciphered:" + unciphered);

            /**
            //Check digital Signature
            byte[] encryptedMessageHash = signedString.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
            logger.info("decryptedMessageHash:" + decryptedMessageHash);

            //Hash
            byte[] messageBytes2 = hashed.getBytes(StandardCharsets.UTF_8);

            MessageDigest md2 = MessageDigest.getInstance("SHA-256");
            byte[] newMessageHash = md2.digest(hashed.getBytes(StandardCharsets.UTF_8));
            logger.info("newMessageHash:" + newMessageHash);

            boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
            System.out.println("isCorrect:" + isCorrect);
             */

            //return true;
        }
        catch (Exception e){
            logger.error("Exception " + e.getMessage());
            e.printStackTrace();
            //return false;
        }
        return unciphered;
    }

    public String encriptarFirma(String keyPath, String firma) throws IOException, GeneralSecurityException {
        String encodedString = null;

        File file = new File("priv.pem");
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll(" ", "")
                .replaceAll("\n", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        RSAPrivateKey rSAPrivateKey  = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        logger.info("rSAPrivateKey" +  rSAPrivateKey);
        //return (RSAPrivateKey) keyFactory.generatePrivate(keySpec).toString();

        return "nothing happens";

        //return encoded;

    }


    /**
    public String encriptarFirmaOLD(InputStream key, String firma) throws IOException, GeneralSecurityException {

        //Logger LOGGER = LoggerFactory.getLogger(BanpayCryptoUtils.class);
        //String privateKeyPEM = readFileAsString(key);
        String privateKeyPEM =  "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgg1ksSvpfYtUFjHms14TaziGUEfHGabkE91FZpSKB0qqhRANCAATJuiHg9Df5bVotF3swiMHMwjHENXV7f/BeIgqe9zP294o8rca1S4rxrIwFeW75xlaGNj/FVv0uY4lClLfnC9ZV";
        logger.info(privateKeyPEM);

        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replaceAll("\n", "");
        privateKeyPEM = privateKeyPEM.replaceAll(" ", "");

        logger.info(privateKeyPEM);

        //byte[] encodedPublicKey = privateKeyPEM.getBytes(StandardCharsets.UTF_8);
        byte[] encodedPublicKey = Base64.getDecoder().decode(privateKeyPEM.getBytes(StandardCharsets.UTF_8));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        byte[] encryptedBytes;

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        encryptedBytes = cipher.doFinal(firma.getBytes(StandardCharsets.UTF_8));

        String encoded = Base64.getEncoder().encodeToString(encryptedBytes);
        return encoded;

    }*/

    /**
    private String readFileAsString(InputStream key) throws IOException {
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(key, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, numRead);
        }
        return out.toString();
    }*/
}
