package web.validator;

import dao.DaoTipoUsuario;
import dao.DaoUsuario;
import dao.impl.DaoTipoUsuarioImpl;
import dao.impl.DaoUsuarioImpl;
import dto.UsuarioDTO;
import entity.TipoUsuario;
import entity.Usuario;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UsuarioValidator {

    private final HttpServletRequest request;
    private final DaoUsuario daoUsuario;

    public UsuarioValidator(HttpServletRequest request) {
        this.request = request;
        this.daoUsuario = new DaoUsuarioImpl();
    }

    public String usuarioLi() {
        String result = null;
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        UsuarioDTO usuario = daoUsuario.iniciarSesion(correo, clave);
        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
        } else {
            result = daoUsuario.getMensaje();
        }
        return result;
    }

    public String usuarioLo() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Sesión cerrada correctamente";
    }

    public String usuarioSel() {
        String result = null;
        List<UsuarioDTO> lista = daoUsuario.obtenerUsuarios();
        if (lista != null) {
            request.setAttribute("lista", lista);
        } else {
            result = daoUsuario.getMensaje();
        }
        return result;
    }

    public String usuarioGet() {
        String result = null;
        String idUsuarioAux = request.getParameter("id");
        Integer idUsuario = Integer.valueOf(idUsuarioAux);
        Usuario usuario = daoUsuario.obtenerUsuarioPorId(idUsuario);

        DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuarioImpl();
        List<TipoUsuario> tipoUsuarios = daoTipoUsuario.tipoUsuarioCbo();

        if (usuario != null) {
            request.setAttribute("usuario", usuario);
            request.setAttribute("tipoUsuarios", tipoUsuarios);
        } else {
            result = daoUsuario.getMensaje();
        }
        return result;
    }

    public String usuarioInsUpd(boolean agreActu) {
        StringBuilder result = new StringBuilder("<ul>");

        String idUsuarioParam = request.getParameter("idUsuario");
        Integer idUsuario = (idUsuarioParam != null && !idUsuarioParam.isEmpty()) ? Integer.valueOf(idUsuarioParam) : null;

        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");

        String tipoUsuarioParam = request.getParameter("tipoUsuario");
        Integer tipoUsuario = (tipoUsuarioParam != null && !tipoUsuarioParam.isEmpty()) ? Integer.valueOf(tipoUsuarioParam) : null;

        if (agreActu && idUsuario == null) {
            result.append("<li>ID requerido</li>");
        }

        if (nombres == null || nombres.trim().length() == 0) {
            result.append("<li>Nombres Requeridos</li>");
        } else if (nombres.trim().length() < 3 || nombres.trim().length() > 30) {
            result.append("<li>La dimension del nombre debe estar")
                    .append(" entre 3 a 30 caracteres</li>");
        }

        if (apellidos == null || apellidos.trim().length() == 0) {
            result.append("<li>Apellidos Requeridos</li>");
        } else if (apellidos.trim().length() < 3 || apellidos.trim().length() > 50) {
            result.append("<li>La dimension de los apellidos debe estar")
                    .append(" entre 3 a 50 caracteres</li>");
        }

        if (correo == null || correo.trim().length() == 0) {
            result.append("<li>Correo Requerido</li>");
        } else if (!correo.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$")) {
            result.append("<li>Correo no válido</li>");
        }

        if (tipoUsuario == null) {
            result.append("<li>Tipo de Usuario Requerido</li>");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setCorreo(correo);
        if (!agreActu) {
            String clave = request.getParameter("clave");
            usuario.setClave(clave);
        }
        usuario.setTipoUsuario(tipoUsuario);

        if (result.length() == 4) {
            String msg = agreActu
                    ? daoUsuario.actualizarUsuario(usuario)
                    : daoUsuario.agregarUsuario(usuario);
            if (msg != null) {
                result.append("<li>").append(msg).append("<li>");
            }
        }

        DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuarioImpl();
        List<TipoUsuario> tipoUsuarios = daoTipoUsuario.tipoUsuarioCbo();
        
        if (result.length() > 4) {
            request.setAttribute("usuario", usuario);
            request.setAttribute("tipoUsuarios", tipoUsuarios);
        }

        return result.length() == 4 ? null : result.append("</ul>").toString();
    }

    public String usuarioCBO() {
        String result = null;
        DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuarioImpl();
        List<TipoUsuario> tipoUsuarios = daoTipoUsuario.tipoUsuarioCbo();
        
        
        if (tipoUsuarios != null) { 
            request.setAttribute("tipoUsuarios", tipoUsuarios);
        } else {
            result = daoUsuario.getMensaje();
        }
        return result; 
    }
}
