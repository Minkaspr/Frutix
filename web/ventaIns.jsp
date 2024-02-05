
<%@page import="entity.DetalleVenta"%>
<%@page import="java.util.List"%>
<%@page import="entity.Fruta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <%
            List<Fruta> frutas = (List<Fruta>) request.getAttribute("frutas");
            request.getSession().setAttribute("frutas", frutas);
            List<DetalleVenta> detallesVenta = (List<DetalleVenta>) request.getAttribute("detallesVenta");
            if (frutas == null) {
                out.println("<li>No se pudo obtener la lista de frutas.</li>");
            } else {
        %>
        <form action="Venta" method="post">
            <input type="hidden" name="op" value="INS"/>
            <table>
                <tr>
                    <th>Seleccionar</th>
                    <th>Fruta</th>
                    <th>Precio</th>
                    <th>Disponible</th>
                    <th>Cantidad</th>
                </tr>
                <% for (Fruta fruta : frutas) {
                        boolean seleccionada = false;
                        Double cantidad = 0.0;
                        if (detallesVenta != null) {
                            for (DetalleVenta detalle : detallesVenta) {
                                if (detalle.getFruta().equals(fruta.getIdFruta())) {
                                    seleccionada = true;
                                    cantidad = detalle.getCantidad();
                                    break;
                                }
                            }
                        }
                %>
                <tr>
                    <td><input type="checkbox" name="frutaSeleccionada" value="<%=fruta.getIdFruta()%>" <%=seleccionada ? "checked" : ""%>></td>
                    <td><%=fruta.getNombre()%></td>
                    <td><%=fruta.getPrecioPorKilogramo()%></td>
                    <td><%=fruta.getCantidadKilogramos() == 0 ? "No Disponible" : fruta.getCantidadKilogramos()%></td>
                    <td><input type="number" name="cantidad<%=fruta.getIdFruta()%>" min="0" value="<%=cantidad%>"></td>
                </tr>
                <% }%>
            </table>
            <input type="submit" value="Procesar Venta">
        </form>
        <% }%>
        <% if (request.getAttribute("message") != null) {%>
        <div><%= request.getAttribute("message")%></div>
        <% }%>
    </body>
</html>
