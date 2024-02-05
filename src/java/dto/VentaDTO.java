
package dto;

import java.util.List;

public class VentaDTO {
    private Integer idVenta;
    private String vendedor;
    private String fecha;
    private Integer cantFrutas;
    private Double precioTotal;
    private List<DetalleVentaDTO> detalles;

    public VentaDTO() {
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Integer getCantFrutas() {
        return cantFrutas;
    }

    public void setCantFrutas(Integer cantFrutas) {
        this.cantFrutas = cantFrutas;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
}
