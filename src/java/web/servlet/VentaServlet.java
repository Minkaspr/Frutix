package web.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.validator.VentaValidator;

@WebServlet(name = "VentaServlet", urlPatterns = {"/Venta"})
public class VentaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String opcion = request.getParameter("op");
        opcion = (opcion == null) ? "" : opcion;

        String result = null;
        String target = "ventaSel.jsp";

        VentaValidator validator = new VentaValidator(request);
        switch (opcion) {
            case "DF":
                result = validator.ventaDf();
                target = "ventaIns.jsp";
                break;
            case "SEL":
                result = validator.ventaSel();
                break;
            case "INS":
                result = validator.ventaIns();
                target = result == null ? "dashboard.jsp" : "ventaIns.jsp";
                break;
            case "DET":
                result = validator.ventaDet();
                target = "ventaDet.jsp";
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
