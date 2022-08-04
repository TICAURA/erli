package com.aura.qamm.util;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.nio.charset.Charset;
//import com.aura.qamm.util.tabapay.TabaPayHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
//import java.security.Security;
import java.security.spec.*;
import java.util.Base64;
import java.security.Signature;
//import org.bouncycastle.asn1.ocsp.Signature;
//import org.bouncycastle.jce.interfaces.ECPublicKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

//import javax.crypto.Cipher;

public interface TPSecurity {
    /**public String signString(String plainString){
        return null;
    }*/

    Logger logger = LoggerFactory.getLogger(TPSecurity.class);

    //public static PrivateKey getPublicKey(String plainText, String publicKeyPath)
    public static PrivateKey getPrivateKey(String privateKeyPath)
            throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
        //String key = new String(keyBytes, StandardCharsets.UTF_8);
        String key = Base64.getEncoder().encodeToString(keyBytes);
        logger.info("key:" + key);
        key = key.replace("-----BEGIN PRIVATE KEY-----\n", "");
        key = key.replace("-----END PRIVATE KEY-----", "");
        key = key.replaceAll("\n", "");
        key = key.replaceAll(" ", "");
        logger.info("stripped key:" + key);

        byte[] encodedPublicKey = Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));

        //KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //KeyFactory keyFactory = KeyFactory.getInstance("EC");
        //X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);

        KeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        PrivateKey privateKey = kf.generatePrivate(spec);

        /**
         * KeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
         * KeyFactory kf = KeyFactory.getInstance("EC");
         * PrivateKey key = kf.generateKey(spec);

        PrivateKey privateKey = keyFactory.generatePrivate(publicKeySpec);*/
        logger.info("privateKey:" + privateKey);
        //byte[] encryptedBytes;


        //System.out.println("encodedPublicKey:" + encodedPublicKey);
        //System.out.println("keyBytes:" + keyBytes.toString());

            //PrivateKey privateKey = getPrivate(publicKeyPath);
            //System.out.println("privateKey:" + privateKey);


        /**
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
        System.out.println("keyBytes:" + keyBytes.toString());

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("ECDSA");
        PrivateKey privateKey = factory.generatePrivate(spec);
         */

        //KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
        //java.security.PublicKey ecPublicKey = factory
        //        .generatePublic(new X509EncodedKeySpec(keyBytes)); // Helper.toByte(ecRemotePubKey)) is java.security.PublicKey#getEncoded()
        //return (ECPublicKey) ecPublicKey;

        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //X509EncodedKeySpec spec =
        //        new X509EncodedKeySpec(keyBytes);
        //KeyFactory kf = KeyFactory.getInstance("ECDSA");
        //KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        //Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        //KeyFactory kf = KeyFactory.getInstance("EC");
        //EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));

        //ecdsaSign.initSign(privateKey);
        //ecdsaSign.update(plaintext.getBytes("UTF-8"));
        //byte[] signature = ecdsaSign.sign();

        return privateKey;
    }

    public static String signString(String plainText, PrivateKey privateKey){
        String encryptedString = "";
        String sig = null;
        try {
            //Cipher cipher = Cipher.getInstance("RSA");
            //Cipher cipher = Cipher.getInstance("EC");
            //Cipher cipher = Cipher.getInstance("ECB");
            //cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
            ecdsaSign.initSign(privateKey);
            ecdsaSign.update(plainText.getBytes("UTF-8"));
            byte[] encryptedBytes = ecdsaSign.sign();
            //String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            //String sig = Base64.getEncoder().encodeToString(signature);

            logger.info("encryptedBytes:" + encryptedBytes);
            sig = Base64.getEncoder().encodeToString(encryptedBytes);
            logger.info("sig:" + sig);

            //Verifiy
            Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
            KeyFactory kf = KeyFactory.getInstance("EC");

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE8nJ2haR3Qj8K0INXUVwGOX/XLLnLD9ZxNurQOj3xOwKFX9Xoi5f+4viv2naJU98pXa0EwodFqhSoccvYYqVfBw=="));

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            ecdsaVerify.initVerify(publicKey);
            //ecdsaVerify.update("ora si, hola tu".getBytes("UTF-8"));
            ecdsaVerify.update(plainText.getBytes("UTF-8"));

            boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(sig));
            logger.info("Verify Result:" + result);

        }
        catch (Exception e){
            System.out.println("Cipher Exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sig;
    }

    /**
    //public static PrivateKey getPublicKey(String plainText, String publicKeyPath)
    public static PublicKey getPublicKey(String publicKeyPath)
            throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
        String key = Base64.getEncoder().encodeToString(keyBytes);
        //String key = Base64.getDecoder().decode(keyBytes);
        System.out.println("key:" + key);
        key = key.replace("-----BEGIN PUBLIC KEY-----\n", "");
        key = key.replace("-----END PUBLIC KEY-----", "");
        key = key.replaceAll("\n", "");
        key = key.replaceAll(" ", "");
        System.out.println("stripped key:" + key);

        //byte[] encodedPublicKey = Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));

        //KeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        PublicKey publicKey = kf.generatePublic(spec);

        System.out.println("publicKey:" + publicKey);

        return publicKey;
    }*/


    /**
    public static PrivateKey getPrivate(String filename)
            throws Exception {


        byte[] keyBytes = new String (Files.readAllBytes(Paths.get(filename)), private static String readFileAsString(InputStream key) throws IOException {
            int bufferSize = 1024;
            char[] buffer = new char[bufferSize];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(key, StandardCharsets.UTF_8);
            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }
            return out.toString();
        });

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        //KeyFactory kf = KeyFactory.getInstance("RSA");
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(spec);
    }*/

    /**
    private static String readFileAsString(InputStream key) throws IOException {
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
