package com.ncpp.asistenteexpedientes.asistente.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ncpp.asistenteexpedientes.asistente.database.AccesoAsistente;
import com.ncpp.asistenteexpedientes.asistente.entity.Estadisticas;
import com.ncpp.asistenteexpedientes.asistente.service.EstadisticasService;
import com.ncpp.asistenteexpedientes.util.LogDony;
import com.ncpp.asistenteexpedientes.util.Util;

public class EstadisticasServiceImpl implements EstadisticasService {

    private final String strActas = "actas";
    private final String strResoluciones = "resoluciones";
    private final String strDocumentos = "documentos";
    private final String strVideos = "videos";
    private final String strHojas = "hojas";
    private final String strBytes = "bytes";
    private final String strPenal = "penal";
    private final String strCivil = "civil";
    private final String strFamilia = "familia";
    private final String strLaboral = "laboral";

    @Override
    public void aumentarActas(long idModulo, int cantidad) {
        aumentarCampo(strActas, idModulo, cantidad);
    }

    @Override
    public void aumentarResoluciones(long idModulo, int cantidad) {
        aumentarCampo(strResoluciones, idModulo, cantidad);
    }

    @Override
    public void aumentarDocumentos(long idModulo, int cantidad) {
        aumentarCampo(strDocumentos, idModulo, cantidad);
    }

    @Override
    public void aumentarVideos(long idModulo, int cantidad) {
        aumentarCampo(strVideos, idModulo, cantidad);
    }

    @Override
    public void aumentarHojas(long idModulo, int cantidad) {
        aumentarCampo(strHojas, idModulo, cantidad);
    }

    @Override
    public void aumentarBytes(long idModulo, long cantidad) {
        aumentarCampo(strBytes, idModulo, cantidad);
    }

    @Override
    public void aumentarPenal(long idModulo, int cantidad) {
        aumentarCampo(strPenal, idModulo, cantidad);
    }

    @Override
    public void aumentarLaboral(long idModulo, int cantidad) {
        aumentarCampo(strLaboral, idModulo, cantidad);
    }

    @Override
    public void aumentarCivil(long idModulo, int cantidad) {
        aumentarCampo(strCivil, idModulo, cantidad);
    }

    @Override
    public void aumentarFamilia(long idModulo, int cantidad) {
        aumentarCampo(strFamilia, idModulo, cantidad);
    }

