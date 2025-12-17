package com.ncpp.asistenteexpedientes.asistente.entity;
import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class Estadisticas implements Serializable{

    private static final long serialVersionUID = 746237126088051312L;

    private long idEstadistica;
    private long idModulo;
    private int actas;
    private int resoluciones;
    private int documentos;
    private int videos;
    private int hojas;
    private long bytes;
    private int penal;
    private int laboral;
    private int civil;
    private int familia;
    private Date fecha;

}
