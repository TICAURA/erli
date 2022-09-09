package com.erli.tp.PaymentEngine.controller;

import com.erli.tp.PaymentEngine.model.TPRequest;
import com.erli.tp.PaymentEngine.service.PEService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class PEController {
    Logger logger = LoggerFactory.getLogger(PEController.class);

    @Autowired
    PEService peService;

    @PostMapping("executePayment")
    @CrossOrigin(origins = "*")
    public String executePayment(@RequestBody String tpRequest){
        logger.info("Execute payment:" + tpRequest);
        String result = peService.executeTPPayment(tpRequest);

        //return new Result(1,"User registered",null);
        return result;
    }
}
