
<%@page import="java.util.List"%>
<%@page import="dto.VentaDTO"%>
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
            String parametroPagina = request.getParameter("pagina");
            String parametroTamanoPagina = request.getParameter("tamanoPagina");

            int paginaActual = (parametroPagina != null) ? Integer.parseInt(parametroPagina) : 1;
            int tamanoPaginaActual = (parametroTamanoPagina != null) ? Integer.parseInt(parametroTamanoPagina) : 10;
        %>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Vendedor</th>
                    <th>Fecha de Venta</th>
                    <th>Cantidad de Frutas</th>
                    <th>Precio Total</th>
                    <th>Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <% List<VentaDTO> lista = (List<VentaDTO>) request.getAttribute("lista");
                    if (lista != null) {
                        for (VentaDTO v : lista) {%>
                <tr>
                    <td><%= v.getIdVenta()%></td>
                    <td><%= v.getVendedor()%></td>
                    <td><%= v.getFecha()%></td>
                    <td><%= v.getCantFrutas()%></td>
                    <td>S/ <%= v.getPrecioTotal()%></td>
                    <td>
                        <a href="?op=DET&id=<%= v.getIdVenta()%>">Detalles</a>
                    </td>
                </tr>
                <% }
                    }%>
            </tbody>
        </table>
        <div>
            <% int totalPaginas = (Integer) request.getAttribute("totalPaginas"); %>
            <% if (paginaActual > 1) {%>
            <a href="?op=SEL&pagina=<%= paginaActual - 1%>&tamanoPagina=<%= tamanoPaginaActual%>">Anterior</a>
            <% } %>
            <% if (paginaActual < totalPaginas) {%>
            <a href="?op=SEL&pagina=<%= paginaActual + 1%>&tamanoPagina=<%= tamanoPaginaActual%>">Siguiente</a>
            <% }%>
        </div>
    </body>
</html>
