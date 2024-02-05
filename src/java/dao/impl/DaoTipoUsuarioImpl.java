
package dao.impl;

import dao.DaoTipoUsuario;
import entity.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;

public class DaoTipoUsuarioImpl implements DaoTipoUsuario{

    private final ConexionBD conexion;
    private String mensaje;

    public DaoTipoUsuarioImpl() {
        this.conexion = new ConexionBD();
    }
    
    @Override
    public List<TipoUsuario> tipoUsuarioCbo() {
        List<TipoUsuario> lista = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("idTipoUsuario, ")
                .append("tipo ")
                .append("FROM tipousuario");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                TipoUsuario tipoUsuario = new TipoUsuario();
                tipoUsuario.setIdTipoUsuario(rs.getInt(1));
                tipoUsuario.setTipo(rs.getString(2));
                lista.add(tipoUsuario);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return lista;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
