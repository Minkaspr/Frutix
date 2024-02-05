
package entity;

public class Fruta {
    private Integer idFruta;
    private String nombre;
    private String descripcion;
    private Double cantidadKilogramos;
    private Double precioPorKilogramo;
    private Boolean disponible;

    public Fruta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getIdFruta() {
        return idFruta;
    }

    public void setIdFruta(Integer idFruta) {
        this.idFruta = idFruta;
    }

    public Double getCantidadKilogramos() {
        return cantidadKilogramos;
    }

    public void setCantidadKilogramos(Double cantidadKilogramos) {
        this.cantidadKilogramos = cantidadKilogramos;
    }

    public Double getPrecioPorKilogramo() {
        return precioPorKilogramo;
    }

    public void setPrecioPorKilogramo(Double precioPorKilogramo) {
        this.precioPorKilogramo = precioPorKilogramo;
    }
}
