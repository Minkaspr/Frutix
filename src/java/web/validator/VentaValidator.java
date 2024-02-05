package web.validator;

import dao.DaoFruta;
import dao.DaoVenta;
import dao.impl.DaoFrutaImpl;
import dao.impl.DaoVentaImpl;
import dto.UsuarioDTO;
import dto.VentaDTO;
import entity.DetalleVenta;
import entity.Fruta;
import entity.Venta;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class VentaValidator {

    private final HttpServletRequest request;
    private final DaoVenta daoVenta;

    public VentaValidator(HttpServletRequest request) {
        this.request = request;
        this.daoVenta = new DaoVentaImpl();
    }

    public String ventaIns() {
        StringBuilder result = new StringBuilder("<ul>");

        // Obtén el ID del usuario desde la sesión
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            result.append("<li>Usuario no encontrado en la sesión.</li>");
            return result.append("</ul>").toString();
        }
        Integer idUsuario = usuario.getIdUsuario();

        // Obtén la fecha y hora actual
        LocalDateTime fechaVenta = LocalDateTime.now();

        // Inicializa el precio total
        Double precioTotal = 0.0;

        // Obtén la lista de frutas desde la solicitud
        List<Fruta> frutas = (List<Fruta>) request.getSession().getAttribute("frutas");
        if (frutas == null) {
            result.append("<li>No se pudo obtener la lista de frutas.</li>");
            return result.append("</ul>").toString();
        }

        // Obtén los IDs de las frutas seleccionadas y las cantidades correspondientes
        String[] frutasSeleccionadas = request.getParameterValues("frutaSeleccionada");
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        if (frutasSeleccionadas != null) {
            for (String idFruta : frutasSeleccionadas) {
                String cantidadParam = request.getParameter("cantidad" + idFruta);
                Double cantidad = (cantidadParam != null && !cantidadParam.isEmpty()) ? Double.valueOf(cantidadParam) : 0;

                // Busca la fruta en listaFrutas por su ID
                Fruta fruta = null;
                for (Fruta f : frutas) {
                    if (f.getIdFruta().equals(Integer.valueOf(idFruta))) {
                        fruta = f;
                        break;
                    }
                }

                // Verifica si la fruta está disponible y si la cantidad solicitada está disponible
                if (fruta == null || !fruta.getDisponible() || fruta.getCantidadKilogramos() < cantidad || cantidad == 0) {
                    result.append("<li>Cantidad no permitida para la fruta: " + fruta.getNombre() + "</li>");
                    continue;
                }

                // Actualiza el precio total
                precioTotal += fruta.getPrecioPorKilogramo() * cantidad;

                // Crea un objeto DetalleVenta para cada fruta seleccionada y agrega a la lista
                DetalleVenta detalle = new DetalleVenta();
                detalle.setFruta(Integer.valueOf(idFruta));
                detalle.setPrecioVenta(fruta.getPrecioPorKilogramo());
                detalle.setCantidad(cantidad);
                detallesVenta.add(detalle);
            }
        } else {
            result.append("<li>Debe elegir al menos una fruta.</li>");
        }

        Venta venta = new Venta();
        venta.setUsuario(idUsuario);
        venta.setFecha(fechaVenta);
        venta.setPrecioTotal(precioTotal);

        if (result.length() == 4 && venta != null && detallesVenta != null ) {
            String mensaje = daoVenta.agregarVenta(venta, detallesVenta);
            if (mensaje != null) {
                result.append("<li>").append(mensaje).append("</li>");
            }
        }

        if (result.length() > 4) {
            request.setAttribute("frutas", frutas);
            request.setAttribute("detallesVenta", detallesVenta);
        }

        return result.length() == 4 ? null : result.append("</ul>").toString();
    }

    public String ventaDf() {
        String result = null;
        // Obtenemos la lista de 'Frutas'
        DaoFruta daoFruta = new DaoFrutaImpl();

        List<Fruta> frutas = daoFruta.obtenerTodasLasFrutas();
        if (frutas != null) {
            // Enviamos la lista de 'Frutas'
            request.setAttribute("frutas", frutas);
        } else {
            result = daoFruta.getMensaje();
        }
        return result;
    }

    public String ventaSel() {
        String result = null;
        String parametroPagina = request.getParameter("pagina");
        String parametroTamanoPagina = request.getParameter("tamanoPagina");

        int pagina = (parametroPagina != null) ? Integer.parseInt(parametroPagina) : 1;
        int tamanoPagina = (parametroTamanoPagina != null) ? Integer.parseInt(parametroTamanoPagina) : 10;

        int primerResultado = (pagina - 1) * tamanoPagina;

        List<VentaDTO> lista = daoVenta.obtenerVentas(tamanoPagina, primerResultado);
        if (lista != null) {
            request.setAttribute("lista", lista);
        } else {
            result = daoVenta.getMensaje();
        }
        
        int totalPaginas = obtenerTotalPaginas(tamanoPagina);
        request.setAttribute("totalPaginas", totalPaginas);
        return result;
    }
    
    public int obtenerTotalPaginas(int tamanoPagina) {
        long totalFrutas = daoVenta.obtenerTotalDeVentas();
        return (int) Math.ceil((double) totalFrutas / tamanoPagina);
    }

    public String ventaDet() {
        String result = null;

        String idVentaAux = request.getParameter("id");
        Integer idVenta = Integer.valueOf(idVentaAux);
        VentaDTO ventaDTO = daoVenta.obtenerVentaPorId(idVenta);

        if (ventaDTO != null) {
            request.setAttribute("ventaDTO", ventaDTO);
        } else {
            result = daoVenta.getMensaje();
        }
        return result;
    }
}
