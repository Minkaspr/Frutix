
<%@page import="java.util.List"%>
<%@page import="entity.Fruta" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Frutix</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <%
            String parametroPagina = request.getParameter("pagina");
            String parametroTamanoPagina = request.getParameter("tamanoPagina");

            int paginaActual = (parametroPagina != null) ? Integer.parseInt(parametroPagina) : 1;
            int tamanoPaginaActual = (parametroTamanoPagina != null) ? Integer.parseInt(parametroTamanoPagina) : 10;

            if (request.getAttribute("message") != null) {
        %>
        <div><%= request.getAttribute("message")%></div>
        <% } %>
        <main>
            <div>
                <section>
                    <div>
                        <p>¡Bienvenido a la <strong>Frutería del Sabor!</strong> 🍉😋</p>
                </section>
                <section>
                    <div>
                        <h2>Tabla de Frutas</h2>
                    </div>
                    <div>
                        <a href="frutaIns.jsp">Agregar</a>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Fruta</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Disponibilidad</th>
                                <th>Operaciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% List<Fruta> lista = (List<Fruta>) request.getAttribute("lista");
                                if (lista != null) {
                                    for (Fruta f : lista) {%>
                            <tr>
                                <td><%= f.getIdFruta()%></td>
                                <td><%= f.getNombre()%></td>
                                <td><%= f.getCantidadKilogramos()%> kg</td>
                                <td>S/ <%= f.getPrecioPorKilogramo()%></td>
                                <td><%= f.getDisponible() ? "Disponible" : "No disponible"%></td>
                                <td>
                                    <a href="?op=GET&id=<%= f.getIdFruta()%>">Editar</a>
                                    <a href="?op=DEL&id=<%= f.getIdFruta()%>">Eliminar</a>
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
                </section>
            </div>
        </main>
    </body>
</html>
