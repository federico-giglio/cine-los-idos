
package app.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//La clase Cartelera va a contener una lista de funciones (por ejemplo, las películas del día).

public class Cartelera {
    
    //Como atributo Usamos una lista dinámica (ArrayList) para almacenar todas las funciones del cine. La lista va a poder crecer a medida que se agregan nuevas funciones.
    private final List<Funcion> funciones = new ArrayList<>();
    
    
    //Creamos la función para "agregar Funcion" a la cartelera
    public void agregarFuncion(Funcion f) {
        if (f == null) {
            throw new IllegalArgumentException("No se puede agregar una función nula (NULL) a la cartelera.");
        }
        funciones.add(f);
    }
    
    
    //Ahora creamos la función para mostrar la lista de funciones, pero sólo lectura. Para que no se modifique la original.
    
    public List<Funcion> listarFunciones() {
        return Collections.unmodifiableList(funciones);
    }
    
    //ahora imprimimos en consola todas las funciones disponibles, con formato prolijo
    
    public void mostrarCartelera() {
        if (funciones.isEmpty()) { //Si está vacío muestra que no hay funciones
            System.out.println("No hay funciones cargadas en la cartelera actualmente.");
            return;
        }

        System.out.println("\n===============================================");
        System.out.println("                CARTELERA DEL CINE             ");
        System.out.println("===============================================\n");

        int contador = 1;
        //Ahora hacemnos un ciclo for each para recorrer automáticamente toda la lista de funciones que tenemos en la clase Cartelera.
        for (Funcion f : funciones) { 
            System.out.println(contador + ". " + f);
            contador++;
        }

        System.out.println("\n===============================================");
    }
    
}
