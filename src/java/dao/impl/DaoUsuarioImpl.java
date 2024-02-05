package dao.impl;

import dao.DaoUsuario;
import dto.UsuarioDTO;
import entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;

public class DaoUsuarioImpl implements DaoUsuario {

    private final ConexionBD conexion;
    private String mensaje;

    public DaoUsuarioImpl() {
        this.conexion = new ConexionBD();
    }

    @Override
    public UsuarioDTO iniciarSesion(String correo, String clave) {
        UsuarioDTO usuario = new UsuarioDTO();
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("u.idUsuario, ")
                .append("u.nombres, ")
                .append("u.correo, ")
                .append("t.tipo ")
                .append("FROM usuario u ")
                .append("INNER JOIN tipoUsuario t ON u.tipoUsuario = t.idTipoUsuario ")
                .append("WHERE u.correo = ? ")
                .append("AND (AES_DECRYPT(u.clave, ? ) = ? )");

        try (Connection cn = conexion.conexionBD()) {
            // Establecer la conexión con la base de datos y preparar la consulta SQL
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setString(1, correo);
            ps.setString(2, clave);
            ps.setString(3, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                    usuario.setNombres(rs.getString(2));
                    usuario.setCorreo(rs.getString(3));
                    usuario.setTipoUsuario(rs.getString(4));
                } else {
                    usuario = null;
                    mensaje = "Usuario no registrado";
                }
            } catch (Exception e) {
                mensaje = e.getMessage();
            }
        } catch (Exception e) {
            mensaje = "Error: " + e.getMessage();
        }
        return usuario;
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        List<UsuarioDTO> lista = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("u.idUsuario, ")
                .append("u.nombres, ")
                .append("u.correo, ")
                .append("t.tipo ")
                .append("FROM usuario u ")
                .append("INNER JOIN tipoUsuario t ON u.tipoUsuario = t.idTipoUsuario ");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setIdUsuario(rs.getInt(1));
                usuarioDTO.setNombres(rs.getString(2));
                usuarioDTO.setCorreo(rs.getString(3));
                usuarioDTO.setTipoUsuario(rs.getString(4));
                lista.add(usuarioDTO);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return lista;
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario = new Usuario();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idUsuario, ")
                .append("nombres, ")
                .append("apellidos, ")
                .append("correo, ")
                .append("tipoUsuario ")
                .append("FROM usuario ")
                .append("WHERE idUsuario = ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                    usuario.setNombres(rs.getString(2));
                    usuario.setApellidos(rs.getString(3));
                    usuario.setCorreo(rs.getString(4));
                    usuario.setTipoUsuario(rs.getInt(5));
                } else {
                    usuario = null;
                }
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return usuario;
    }

    @Override
    public String agregarUsuario(Usuario usuario) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO usuario ( ")
                .append("nombres, ")
                .append("apellidos, ")
                .append("correo, ")
                .append("clave, ")
                .append("tipoUsuario ")
                .append(") VALUES (?, ?, ?, AES_ENCRYPT(?, ?), ?) ");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getClave());
            ps.setString(5, usuario.getClave()); // Clave de encriptación
            ps.setInt(6, usuario.getTipoUsuario());
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
    public String actualizarUsuario(Usuario usuario) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE usuario SET ")
                .append("nombres = ?, ")
                .append("apellidos = ?, ")
                .append("correo = ?, ")
                .append("tipoUsuario = ? ")
                .append("WHERE idUsuario = ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getCorreo());
            ps.setInt(4, usuario.getTipoUsuario());
            ps.setInt(5, usuario.getIdUsuario());
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
    public String eliminarUsuario(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
