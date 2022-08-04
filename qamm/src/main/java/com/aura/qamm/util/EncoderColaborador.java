package com.aura.qamm.util;

import java.util.Base64;
//import java.util.Base64.Encoder;

public interface EncoderColaborador {
    public static String encode(String original){
        return Base64.getEncoder().encodeToString(original.getBytes());
    }
}
