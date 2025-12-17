package com.ncpp.asistenteexpedientes.asistente.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ncpp.asistenteexpedientes.asistente.database.AccesoAsistente;
import com.ncpp.asistenteexpedientes.asistente.entity.Bitacora;
import com.ncpp.asistenteexpedientes.asistente.service.BitacoraService;
import com.ncpp.asistenteexpedientes.util.LogDony;

public class BitacoraServiceImpl implements BitacoraService {

    @Override
    public void create(Bitacora bitacora) {  
        Connection cn = null;
        try {
            cn=AccesoAsistente.getConnection();
            cn.setAutoCommit(false);
            String sql = "INSERT INTO bitacora(ip_modulo,usuario_modulo,dni_sece,nombre_sece,codigo_accion,descripcion_accion)"
                    + " VALUES(?,?,?,?,?,?)";
                    
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, bitacora.getIpModulo());
            pstm.setString(2, bitacora.getUsuarioModulo());
            pstm.setString(3, bitacora.getDniSece());
            pstm.setString(4, bitacora.getNombreSece());
            pstm.setString(5, bitacora.getCodigoAccion());
            pstm.setString(6, bitacora.getDescripcionAccion());
            pstm.executeUpdate();
            cn.commit();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
        } finally{
            try {
               cn.close();
            } catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
        }
    }
   
}
