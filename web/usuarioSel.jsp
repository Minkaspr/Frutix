
<%@page import="java.util.List"%>
<%@page import="dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Tipo de Usuario</th>
                    <th>Operaciones</th>
                </tr>
            </thead>
            <tbody>
                <% List<UsuarioDTO> lista = (List<UsuarioDTO>) request.getAttribute("lista");
                    if (lista != null) {
                        for (UsuarioDTO u : lista) {%>
                <tr>
                    <td><%= u.getIdUsuario()%></td>
                    <td><%= u.getNombres()%></td>
                    <td><%= u.getCorreo()%></td>
                    <td><%= u.getTipoUsuario()%></td>
                    <td>
                        <a href="?op=GET&id=<%= u.getIdUsuario()%>">Editar</a>
                        <a href="?op=DEL&id=<%= u.getIdUsuario()%>">Eliminar</a>
                    </td>
                </tr>
                <% }
                    }%>
            </tbody>
        </table>
    </body>
</html>
