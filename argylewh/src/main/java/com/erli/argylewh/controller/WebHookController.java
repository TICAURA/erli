package com.erli.argylewh.controller;

import com.erli.argylewh.service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class WebHookController {
    Logger logger = LoggerFactory.getLogger(WebHookController.class);

    @Autowired
    WebHookService webHookService;

    @PostMapping("payout")
    @CrossOrigin(origins = "*")
    //public String getPayoutUpdate(@RequestBody String payoutUpdate){
    public String getPayout(@RequestBody String reqBody,
                            @RequestHeader Map<String, String> reqHeaders){

        reqHeaders.forEach((key, value) -> {
            logger.info(String.format("Header '%s' = %s", key, value));
        });

        logger.info("Incoming Argyle Payout Update");
        logger.info("payoutUpdate:" + reqBody.toString());
        if (webHookService.processPayOutUpdate(reqBody.toString())) {
            return "{\"SC\":\"200\",\"SM\":\"Payout update received\"}";
        }
        else return "{\"SC\":\"400\",\"SM\":\"Payout update process failure\"}";
        //return "OK";
    }
}
