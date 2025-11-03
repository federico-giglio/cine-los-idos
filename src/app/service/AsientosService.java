// Cine_Los_Idos: Clase AsientosService
/*
Esta clase contiene la logica requerida para la reserva de asientos 
y su interaccion con la Orden de Compra y Main menu.
*/

package app.service;

import app.domain.Sala;
import app.domain.OrdenDeCompra;

/**
 * Servicio encargado de gestionar la reserva de asientos en el cine.
 * Se comunica con la clase Sala para verificar disponibilidad y realizar reservas,
 * y actualiza la OrdenDeCompra con los asientos seleccionados.
 */
public class AsientosService {
    private Sala salaActual;  // Instanciamos un objeto de la Clase Sala.
    
    /**
     * Constructor que inicializa el servicio creando una nueva sala.
     * La sala se genera con ocupacion aleatoria automaticamente.
     */
    public AsientosService(){ 
        generarSalaNueva();
    }
    
    /**
     * Genera una nueva sala con disposicion aleatoria de asientos ocupados/libres.
     * Se llama automaticamente al crear el servicio y puede ser llamado para resetear.
     */
    public void generarSalaNueva(){
        salaActual = new Sala();
    }
    
    /**
     * Obtiene la sala actual que esta siendo gestionada por este servicio.
     * 
     * @return Sala objeto que representa la sala de cine actual
     */
    public Sala getSala(){ 
        return salaActual;
    }
    
    /**
     * Realiza la reserva de un asiento especifico y lo agrega a la orden de compra.
     * Verifica primero si el asiento esta disponible antes de realizar la reserva.
     * 
     * @param orden Orden de compra donde se agregara el asiento reservado
     * @param fila Numero de fila del asiento (1-based)
     * @param columna Numero de columna del asiento (1-based)
     * @return true si la reserva fue exitosa, false si el asiento ya estaba ocupado
     */
    public boolean reservar(OrdenDeCompra orden , int fila, int columna){
        if (salaActual.reservar(fila, columna)){
           // Convertir coordenadas numericas a formato de asiento (ej: "A1", "B2")
           String asiento = convertirCoordenadasAAsiento(fila, columna);
           orden.agregarAsiento(asiento);            
           return true;
        }
        return false;        
    }

    /**
     * Convierte coordenadas numericas de fila y columna a formato de asiento.
     * Ejemplo: fila=1, columna=1 -> "A1"; fila=2, columna=3 -> "B3"
     * 
     * @param fila Numero de fila (1-based)
     * @param columna Numero de columna (1-based)
     * @return String representando el asiento en formato letra-numero
     */
    private String convertirCoordenadasAAsiento(int fila, int columna) {
        char letraFila = (char) ('A' + (fila - 1));
        return letraFila + Integer.toString(columna);
    }

    /**
     * Valida que haya suficientes asientos libres para la cantidad solicitada.
     * Recorre toda la sala contando los asientos disponibles.
     * 
     * @param cantidad Numero de asientos necesarios
     * @return true si hay al menos 'cantidad' asientos libres, false en caso contrario
     */
    public boolean tieneAsientosSuficientes(int cantidad){
        int libres = 0;
        for (int i = 0; i < salaActual.getFilas(); i++){
            for (int j = 0; j < salaActual.getColumnas(); j++){
                if (salaActual.getAsientos()[i][j] == Sala.Estado.LIBRE){
                    libres++;
                }
            }
        }
        return libres >= cantidad;
    }
    
    /**
     * Obtiene la cantidad actual de asientos libres en la sala.
     * Util para mostrar informacion al usuario sobre disponibilidad.
     * 
     * @return Numero de asientos disponibles en la sala
     */
    public int getAsientosDisponibles() {
        int libres = 0;
        for (int i = 0; i < salaActual.getFilas(); i++){
            for (int j = 0; j < salaActual.getColumnas(); j++){
                if (salaActual.getAsientos()[i][j] == Sala.Estado.LIBRE){
                    libres++;
                }
            }
        }
        return libres;
    }
}