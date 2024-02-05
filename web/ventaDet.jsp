
<%@page import="dto.VentaDTO"%>
<%@page import="dto.DetalleVentaDTO"%>
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
                VentaDTO ventaDTO = (VentaDTO) request.getAttribute("ventaDTO");
            %>
        <h1>Detalles de la Venta</h1>
        <p>Vendedor: ${ventaDTO.vendedor}</p>
        <p>Fecha: ${ventaDTO.fecha}</p>

        <h2>Productos:</h2>
        <table>
            <tr>
                <th>Nombre de la Fruta</th>
                <th>Precio de Venta</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
            <% for(DetalleVentaDTO detalle : ventaDTO.getDetalles()) { %>
                <tr>
                    <td><%= detalle.getNombreFruta() %></td>
                    <td><%= detalle.getPrecioVenta() %></td>
                    <td><%= detalle.getCantidad() %></td>
                    <td><%= detalle.getPrecioVenta() * detalle.getCantidad() %></td>
                </tr>
            <% } %>
        </table>

        <p>Precio Total: ${ventaDTO.precioTotal}</p>
    </body>
</html>
