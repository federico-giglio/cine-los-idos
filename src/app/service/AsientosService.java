// Cine_Los_Idos: Clase AsientosService
/*
Esta clase contiene la lógica requerida para la reserva de asientos y su interacción con la Orden de Compra y Main menu.
*/

package app.service;

//import app.domain.OrdenDeCompra; => Se consume del módulo Orded
import app.domain.Sala;
import.app.domain.OrdenDeCompra;

//Creamos una nueva clase AsientosService que manejará la reserva de asientos.
public class AsientosService {
    private Sala salaActual;  //Instanciamos un objeto de la Clase Sala.
    
    public AsientosService(){ //Creamos una fc para generar una nueva sala.
        generarSalaNueva();
    }
    
    public void generarSalaNueva(){
        salaActual = new Sala();
    }
    
    public Sala getSala(){ //Método get para traer la salaActual creada.
        return salaActual;
    }
    
    //En esta fc se realiza la reserva agregando los asientos a la Orden de Compra. 
    public boolean reservar(OrdenDeCompra orden , int fila, int columna){
        if (salaActual.reservar(fila, columna)){
           orden.agregarAsiento(fila, columna);            
           return true;
        }
        return false;        
    }

    //Con este método validamos que la cantidad de asientos a reservar está disponible 
    public boolean tieneAsientosSufucientes(int cantidad){
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
}
