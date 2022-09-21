package com.aura.qamm.service;

import com.aura.qamm.dao.AuthDao;
import com.aura.qamm.dao.PromocionDao;
import com.aura.qamm.dto.ImporteAnticipo;
import com.aura.qamm.dto.Promocion;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.util.PromoENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PromocionService {

    @Autowired
    private PromocionDao promocionDao;

    /**
    public List<Promocion> getPromociones() throws BusinessException{
        return promocionDao.getPromociones();
    }
    public Promocion getPromocion(int idPromocion) throws BusinessException{
       return promocionDao.getPromocion(idPromocion);
    }
    public List<Promocion> getPromocionesPorColaborador(int claveColaborador) throws BusinessException{
        return promocionDao.getPromocionPorColaborador(claveColaborador);
    }*/

    /**
    public List<Promocion> getPromocionesPorColaboradorTodas(int claveColaborador) throws BusinessException{
        return promocionDao.getPromocionPorColaboradorUtilizadas(claveColaborador);
    }*/


    /**
    public void canjearPromocion(int claveColaborador, String uuid) throws BusinessException{
        //obtener uuid promocion
        Promocion promocion = promocionDao.obtenerPorUuid(uuid);
        if(promocion == null){
            throw new BusinessException("El código que ingresó no está asociado a ninguna promoción.",404);
        }
        //validar fecha inicio y fin
        Date fechaActual = new Date();
        boolean inicio = promocion.getFechaInicio().before(fechaActual);
        boolean fin = promocion.getFechaFin().after(fechaActual);
        if(! ( inicio && fin) ){
            throw new BusinessException("El código que ingresó está asignado a una promoción que ya expiró o no ha empezado.",401);
        }

        //ver si ya lo tiene el colaborador
        if(promocionDao.validarPromocion(promocion.getIdPromocion(),claveColaborador)!=null){
            throw new BusinessException("Usted ya ingresó el código previamente.",401);
        }

        promocionDao.canjearPromocion(promocion.getIdPromocion(),claveColaborador);
    }*/
    /**
    public void cobrarPromocion(ImporteAnticipo importeAnticipo) throws BusinessException {
        //validar que la promocion exista y que este en 0
        Promocion promocion = promocionDao.validarPromocion(importeAnticipo.getIdPromocion(),importeAnticipo.getIdColaborador());

        if(promocion==null){
            throw new BusinessException("Usted no tiene asignada esta promoción.",401);
        }

        if(promocion.isCobrado()){
            throw new BusinessException("Usted ya utilizó esta promoción, ya no puede recibir más beneficios de ella.",401);
        }

        if(promocion.getTipoPromocion()== PromoENUM.MONTO.getId()){
            double monto  = Double.parseDouble(importeAnticipo.getImporteSolicitado())+promocion.getMontoBeneficio();
            importeAnticipo.setImporteSolicitado(monto+"");
            importeAnticipo.setMontoPromocion((double) promocion.getMontoBeneficio());
            importeAnticipo.setIdPromocion(promocion.getIdPromocion());
        }
        else if(promocion.getTipoPromocion()==PromoENUM.PORCENTAJE.getId()){

            double monto  = Double.parseDouble(importeAnticipo.getImporteSolicitado());
            if(monto<promocion.getMontoPorcentajeMaximo()){
                importeAnticipo.setImporteSolicitado((monto* (1 + promocion.getPorcentajeBeneficio().doubleValue()))+"");
                importeAnticipo.setMontoPromocion(monto* promocion.getPorcentajeBeneficio().doubleValue());
                importeAnticipo.setIdPromocion(promocion.getIdPromocion());
            }

        }
        else{
            return;
        }

        promocionDao.ejecutarPromocion(importeAnticipo.getIdPromocion(),importeAnticipo.getIdColaborador());


    }*/



}
