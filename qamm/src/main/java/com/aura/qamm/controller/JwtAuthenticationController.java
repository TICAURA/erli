package com.aura.qamm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.aura.qamm.service.JwtUserDetailsService;

import com.aura.qamm.config.JwtTokenUtil;
import com.aura.qamm.model.JwtRequest;
import com.aura.qamm.model.JwtResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class JwtAuthenticationController {
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            logger.info("[QAMM] Authenticate ...");
            //AuthenticationManager usa el username para buscar el registro en la base datos por medio nuestra implementación de UserDetailsService (JwtUserDetailsService)
            //En caso de haber un error, lanza una excepción.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            logger.info("LOGGED USER: "+userDetails.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            logger.info("[QAMM] Authenticate.");
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (DisabledException e) {
            logger.error("DISABLED_USER");
            return new ResponseEntity<>("{\"error\":\"DISABLED_USER\"}", HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            logger.error("INVALID_CREDENTIALS"+e.getMessage());
            return new ResponseEntity<>("{\"error\":\"INVALID_CREDENTIALS\"}", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("ERROR_INESPERADO : "+e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
