package web.validator;

import dao.DaoFruta;
import dao.impl.DaoFrutaImpl;
import entity.Fruta;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class FrutaValidator {

    private final HttpServletRequest request;
    private final DaoFruta daoFruta;

    public FrutaValidator(HttpServletRequest request) {
        this.request = request;
        this.daoFruta = new DaoFrutaImpl();
    }

    public String frutaSel() {
        String result = null;
        String parametroPagina = request.getParameter("pagina");
        String parametroTamanoPagina = request.getParameter("tamanoPagina");

        int pagina = (parametroPagina != null) ? Integer.parseInt(parametroPagina) : 1;
        int tamanoPagina = (parametroTamanoPagina != null) ? Integer.parseInt(parametroTamanoPagina) : 10;

        int primerResultado = (pagina - 1) * tamanoPagina;

        List<Fruta> lista = daoFruta.obtenerFrutas(tamanoPagina, primerResultado);
        if (lista != null) {
            request.setAttribute("lista", lista);
        } else {
            result = daoFruta.getMensaje();
        }
        int totalPaginas = obtenerTotalPaginas(tamanoPagina);
        request.setAttribute("totalPaginas", totalPaginas);
        return result;
    }

    public int obtenerTotalPaginas(int tamanoPagina) {
        long totalFrutas = daoFruta.obtenerTotalDeFrutas();
        return (int) Math.ceil((double) totalFrutas / tamanoPagina);
    }

    public String frutaInsUpd(boolean agreActu) {
        StringBuilder result = new StringBuilder("<ul>");

        String idFrutaParam = request.getParameter("idFruta");
        Integer idFruta = (idFrutaParam != null && !idFrutaParam.isEmpty()) ? Integer.valueOf(idFrutaParam) : null;

        String nombre = request.getParameter("nombre");

        String descripcion = request.getParameter("descripcion");

        String cantidadKilogramosParam = request.getParameter("cantidadKilogramos");
        Double cantidadKilogramos = (cantidadKilogramosParam != null && !cantidadKilogramosParam.isEmpty()) ? Double.valueOf(cantidadKilogramosParam) : 0;

        String precioPorKilogramoParam = request.getParameter("precioPorKilogramo");
        Double precioPorKilogramo = (precioPorKilogramoParam != null && !precioPorKilogramoParam.isEmpty()) ? Double.valueOf(precioPorKilogramoParam) : 0;

        if (agreActu && idFruta == null) {
            result.append("<li>ID requerido</li>");
        }

        if (nombre == null || nombre.trim().length() == 0) {
            result.append("<li>Nombre Requerido</li>");
        } else if (nombre.trim().length() < 3 || nombre.trim().length() > 30) {
            result.append("<li>La dimension del nombre debe estar")
                    .append(" entre 3 a 30 caracteres</li>");
        }

        if (descripcion == null || descripcion.trim().length() == 0) {
            result.append("<li>Descripcion Requerido</li>");
        } else if (descripcion.trim().length() < 3 || descripcion.trim().length() > 50) {
            result.append("<li>La dimension de la descripcion debe estar")
                    .append(" entre 3 a 50 caracteres</li>");
        }

        if (cantidadKilogramos == 0) {
            result.append("<li>Ingresa la cantidad de kilogramos</li>");
        } else if (cantidadKilogramos < 0) {
            result.append("Ingrese valores positivos");
        }

        if (precioPorKilogramo == 0) {
            result.append("<li>Ingresa el precio por kilogramos</li>");
        } else if (precioPorKilogramo < 0) {
            result.append("Ingrese valores positivos");
        }

        Fruta fruta = new Fruta();
        fruta.setIdFruta(idFruta);
        fruta.setNombre(nombre);
        fruta.setDescripcion(descripcion);
        fruta.setCantidadKilogramos(cantidadKilogramos);
        fruta.setPrecioPorKilogramo(precioPorKilogramo);
        boolean disponible;
        if (agreActu) {
            String disponibleParam = request.getParameter("disponible");
            disponible = "on".equals(disponibleParam);

        } else {
            disponible = cantidadKilogramos > 0;
        }
        fruta.setDisponible(disponible);

        if (result.length() == 4) {
            String msg = agreActu
                    ? daoFruta.actualizarFruta(fruta)
                    : daoFruta.agregarFruta(fruta);
            if (msg != null) {
                result.append("<li>").append(msg).append("<li>");
            }
        }

        if (result.length() > 4) {
            request.setAttribute("fruta", fruta);
        }

        return result.length() == 4 ? null : result.append("</ul>").toString();
    }

    public String frutaGet() {
        String result = null;
        String idFrutaAux = request.getParameter("id");
        Integer idFruta = Integer.valueOf(idFrutaAux);
        Fruta fruta = daoFruta.obtenerFrutaPorId(idFruta);
        if (fruta != null) {
            request.setAttribute("fruta", fruta);
        } else {
            result = daoFruta.getMensaje();
        }
        return result;
    }

    public String frutaDel() {
        String idParam = request.getParameter("id");
        String resultado = null;
        if (idParam != null && !idParam.isEmpty()) {
            Integer id = Integer.parseInt(idParam);
            resultado = daoFruta.eliminarFruta(id);
        } else {
            resultado = "ID incorrecto";
        }
        return resultado;
    }
}
