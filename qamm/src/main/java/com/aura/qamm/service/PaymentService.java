package com.aura.qamm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import com.aura.payment.PaymentEngine;


@Service
public class PaymentService {

    public String executePayment(String jsonString, String type){
        String response = null;
        PaymentEngine paymentEngine = new PaymentEngine();
        response = paymentEngine.processPayment(jsonString,type);
        return response;
    }

}
