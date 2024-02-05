package dao.impl;

import dao.DaoVenta;
import dto.DetalleVentaDTO;
import dto.VentaDTO;
import entity.DetalleVenta;
import entity.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;

public class DaoVentaImpl implements DaoVenta {

    private final ConexionBD conexion;
    private String mensaje;

    public DaoVentaImpl() {
        this.conexion = new ConexionBD();
    }

    @Override
    public List<VentaDTO> obtenerVentas(int maxResultados, int primerResultado) {
        List<VentaDTO> lista = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("idVenta, ")
                .append("vendedor, ")
                .append("fechaVenta, ")
                .append("cantidadFrutasVendidas, ")
                .append("precioTotal ")
                .append("FROM venta_view ")
                .append("LIMIT ? OFFSET ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setInt(1, maxResultados);
            ps.setInt(2, primerResultado);
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a - dd / MM / yy");
            while (rs.next()) {
                VentaDTO venta = new VentaDTO();
                venta.setIdVenta(rs.getInt(1));
                venta.setVendedor(rs.getString(2));
                venta.setFecha(rs.getTimestamp(3).toLocalDateTime().format(formatter));
                venta.setCantFrutas(rs.getInt(4));
                venta.setPrecioTotal(rs.getDouble(5));
                lista.add(venta);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return lista;
    }

    @Override
    public VentaDTO obtenerVentaPorId(int id) {
        VentaDTO ventaDTO = new VentaDTO();
        StringBuilder query = new StringBuilder();
        query.append("SELECT ")
                .append("vendedor, ")
                .append("fechaVenta, ")
                .append("precioTotal ")
                .append("FROM venta_view ")
                .append("WHERE idVenta = ?");
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query.toString());
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ventaDTO.setVendedor(rs.getString(1));
                    ventaDTO.setFecha(rs.getString(2));
                    ventaDTO.setPrecioTotal(rs.getDouble(3));
                    List<DetalleVentaDTO> detalles = new ArrayList<>();
                    StringBuilder subquery = new StringBuilder();
                    subquery.append("SELECT f.nombre, dv.precioVenta, dv.cantidad ")
                            .append("FROM detalleVenta dv ")
                            .append("INNER JOIN fruta f ")
                            .append("ON dv.fruta = f.idFruta ")
                            .append("WHERE dv.venta = ?");
                    PreparedStatement psDetalles = cn.prepareStatement(subquery.toString());
                    psDetalles.setInt(1, id);
                    try (ResultSet rsDetalles = psDetalles.executeQuery()) {
                        while (rsDetalles.next()) {
                            DetalleVentaDTO detalle = new DetalleVentaDTO();
                            detalle.setNombreFruta(rsDetalles.getString(1));
                            detalle.setPrecioVenta(rsDetalles.getDouble(2));
                            detalle.setCantidad(rsDetalles.getDouble(3));
                            detalles.add(detalle);
                        }
                    }
                    ventaDTO.setDetalles(detalles);
                } else {
                    ventaDTO = null;
                }
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return ventaDTO;
    }

    @Override
    public String agregarVenta(Venta venta, List<DetalleVenta> detallesVenta) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO venta(")
                .append("usuario, fechaVenta, precioTotal")
                .append(") VALUES (?, ?, ?)");
        try (Connection cn = conexion.conexionBD()) {
            // Desactiva el auto-commit para iniciar una transacción
            cn.setAutoCommit(false);

            PreparedStatement ps = cn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, venta.getUsuario());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(venta.getFecha()));
            ps.setDouble(3, venta.getPrecioTotal());

            int ctos = ps.executeUpdate();
            boolean transaccionExitosa = true;
            if (ctos == 0) {
                mensaje = "cero filas insertadas";
                transaccionExitosa = false;
            } else {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idVenta = rs.getInt(1);

                    for (DetalleVenta detalle : detallesVenta) {
                        query = new StringBuilder();
                        query.append("INSERT INTO detalleVenta(")
                                .append("venta, fruta, precioVenta, cantidad")
                                .append(") VALUES (?, ?, ?, ?)");
                        ps = cn.prepareStatement(query.toString());
                        ps.setInt(1, idVenta);
                        ps.setInt(2, detalle.getFruta());
                        ps.setDouble(3, detalle.getPrecioVenta());
                        ps.setDouble(4, detalle.getCantidad());
                        ctos = ps.executeUpdate();
                        if (ctos == 0) {
                            mensaje = "Error al insertar detalle de venta";
                            transaccionExitosa = false;
                            break;
                        }
                    }
                }
            }
            // Si todas las operaciones fueron exitosas, realiza los cambios
            if (transaccionExitosa) {
                cn.commit();
            } else {
                // Si hubo algún error, deshacemos la transacción
                cn.rollback();
            }
            // Volvemos a habilitar el modo de confirmación automática
            cn.setAutoCommit(true);
        } catch (SQLException e) {
            // Imprime el mensaje de la excepción
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    @Override
    public Integer obtenerTotalDeVentas() {
        Integer total = null;
        String query = "SELECT COUNT(idVenta) FROM venta_view";
        try (Connection cn = conexion.conexionBD()) {
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        }
        return total;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
