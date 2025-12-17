package com.ncpp.asistenteexpedientes.asistente.entity;

import java.util.Date;

import lombok.Data;
@Data
public class Encuesta {
    private long idEncuesta;
    private long idModulo; 
    private String dniSece;
    private String nombreSece;
    private int calificacion;
    private Date fechaHora;
}
