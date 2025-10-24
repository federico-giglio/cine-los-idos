
package app.service;
import app.domain.*; //Importamos TODO lo que se encuentre en app.domain
import java.util.ArrayList;
import java.util.List;

/*Esto va a funcionar "como si fuera una base de datos", alimentando la cartelera de películas. 
Va a manejar la lógica del negocio relacionada con la cartelera del cine. O sea es la "capa de servicio que organiza,
busca y muestra la información. 
Va a contener una instancia de la clase Cartelera (para guardar las funciones), una carga de datos iniciales (películas y funciones)
y va a permitirnos buscar, agregar o mostrar películas en la cartelera.
*/
public class CarteleraService {
    //Atributos Cartelera principal del cine, y la lista de películas disponibles
    private final Cartelera cartelera;
    private final List<Pelicula> peliculas;
    
    //Constructor cartelera y lista de películas vacías.
    public CarteleraService(){
        this.cartelera = new Cartelera();
        this.peliculas = new ArrayList();
        
        cargarPeliculasIniciales();
        cargarFuncionesIniciales();        
    }
    
    //Método cargarPeliculasIniciales() para cargar las películas en memoria
    private void cargarPeliculasIniciales() {
        peliculas.add(new Pelicula(1, "Dune", 2021, "Ciencia ficción", "155 min"));
        peliculas.add(new Pelicula(2, "Joker", 2019, "Drama", "122 min"));
        peliculas.add(new Pelicula(3, "The Batman", 2022, "Acción", "176 min"));
        peliculas.add(new Pelicula(4, "Interstellar", 2014, "Ciencia ficción", "169 min"));
        peliculas.add(new Pelicula(5, "Oppenheimer", 2023, "Biografía", "180 min"));
    }
    
    //Método para que, a partir de las películas cargadas, generamos funciones de ejemplo.
    private void cargarFuncionesIniciales() {
        cartelera.agregarFuncion(new Funcion(peliculas.get(0), "Viernes", "20:00", "Sala 1", 5000.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(1), "Sábado", "17:00", "Sala 2", 4800.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(2), "Domingo", "14:00", "Sala 3", 5100.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(3), "Domingo", "22:00", "Sala 1", 5200.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(4), "Lunes", "19:00", "Sala 2", null)); //Acá debería traer el precio por defecto
    }
    
    //Ahora delegamos la tarea de mostrar la cartelera a la clase Cartelera
    public void mostrarCartelera() {
        cartelera.mostrarCartelera();
    }
    
    //Metodo para buscar una película en la lista por nombre, proponiendo ignorar mayúsculas y minúsculas
    public Pelicula buscarPeliculaPorNombre(String nombre){
        for (Pelicula p : peliculas) {
        if (p.getTitulo().equalsIgnoreCase(nombre)){
            return p;//Si existe la devolvemos
            }
        }
        return null; //Si no existe vamos a devolver NULL
    }
    
    //Método para buscar la película por ID. Utilizaremos este para trabajar idealmente.
    public Pelicula buscarPeliculaPorId(int id) {
        for (Pelicula p : peliculas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    //Método "Función/Proyección" a la cartelera principal del cine, esto actúa como puente entre el servicio y la clase Cartelera
    //Delega la responsabilidad de almacenar la función en la clase.
    public void agregarFuncion(Funcion funcion) {
        cartelera.agregarFuncion(funcion);
    }
    
    //Método para devolver la lista completa de películas disponibles en el servicio. Esto va a permitir acceder a las películas precargadas sin exponer desde la estructura interna(encapsulamiento).
    public List<Pelicula> obtenerPeliculas() {
        return peliculas;
    }
    
    //Método que devuelve la cartelera actual del cine, contiene todas las proyeccciones disponibles. Se usa cuando otras clases tienen que acceder a la lista de funciones. 
    public Cartelera obtenerCartelera() {
        return cartelera;
    }
    
    
}
