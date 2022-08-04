package com.aura.qamm.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.aura.qamm.controller.QammController;
import com.aura.qamm.dao.AuthDao;
import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.QUser;
import com.aura.qamm.model.AuthUser;
import com.aura.qamm.transformer.QuincenaTransformer;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
    Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY =  15 * 60;

    public static final long MAIL_TOKEN_VALIDITY =  7 * 24 * 60 * 60;

    public static final long RESET_PASSWORD_TOKEN_VALIDITY = 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    AuthDao authDao;


    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        if(isTokenExpired(token)){return null;}
        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }




    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        /**
        Map<String, Object> claims = new HashMap<>();
        AuthUser authUser = authDao.getUserByEmail(userDetails.getUsername());

        claims.put("clientId",authUser.getClientId());
        claims.put("collaboratorId",authUser.getCollaboratorId());
        claims.put("onBoardDate",authUser.getOnboardDate());
        claims.put("personId",authUser.getPersonId());*/
        Map claims = setClaims(userDetails.getUsername());

        String subject = userDetails.getUsername();

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generatePDFToken(String email) {
        //Map<String, Object> claims = new HashMap<>();
        Map claims = setClaims(email);
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + MAIL_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateResetPasswordToken(String email) {
        //Map<String, Object> claims = new HashMap<>();
        Map claims = setClaims(email);
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + RESET_PASSWORD_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Map setClaims(String email){
        Map<String, Object> claims = new HashMap<>();
        AuthUser authUser = authDao.getUserByEmail(email);

        claims.put("clientId",authUser.getClientId());
        claims.put("collaboratorId",authUser.getCollaboratorId());
        claims.put("onBoardDate",authUser.getOnboardDate());
        claims.put("personId",authUser.getPersonId());

        return claims;
    }
}
