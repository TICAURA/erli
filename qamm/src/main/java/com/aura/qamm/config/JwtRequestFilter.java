package com.aura.qamm.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aura.qamm.exception.BusinessException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aura.qamm.service.JwtUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try{

            logger.info("[JWT Request Filter] doFilterInternal ...");

            String username = null;
            Claims claims = null;
            //EXTRACT TOKEN
            final String requestTokenHeader = request.getHeader("Authorization");

            //CHECK IF THE TOKEN IS NOT NULL
            if(requestTokenHeader == null){
                logger.info("[JWT Request Filter] NULL token.");
                chain.doFilter(request, response);
                return;
            }

            //VALIDATE THE TOKEN
            try {
                username = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
                claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(requestTokenHeader).getBody();
                //String collId = (String) claims.get("collaboratorId");
                //logger.info("collId:" + collId);
            } catch (IllegalArgumentException e) {
                throw new BusinessException("[JWT Request Filter] Unable to get JWT token. ", HttpStatus.UNAUTHORIZED.value());
            } catch (MalformedJwtException e) {
                throw new BusinessException("[JWT Request Filter] JWT malformed token.", HttpStatus.UNAUTHORIZED.value());
            } catch (ExpiredJwtException e){
                throw new BusinessException("[JWT Request Filter] Expired token.", HttpStatus.UNAUTHORIZED.value());
            } catch (SignatureException e ){
                throw new BusinessException("[JWT Request Filter] Invalid token, hash is not matching.", HttpStatus.UNAUTHORIZED.value());
            }

            //CHECK IF THE USERNAME IS NOT NULL
            if (username == null) {
                throw new BusinessException("[JWT Request Filter] INVALID TOKEN.", HttpStatus.UNAUTHORIZED.value());
            }

            // ADD user to the security context.
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                // if token is valid configure Spring Security to manually set authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                logger.info("VALID USER "+ username);
            }

            //ADD username to every request to be accessible in the controller.
            request.setAttribute("username",username);
            logger.info("username:" + username);
            request.setAttribute("claims",claims);
            logger.info("claims:" + claims);
            logger.info("[JWT Request Filter] doFilterInternal.");
            chain.doFilter(request, response);

        }catch (BusinessException e){

            logger.error(e.getError());
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(401);
            PrintWriter out = response.getWriter();
            out.print("{\"error\":\""+e.getError()+"\"}");
            out.flush();

        }
    }
}
