package com.aura.qamm.service;

import com.aura.qamm.dao.AuthDao;
import com.aura.qamm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

       @Autowired
       private AuthDao authDao;

       public int getClaveColaborador(String email) throws BusinessException {
           return authDao.getClaveColaborador(email);
       }
}
