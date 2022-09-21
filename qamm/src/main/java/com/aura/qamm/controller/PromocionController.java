package com.aura.qamm.controller;

import com.aura.qamm.dto.ImporteAnticipo;
import com.aura.qamm.dto.Promocion;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.service.AuthService;
import com.aura.qamm.service.DispersorService;
import com.aura.qamm.service.PromocionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("promociones")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class PromocionController {
    /**
    @Autowired
    private PromocionService promocionService;

    @Autowired
    private DispersorService dispersorService;

    @Autowired
    private AuthService authService;

    Logger logger = LoggerFactory.getLogger(PromocionController.class);

    @GetMapping("")
    public ResponseEntity<Object> getNotifications(){
        try{
            List<Promocion> promociones = promocionService.getPromociones();
            return new ResponseEntity<>(promociones, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idPromocion}")
    public ResponseEntity<Object> getNotification(@RequestAttribute("username") String email,@PathVariable("idPromocion") int idPromocion){
        try{
            Promocion promo = promocionService.getPromocion(idPromocion);
            return new ResponseEntity<>(promo, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("colaborador")
    public ResponseEntity<Object> getPromoColaborador(@RequestAttribute("username") String email){
        try{
            List<Promocion> promociones = promocionService.getPromocionesPorColaborador(authService.getClaveColaborador(email));
            return new ResponseEntity<>(promociones, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("colaborador/todas")
    public ResponseEntity<Object> getPromoColaboradorTodas(@RequestAttribute("username") String email){
        try{
            List<Promocion> promociones = promocionService.getPromocionesPorColaboradorTodas(authService.getClaveColaborador(email));
            return new ResponseEntity<>(promociones, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("canjear/{uuid}")
    public ResponseEntity<Object> canjearNotificion(@RequestAttribute("username") String email,@PathVariable("uuid") String uuid){
        try{
            promocionService.canjearPromocion(authService.getClaveColaborador(email),uuid);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("confirmaSolicitudAnticipo")
    @CrossOrigin(origins = "*")
    public Object confirmaSolicitudAnticipo(@RequestBody ImporteAnticipo importeAnticipo, @RequestAttribute("username") String email){

        try{
            int idColaborador = authService.getClaveColaborador(email);
            importeAnticipo.setIdColaborador(idColaborador);
            return dispersorService.ejecutarPromocion(importeAnticipo,email);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    */
}
