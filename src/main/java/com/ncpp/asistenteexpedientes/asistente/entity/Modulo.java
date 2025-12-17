package com.ncpp.asistenteexpedientes.asistente.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="modulo")
public class Modulo implements Serializable{

    private static final long serialVersionUID = 746237126088051312L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_modulo")
    private long idModulo;

    @Column(name="pc_ip")
    private String pcIp;

    @Column(name="pc_usuario")
    private String pcUsuario;

    @Column(name="pc_clave")
    private String pcClave;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="ubicacion")
    private String ubicacion;

    @Column(name="estado")
    private int estado;
    
}
