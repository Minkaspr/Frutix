
package dao;

import dto.VentaDTO;
import entity.DetalleVenta;
import entity.Venta;
import java.util.List;

public interface DaoVenta {
    List<VentaDTO> obtenerVentas(int maxResultados, int primerResultado);
    VentaDTO obtenerVentaPorId(int id);
    String agregarVenta(Venta venta, List<DetalleVenta> detallesVenta);
    Integer obtenerTotalDeVentas();
    String getMensaje();
}
