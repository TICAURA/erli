package com.aura.qamm.controller;

import com.aura.qamm.dto.Notification;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @GetMapping("getNotifications")
    public ResponseEntity<Object> getNotifications(@RequestAttribute("username") String email){
        try{
            List<Notification> notifications = notificationService.getNotifications(email);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getNotification/{uuid}")
    public ResponseEntity<Object> getNotification(@PathVariable("uuid") String uuid,@RequestAttribute("username") String email){
        try{
            Notification notification = notificationService.getNotification(email,uuid);
            return  new ResponseEntity<>(notification,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/updateSeenNotification/{uuid}")
    public ResponseEntity<Object> updateNotification(@PathVariable("uuid") String uuid,@RequestAttribute("username") String email){
        try{
            notificationService.updateNotification(email,uuid);
            return new ResponseEntity<>("",HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleteSeenNotification/{uuid}")
    public ResponseEntity<Object> borrarNotification(@PathVariable("uuid") String uuid,@RequestAttribute("username") String email){
        try{
            notificationService.deleteNotification(email,uuid);
            return new ResponseEntity<>("",HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
