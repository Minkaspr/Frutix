package web.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.validator.UsuarioValidator;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/Usuario"})
public class UsuarioServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String opcion = request.getParameter("op");
        opcion = (opcion == null) ? "" : opcion;

        String result = null;
        String target = "index.jsp";

        UsuarioValidator validator = new UsuarioValidator(request);
        switch (opcion) {
            case "LI": // LogIn
                result = validator.usuarioLi();
                target = result == null ? "dashboard.jsp" : "login.jsp";
                break;
            case "LO": //LogOut
                result = validator.usuarioLo();
                target = "index.jsp";
                break;
            case "SEL":
                result = validator.usuarioSel();
                target = "usuarioSel.jsp";
                break;
            case "CBO":
                result = validator.usuarioCBO();
                target = "usuarioIns.jsp";
                break;
            case "INS":
                result = validator.usuarioInsUpd(false);
                target = result == null ? "usuario.jsp" : "usuarioIns.jsp";
                break;
            case "GET":
                result = validator.usuarioGet();
                target = "usuarioUpd.jsp";
                break;
            case "UPD":
                result = validator.usuarioInsUpd(true);
                target = result == null ? "usuario.jsp" : "usuarioUpd.jsp";
                break;
            case "":
                result = "Solicitud requerida";
                break;
            default:
                result = "Solicitud no reconocida";
        }

        if (result != null) {
            request.setAttribute("message", result);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
