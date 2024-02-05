
package dao;

import entity.TipoUsuario;
import java.util.List;

public interface DaoTipoUsuario {
    List<TipoUsuario> tipoUsuarioCbo();
    String getMensaje();
}
