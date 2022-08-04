package com.aura.qamm.dto;

import lombok.Data;

import java.util.Date;

public @Data
class AnticipoProgramado {

    private int idProgramacionAnticipo;
    private Date fechaAnticipo;
    private Date fechaSiguienteAnticipo;
    private Date fechaCreacion;
    private int periodo;
}
