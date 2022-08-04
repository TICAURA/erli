package com.aura.qamm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aura.qamm.dao.AuthDao;
import com.aura.qamm.dao.QuincenaDAO;
import com.aura.qamm.dto.QUser;
import com.aura.qamm.model.AuthUser;
import com.aura.qamm.transformer.QuincenaTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);


    @Autowired
    private AuthDao authDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username != null){
            AuthUser authUser = authDao.getUserByEmail(username);

            if(authUser==null){
                logger.info("User not found with username: " + username);
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            return new User(authUser.getUser(),authUser.getPassword(), new ArrayList<>());

        } else {
            logger.info("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
