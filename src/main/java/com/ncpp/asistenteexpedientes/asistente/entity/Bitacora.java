package com.ncpp.asistenteexpedientes.asistente.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bitacora implements Serializable{

    private static final long serialVersionUID = 746237126088051312L;

    private long idBitacora;
    private String ipModulo;
    private String usuarioModulo;
    private String dniSece;
    private String nombreSece;
    private String codigoAccion;
    private String descripcionAccion;
    private Date fechaHora;
    
}
