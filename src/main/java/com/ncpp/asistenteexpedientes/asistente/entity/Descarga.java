package com.ncpp.asistenteexpedientes.asistente.entity;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Descarga implements Serializable{

    private static final long serialVersionUID = 746237126088051312L;
     
    private long idDescarga;
    private String keyDescarga;
    private String estado;
    private int porcentajeDescarga;
    private int conteoDescarga;
    private int totalDescarga;
    private int porcentajeCopia;
    private int conteoCopia;
    private int totalCopia;
    private String mensajeFinal;

}
