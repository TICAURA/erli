package com.aura.qamm.controller;

import com.aura.qamm.dto.AnticipoProgramado;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.service.AnticipoProgramadoService;
import com.aura.qamm.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("anticipo-programado")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class AnticipoProgramadoController {
    /**
    Logger logger = LoggerFactory.getLogger(AnticipoProgramadoController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private AnticipoProgramadoService anticipoProgramadoService;

    @GetMapping("")
    public ResponseEntity<Object> getAnticiposProgramados(@RequestAttribute("username") String email){
    try{
        int idColaborador = authService.getClaveColaborador(email);
        List<AnticipoProgramado> anticipos = anticipoProgramadoService.getAnticiposProgramados(idColaborador);
        return new ResponseEntity<>(anticipos,HttpStatus.OK);
    }catch(BusinessException e){
        return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}", HttpStatus.valueOf(e.getCode()));
    }catch (Exception e){
        logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
        return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnticipoProgramado(@RequestAttribute("username") String email,@PathVariable("id") int id){
        try{
            int idColaborador = authService.getClaveColaborador(email);
            AnticipoProgramado anticipoProgramado = anticipoProgramadoService.getAnticipoProgramado(idColaborador,id);
            return new ResponseEntity<>(anticipoProgramado,HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}", HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> postAnticiposProgramados(@RequestAttribute("username") String email, @RequestBody AnticipoProgramado anticipoProgramado){
        try{
            int idColaborador = authService.getClaveColaborador(email);
            int id = anticipoProgramadoService.postAnticipoProgramado(idColaborador,anticipoProgramado);
            return new ResponseEntity<Object>("{\"idAnticipoProgramado\":\""+id+"\"}", HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}", HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Object> putAnticiposProgramados(@RequestAttribute("username") String email, @RequestBody AnticipoProgramado anticipoProgramado){
        try{
            int idColaborador = authService.getClaveColaborador(email);
            anticipoProgramadoService.putAnticipoProgramado(idColaborador,anticipoProgramado);
            return new ResponseEntity<>("{}",HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}", HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnticipoProgramado(@RequestAttribute("username") String email,@PathVariable("id") int id){
        try{
            int idColaborador = authService.getClaveColaborador(email);
            anticipoProgramadoService.deleteAnticipoProgramado(idColaborador,id);
            return new ResponseEntity<>("{}",HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}", HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

}
