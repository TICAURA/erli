package com.aura.qamm.service;

import com.aura.qamm.dao.AnticipoProgramadoDao;
import com.aura.qamm.dto.AnticipoProgramado;
import com.aura.qamm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnticipoProgramadoService {
    //TODO log a todos mis endpoints
    @Autowired
    private AnticipoProgramadoDao anticipoProgramadoDao;

    public List<AnticipoProgramado> getAnticiposProgramados(int idColaborador) throws BusinessException {
        return anticipoProgramadoDao.getAnticiposProgramados(idColaborador);
    }

    public AnticipoProgramado getAnticipoProgramado(int idColaborador,int anticipoProgramadoId) throws BusinessException {
        return anticipoProgramadoDao.getAnticipoProgramado(idColaborador,anticipoProgramadoId);
    }
    public int postAnticipoProgramado(int idColaborador, AnticipoProgramado anticipoProgramado)throws BusinessException{
        //TODO validar datos
        if(anticipoProgramado.getFechaAnticipo() == null){
            throw  new BusinessException("Error, porfavor ingrese una fecha valida para la dispersión del anticipo.",406);
        }
        return anticipoProgramadoDao.postAnticipoProgramado(idColaborador,anticipoProgramado);
    }
    public void putAnticipoProgramado(int idColaborador,AnticipoProgramado anticipoProgramado) throws BusinessException{


        if(anticipoProgramado.getFechaAnticipo() == null){
            throw  new BusinessException("Error, porfavor ingrese una fecha valida para la dispersión del anticipo.",406);
        }
         anticipoProgramadoDao.putAnticipoProgramado(idColaborador,anticipoProgramado);
    }
    public void deleteAnticipoProgramado(int idColaborador, int anticipoProgramadoId) throws BusinessException{
        anticipoProgramadoDao.deleteAnticipoProgramado(idColaborador,anticipoProgramadoId);
    }
}
