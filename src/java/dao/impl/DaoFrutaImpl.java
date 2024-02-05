package dao.impl;

import dao.DaoFruta;
import entity.Fruta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;

public class DaoFrutaImpl implements DaoFruta {

    private final ConexionBD conexion;
    private String mensaje;

    public DaoFrutaImpl() {
        this.conexion = new ConexionBD();
    }

    @Override
    public List<Fruta> obtenerFrutas(int maxResultados, int primerResultado) {
        List<Fruta> lista = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("idFruta, ")
                .append("nombre, ")
                .append("descripcion, ")
                .append("cantidadKilogramos, ")
                .append("precioPorKilogramo, ")
                .append("disponible ")
                .append("FROM fruta ")
                .append("LIMIT ? OFFSET ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setInt(1, maxResultados);
            ps.setInt(2, primerResultado);
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Fruta fruta = new Fruta();
                fruta.setIdFruta(rs.getInt(1));
                fruta.setNombre(rs.getString(2));
                fruta.setDescripcion(rs.getString(3));
                fruta.setCantidadKilogramos(rs.getDouble(4));
                fruta.setPrecioPorKilogramo(rs.getDouble(5));
                fruta.setDisponible(rs.getBoolean(6));
                lista.add(fruta);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return lista;
    }

    @Override
    public Fruta obtenerFrutaPorId(int id) {
        Fruta fruta = new Fruta();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idFruta, ")
                .append("nombre, ")
                .append("descripcion, ")
                .append("cantidadKilogramos, ")
                .append("precioPorKilogramo, ")
                .append("disponible ")
                .append("FROM fruta ")
                .append("WHERE idFruta = ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fruta.setIdFruta(rs.getInt(1));
                    fruta.setNombre(rs.getString(2));
                    fruta.setDescripcion(rs.getString(3));
                    fruta.setCantidadKilogramos(rs.getDouble(4));
                    fruta.setPrecioPorKilogramo(rs.getDouble(5));
                    fruta.setDisponible(rs.getBoolean(6));
                } else {
                    fruta = null;
                }
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return fruta;
    }

    @Override
    public String agregarFruta(Fruta fruta) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO fruta ( ")
                .append("nombre, ")
                .append("descripcion, ")
                .append("cantidadKilogramos, ")
                .append("precioPorKilogramo ")
                .append(") VALUES (?,?,?,?) ");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setString(1, fruta.getNombre());
            ps.setString(2, fruta.getDescripcion());
            ps.setDouble(3, fruta.getCantidadKilogramos());
            ps.setDouble(4, fruta.getPrecioPorKilogramo());
            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                mensaje = "cero filas insertadas";
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    @Override
    public String actualizarFruta(Fruta fruta) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE fruta SET ")
                .append("nombre = ?, ")
                .append("descripcion = ?, ")
                .append("cantidadKilogramos = ?, ")
                .append("precioPorKilogramo = ?, ")
                .append("disponible = ? ")
                .append("WHERE idFruta = ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setString(1, fruta.getNombre());
            ps.setString(2, fruta.getDescripcion());
            ps.setDouble(3, fruta.getCantidadKilogramos());
            ps.setDouble(4, fruta.getPrecioPorKilogramo());
            ps.setBoolean(5, fruta.getDisponible());
            ps.setInt(6, fruta.getIdFruta());
            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                mensaje = "No se pudo actualizar";
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    @Override
    public String eliminarFruta(Integer id) {
        String mensaje = null;
        String query = "DELETE FROM fruta WHERE idFruta = ?";
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setInt(1, id);
            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                mensaje = "ID: " + id + " no existe";
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    @Override
    public Integer obtenerTotalDeFrutas() {
        Integer total = null;
        String query = "SELECT COUNT(*) FROM fruta";
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return total;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

    @Override
    public List<Fruta> obtenerTodasLasFrutas() {
        List<Fruta> lista = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("idFruta, ")
                .append("nombre, ")
                .append("descripcion, ")
                .append("cantidadKilogramos, ")
                .append("precioPorKilogramo, ")
                .append("disponible ")
                .append("FROM fruta ");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Fruta fruta = new Fruta();
                fruta.setIdFruta(rs.getInt(1));
                fruta.setNombre(rs.getString(2));
                fruta.setDescripcion(rs.getString(3));
                fruta.setCantidadKilogramos(rs.getDouble(4));
                fruta.setPrecioPorKilogramo(rs.getDouble(5));
                fruta.setDisponible(rs.getBoolean(6));
                lista.add(fruta);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return lista;
    }
}
