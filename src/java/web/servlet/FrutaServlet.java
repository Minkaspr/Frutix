package web.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.validator.FrutaValidator;

@WebServlet(name = "FrutaServlet", urlPatterns = {"/Fruta"})
public class FrutaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String opcion = request.getParameter("op");
        opcion = (opcion == null) ? "" : opcion;

        String result = null;
        String target = "frutaSel.jsp";

        FrutaValidator validator = new FrutaValidator(request);
        switch (opcion) {
            case "SEL":
                result = validator.frutaSel();
                break;
            case "INS":
                result = validator.frutaInsUpd(false);
                target = result == null ? "Fruta?op=SEL" : "frutaIns.jsp";
                break;
            case "GET":
                result = validator.frutaGet();
                target = "frutaUpd.jsp";
                break;
            case "UPD":
                result = validator.frutaInsUpd(true);
                target = result == null ? "fruta.jsp" : "frutaUpd.jsp";
                break;
            case "DEL":
                result = validator.frutaDel();
                target = "Fruta?op=SEL";
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
