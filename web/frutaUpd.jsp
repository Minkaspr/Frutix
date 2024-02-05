
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
        <section class="add_fruit">
            <div class="top-title">
                <h2 class="sub__titulo">Actualizar Fruta</h2>
            </div>
            <%
                Fruta fruta = (Fruta) request.getAttribute("fruta");
            %>
            <form action="Fruta" method="POST" class="form">
                <input type="hidden" name="op" value="UPD"/>
                <input type="hidden" name="idFruta" value="<%= fruta.getIdFruta()%>"/>
                <div class="form__group">
                    <div class="form__item">
                        <label for="nombre" class="form__label">Nombre</label>
                        <input type="text" id="nombre" class="form__input" name="nombre" value="<%= fruta.getNombre()%>" />
                    </div>
                    <div class="form__item">
                        <label for="descripcion" class="form__label">Descripción</label>
                        <input type="text" id="descripcion" class="form__input" name="descripcion" value="<%= fruta.getDescripcion()%>">
                    </div>
                    <div class="form__item">
                        <label for="cantidad" class="form__label">Cantidad - Kg</label>
                        <input type="number" id="cantidad" class="form__input" name="cantidadKilogramos" min="0" step="0.01" value="<%= fruta.getCantidadKilogramos()%>">
                    </div>
                    <div class="form__item">
                        <label for="precio" class="form__label">Precio</label>
                        <input type="number" id="precio" class="form__input" name="precioPorKilogramo" min="0" step="0.01" value="<%= fruta.getPrecioPorKilogramo()%>">
                    </div>
                    <div class="form__item">
                        <label for="disponible" class="form__label">Disponibilidad</label>
                        <input type="checkbox" id="disponible" class="form__input" name="disponible" <%= fruta.getDisponible() ? "checked" : ""%> />
                    </div>
                    <div class="btn__group">
                        <button class="btn__item btn__item--cancel" onclick="location.href = 'fruta.jsp'">
                            <i class="uil uil-times"></i>
                            <span class="btnText">Cancelar</span>
                        </button>
                        <button class="btn__item btn__item--add" type="submit">
                            <i class="uil uil-check"></i>
                            <span class="btnText">Actualizar</span>
                        </button>
                    </div>
            </form>
            <% if (request.getAttribute("message") != null) {%>
            <div><%= request.getAttribute("message")%></div>
            <% }%>
        </section>
    </body>
</html>
