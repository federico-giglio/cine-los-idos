
package app.tests;
import app.service.CarteleraService;
import app.domain.Pelicula;
import app.domain.Funcion;

public class TestCarteleraService {
    public static void main(String[] args) {
        CarteleraService servicio = new CarteleraService();
        
        System.out.println("========= CARTELERA CARGADA AUTOMÁTICAMENTE =========");
        servicio.mostrarCartelera();
        
        System.out.println("\n========= BÚSQUEDA DE PELÍCULA POR ID =========");
        Pelicula porId = servicio.buscarPeliculaPorId(3); //Tiene que traer "The Batman"
        if (porId != null){
            System.out.println("Película seleccionada (ID 3):" + porId.getTitulo());
        } else {
            System.out.println("No se encontró ninguna película con ese ID.");
        }
        
        
        System.out.println("\n========= AGREGAR NUEVA PROYECCIÓN =========");
        Pelicula interstellar = servicio.buscarPeliculaPorId(4);
        Funcion nuevaFuncion = new Funcion(interstellar, "Martes", "22:30", "Sala 4", 5500.0);
        servicio.agregarFuncion(nuevaFuncion);
        
        System.out.println("\n========= CARTELERA ACTUALIZADA =========");
        servicio.mostrarCartelera();
        
    }
}
