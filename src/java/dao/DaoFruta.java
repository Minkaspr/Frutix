
package dao;

import entity.Fruta;
import java.util.List;

public interface DaoFruta {
    List<Fruta> obtenerFrutas(int maxResultados, int primerResultado);
    List<Fruta> obtenerTodasLasFrutas();
    Fruta obtenerFrutaPorId(int id);
    String agregarFruta(Fruta fruta);
    String actualizarFruta(Fruta fruta);
    String eliminarFruta(Integer id);
    Integer obtenerTotalDeFrutas();
    String getMensaje();
}
