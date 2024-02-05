
package dao;

import dto.UsuarioDTO;
import entity.Usuario;
import java.util.List;

public interface DaoUsuario {
    UsuarioDTO iniciarSesion(String correo, String clave);
    List<UsuarioDTO> obtenerUsuarios();
    Usuario obtenerUsuarioPorId(int id);
    String agregarUsuario(Usuario usuario);
    String actualizarUsuario(Usuario usuario);
    String eliminarUsuario(Integer id);
    String getMensaje();
}
