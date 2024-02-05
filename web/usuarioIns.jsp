
<%@page import="entity.TipoUsuario"%>
<%@page import="java.util.List"%>
<%@page import="entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <section class="add_fruit">
            <div class="top-title">
                <h2 class="sub__titulo">Agregar Usuario</h2>
            </div>
            <%
                Usuario usuario = (Usuario) request.getAttribute("usuario");
                String nombresUsuario = (usuario != null) ? usuario.getNombres() : "";
                String apellidosUsuario = (usuario != null) ? usuario.getApellidos() : "";
                String correoUsuario = (usuario != null) ? usuario.getCorreo() : "";
                String claveUsuario = (usuario != null) ? usuario.getClave() : "";
                Integer tipoUsuarioUsuario = (usuario != null) ? usuario.getTipoUsuario() : 1;
                List<TipoUsuario> tipoUsuarios = (List<TipoUsuario>) request.getAttribute("tipoUsuarios");
            %>
            <form action="Usuario" method="POST" class="form">
                <input type="hidden" name="op" value="INS"/>
                <div class="form__group">
                    <div class="form__item">
                        <label for="nombre" class="form__label">Nombres</label>
                        <input type="text" id="nombre" class="form__input" name="nombres" value="<%= nombresUsuario%>" />
                    </div>
                    <div class="form__item">
                        <label for="apellidos" class="form__label">Apellidos</label>
                        <input type="text" id="apellidos" class="form__input" name="apellidos" value="<%= apellidosUsuario%>">
                    </div>
                    <div class="form__item">
                        <label for="correo" class="form__label">Correo</label>
                        <input type="email" id="correo" class="form__input" name="correo" value="<%= correoUsuario%>">
                    </div>
                    <div class="form__item">
                        <label for="clave" class="form__label">Clave</label>
                        <input type="password" id="clave" class="form__input" name="clave" value="<%= claveUsuario%>">
                    </div>
                    <div class="form__item">
                        <label for="tipoUsuario" class="form__label">Tipo de Usuario</label>
                        <select class="form-select" id="tipoUsuario" name="tipoUsuario">
                            <option selected disabled>Seleccionar</option>
                            <% for (TipoUsuario tu : tipoUsuarios) {%>
                            <option value="<%= tu.getIdTipoUsuario()%>" <%= tu.getIdTipoUsuario() == tipoUsuarioUsuario ? "selected" : ""%>>
                                <%= tu.getTipo()%>
                            </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="btn__group">
                        <button class="btn__item btn__item--cancel" onclick="location.href = 'usuario.jsp'">
                            <i class="uil uil-times"></i>
                            <span class="btnText">Cancelar</span>
                        </button>
                        <button class="btn__item btn__item--add" type="submit">
                            <i class="uil uil-check"></i>
                                <span class="btnText">Agregar</span>
                        </button>
                    </div>
            </form>
            <% if (request.getAttribute("message") != null) {%>
            <div><%= request.getAttribute("message")%></div>
            <% }%>
        </section>
    </body>
</html>