    private void aumentarCampo(String nombreCampo, long idModulo, long cantidad) {
        Connection cn = null;
        try {
            Estadisticas estadisticas = getActualObject(idModulo);
            if (estadisticas != null && estadisticas.getIdEstadistica() != 0) {
                cn = AccesoAsistente.getConnection();
                cn.setAutoCommit(false);
                String sql = "UPDATE estadisticas SET " + nombreCampo + " =  ? WHERE id_estadistica = ? ";
                PreparedStatement pstm = cn.prepareStatement(sql);
                switch (nombreCampo) {
                    case strActas:
                        pstm.setLong(1, estadisticas.getActas() + cantidad);
                        break;
                    case strResoluciones:
                        pstm.setLong(1, estadisticas.getResoluciones() + cantidad);
                        break;
                    case strVideos:
                        pstm.setLong(1, estadisticas.getVideos() + cantidad);
                        break;
                    case strDocumentos:
                        pstm.setLong(1, estadisticas.getDocumentos() + cantidad);
                        break;
                    case strHojas:
                        pstm.setLong(1, estadisticas.getHojas() + cantidad);
                        break;
                    case strBytes:
                        pstm.setLong(1, estadisticas.getBytes() + cantidad);
                        break;
                    case strPenal:
                        pstm.setLong(1, estadisticas.getPenal() + cantidad);
                        break;
                    case strCivil:
                        pstm.setLong(1, estadisticas.getCivil() + cantidad);
                        break;
                    case strLaboral:
                        pstm.setLong(1, estadisticas.getLaboral() + cantidad);
                        break;
                    case strFamilia:
                        pstm.setLong(1, estadisticas.getFamilia() + cantidad);
                        break;
                    default:
                        break;
                }

                pstm.setLong(2, estadisticas.getIdEstadistica());
                pstm.executeUpdate();
                cn.commit();
                pstm.close();
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
    }

    private Estadisticas getActualObject(long idModulo) {
        Estadisticas estadisticas = findByDate(idModulo);
        if (estadisticas == null || estadisticas.getIdEstadistica() == 0) {
            estadisticas = create(idModulo);
        }
        return estadisticas;
    }

    private Estadisticas findByDate(long idModulo) {
        Connection cn = null;
        Estadisticas estadisticas = new Estadisticas();
        try {
            cn = AccesoAsistente.getConnection();
            String sql = "SELECT id_estadistica,actas,resoluciones,documentos, "+
            " videos,hojas, bytes, penal, laboral, familia, civil FROM estadisticas WHERE id_modulo = ? "
                    + " AND fecha = ?";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setLong(1, idModulo);
            pstm.setDate(2, getFechaSQL());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                estadisticas.setIdEstadistica(rs.getInt("id_estadistica"));
                estadisticas.setActas(rs.getInt(strActas));
                estadisticas.setResoluciones(rs.getInt(strResoluciones));
                estadisticas.setDocumentos(rs.getInt(strDocumentos));
                estadisticas.setVideos(rs.getInt(strVideos));
                estadisticas.setHojas(rs.getInt(strHojas));
                estadisticas.setBytes(rs.getLong(strBytes));
                estadisticas.setPenal(rs.getInt(strPenal));
                estadisticas.setLaboral(rs.getInt(strLaboral));
                estadisticas.setFamilia(rs.getInt(strFamilia));
                estadisticas.setCivil(rs.getInt(strCivil));
                estadisticas.setIdModulo(idModulo);
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName() + " - ERROR: " + e);
            estadisticas = null;
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
                System.out.println(e);
                LogDony.write(this.getClass().getName() + " - ERROR: " + e);
                e.printStackTrace();
            }
        }
        return estadisticas;
    }

    private Estadisticas create(long idModulo) {
        Connection cn = null;
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setActas(0);
        estadisticas.setDocumentos(0);
        estadisticas.setResoluciones(0);
        estadisticas.setVideos(0);
        estadisticas.setHojas(0);
        estadisticas.setBytes(0);
        estadisticas.setPenal(0);
        estadisticas.setLaboral(0);
        estadisticas.setCivil(0);
        estadisticas.setFamilia(0);
        estadisticas.setIdModulo(idModulo);
        estadisticas.setFecha(getFechaSQL());
        try {
            cn = AccesoAsistente.getConnection();
            cn.setAutoCommit(false);
            String sql = "INSERT INTO estadisticas(id_modulo,actas,resoluciones,documentos,videos,hojas,fecha, bytes, penal, laboral, civil, familia)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setLong(1, estadisticas.getIdModulo());
            pstm.setInt(2, estadisticas.getActas());
            pstm.setInt(3, estadisticas.getResoluciones());
            pstm.setInt(4, estadisticas.getDocumentos());
            pstm.setInt(5, estadisticas.getVideos());
            pstm.setInt(6, estadisticas.getHojas());
            pstm.setDate(7, estadisticas.getFecha());
            pstm.setLong(8, estadisticas.getBytes());
            pstm.setInt(9, estadisticas.getPenal());
            pstm.setInt(10, estadisticas.getLaboral());
            pstm.setInt(11, estadisticas.getCivil());
            pstm.setInt(12, estadisticas.getFamilia());

            pstm.executeUpdate();
            estadisticas.setIdEstadistica(Util.getMaxId("id_estadistica", "estadisticas", cn));
            cn.commit();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            LogDony.write(this.getClass().getName() + " - ERROR: " + e);
            estadisticas = null;
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
                System.out.println(e);
                LogDony.write(this.getClass().getName() + " - ERROR: " + e);
                e.printStackTrace();
            }
        }
        return estadisticas;
    }

    private java.sql.Date getFechaSQL() {
        java.util.Date utilDate = new java.util.Date();
        return new java.sql.Date(utilDate.getTime());
    }

}
