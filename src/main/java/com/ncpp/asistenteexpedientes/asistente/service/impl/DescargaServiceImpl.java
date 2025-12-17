package com.ncpp.asistenteexpedientes.asistente.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ncpp.asistenteexpedientes.asistente.database.AccesoAsistente;
import com.ncpp.asistenteexpedientes.asistente.entity.Descarga;
import com.ncpp.asistenteexpedientes.asistente.service.DescargaService;
import com.ncpp.asistenteexpedientes.util.LogDony;
import com.ncpp.asistenteexpedientes.util.Util;


public class DescargaServiceImpl  implements DescargaService{
   

    public final static String ESTADO_DESCARGANDO = "descargando";
    public final static String ESTADO_ERROR_DESCARGA = "error-descarga";
    public final static String ESTADO_COMPLETO_DESCARGA = "completo-descarga";
    public final static String ESTADO_FALTA_ESPACIO = "falta-espacio";

	//@Override
	private Descarga create(String keyDescarga) {
		Connection cn = null;
		Descarga descarga=new Descarga();
        try {
            cn=AccesoAsistente.getConnection();
            cn.setAutoCommit(false);
            String sql = "INSERT INTO descarga(key_descarga, estado,  "
            +"porcentaje_descarga, conteo_descarga, total_descarga, porcentaje_copia, conteo_copia, total_copia ) VALUES(?,'creado',0,0,0,0,0,0) ";
                    
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, keyDescarga);
            pstm.executeUpdate();

			int idDescarga = Util.getMaxId("id_descarga", "descarga", cn);
			descarga.setIdDescarga(idDescarga);
			descarga.setKeyDescarga(keyDescarga);
            cn.commit();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
			descarga=null;
        } finally{
            try {
               cn.close();
            } catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
        }
		return descarga;
	}

	@Override
	public Descarga createOrUpdateDescarga(String keyDescarga, String estado , int archivosDescargados, int totalArchivos) {
		Descarga descarga=find(keyDescarga);
		if(descarga==null || descarga.getIdDescarga() == 0)  {
            descarga=create(keyDescarga);
        }else{
            descarga=updateDescarga(descarga, estado,  archivosDescargados, totalArchivos);
        }
		return descarga;
	}

    @Override
	public Descarga find(String keyDescarga) {
		Connection cn = null;
		Descarga descarga = new Descarga();
        try {
            cn=AccesoAsistente.getConnection();
			String sql = "SELECT id_descarga, key_descarga, estado, porcentaje_descarga, conteo_descarga, total_descarga, porcentaje_copia, conteo_copia, total_copia , mensaje_final FROM descarga WHERE key_descarga = ? ";
			PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, keyDescarga);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				descarga.setIdDescarga(rs.getLong("id_descarga"));
				descarga.setKeyDescarga(rs.getString("key_descarga"));
                descarga.setEstado(rs.getString("estado"));
                descarga.setPorcentajeDescarga(rs.getInt("porcentaje_descarga"));
                descarga.setConteoDescarga(rs.getInt("conteo_descarga"));
                descarga.setTotalDescarga(rs.getInt("total_descarga"));
                descarga.setPorcentajeCopia(rs.getInt("porcentaje_copia"));
                descarga.setConteoCopia(rs.getInt("conteo_copia"));
                descarga.setTotalCopia(rs.getInt("total_copia"));
                descarga.setMensajeFinal(rs.getString("mensaje_final"));
			}
			rs.close();
            pstm.close();
        } catch (Exception e) {
			System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
			descarga=null;
        } finally{
            try {
               cn.close();
            } catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
        }
		return descarga;
	}

    @Override
    public Descarga addMensajeFinal(String keyDescarga, String mensajeFinal){
        return null;
    }

    
    private Descarga updateDescarga(Descarga descarga, String estado,  int conteoDescarga, int totalDescarga) {
        Connection cn = null;
        //Descarga descarga = new Descarga();
        try {
            if(descarga!=null && descarga.getIdDescarga() != 0) {
                cn = AccesoAsistente.getConnection();
                cn.setAutoCommit(false);
                String sql = "UPDATE descarga SET estado = ? , porcentaje_descarga =  ? , conteo_descarga = ? , total_descarga = ? "
                +"WHERE id_descarga = ? ";
                PreparedStatement pstm = cn.prepareStatement(sql);
                pstm.setString(1, estado);
                pstm.setInt(2, Util.getPorcentajeDescarga(conteoDescarga, totalDescarga));
                pstm.setInt(3, conteoDescarga);
                pstm.setInt(4, totalDescarga);
                pstm.setLong(5, descarga.getIdDescarga());
                pstm.executeUpdate();
                cn.commit();
                pstm.close();

                descarga.setEstado(estado);
                descarga.setPorcentajeDescarga(Util.getPorcentajeDescarga(conteoDescarga, totalDescarga));
                descarga.setConteoDescarga(conteoDescarga);
                descarga.setTotalDescarga(totalDescarga);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName() + " - ERROR: " + e);
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                LogDony.write(this.getClass().getName() + " - ERROR: " + e);
            }
        }
        return descarga;
    }

}
