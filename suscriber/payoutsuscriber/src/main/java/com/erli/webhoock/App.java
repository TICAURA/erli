package com.erli.webhoock;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.stream.StreamSupport;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        String hmacSHA256Value = "5b50d80c7dc7ae8bb1b1433cc0b99ecd2ac8397a555c6f75cb8a619ae35a0c35";
        String hmacSHA256Algorithm = "HmacSHA256";
        String data = "baeldung";
        //String data = "3rlisuscrib3er";
        String key = "123456";

        String result = hmacWithJava(hmacSHA256Algorithm, data, key);
        System.out.println(".........result:" + result);
        System.out.println("hmacSHA256Value:" + result);

        //assertEquals(hmacSHA256Value, result);

        System.out.println( "DONE" );
    }

    public static String hmacWithJava(String algorithm, String data, String key){
            //throws Exception {
        String hexString = null;
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            hexString = bytesToHex(mac.doFinal(data.getBytes()));
        }
        catch (Exception e){
            System.out.println("Excepcion " + e.getMessage());
            e.printStackTrace();
        }
        return hexString;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
