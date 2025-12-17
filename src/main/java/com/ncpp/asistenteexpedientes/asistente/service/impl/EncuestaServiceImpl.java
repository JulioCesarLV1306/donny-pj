package com.ncpp.asistenteexpedientes.asistente.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ncpp.asistenteexpedientes.asistente.database.AccesoAsistente;
import com.ncpp.asistenteexpedientes.asistente.entity.Encuesta;
import com.ncpp.asistenteexpedientes.asistente.service.EncuestaService;
import com.ncpp.asistenteexpedientes.util.LogDony;

public class EncuestaServiceImpl implements EncuestaService {

    @Override
    public void create(Encuesta encuesta) {
        Connection cn = null;
        try {
            cn=AccesoAsistente.getConnection();
            cn.setAutoCommit(false);
            String sql = "INSERT INTO encuesta(id_modulo,dni_sece, "+
            "nombre_sece, calificacion) VALUES(?,?,?,?) ";
                    
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setLong(1, encuesta.getIdModulo());
            pstm.setString(2, encuesta.getDniSece());
            pstm.setString(3, encuesta.getNombreSece());
            pstm.setInt(4, encuesta.getCalificacion());
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

    @Override
    public Encuesta findByDni(String dni) {
        Connection cn = null;
		Encuesta encuesta = null;
        try {
            cn=AccesoAsistente.getConnection();
			String sql = "SELECT id_encuesta, id_modulo, dni_sece, "+
            "nombre_sece, calificacion, fecha_hora FROM encuesta WHERE dni_sece = ? ";
			PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, dni);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
                encuesta=new Encuesta();
				encuesta.setIdEncuesta(rs.getLong("id_encuesta"));
                encuesta.setIdModulo(rs.getLong("id_modulo"));
                encuesta.setDniSece(rs.getString("dni_sece"));
                encuesta.setNombreSece(rs.getString("nombre_sece"));
                encuesta.setCalificacion(rs.getInt("calificacion"));
                encuesta.setFechaHora(rs.getDate("fecha_hora"));
			}
			rs.close();
            pstm.close();
        } catch (Exception e) {
			System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
			encuesta=null;
        } finally{
            try {
               cn.close();
            } catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
        }
		return encuesta;
    }
    
}
