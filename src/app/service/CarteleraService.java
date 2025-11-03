package app.service;
import app.domain.*; //Importamos TODO lo que se encuentre en app.domain
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que funciona como una base de datos en memoria para la cartelera del cine.
 * 
 * Maneja la logica del negocio relacionada con la cartelera del cine. 
 * Esta capa de servicio organiza, busca y muestra la informacion de peliculas y funciones.
 * 
 * Contiene una instancia de la clase Cartelera (para guardar las funciones), 
 * una carga de datos iniciales (peliculas y funciones) y permite buscar, 
 * agregar o mostrar peliculas en la cartelera.
 */
public class CarteleraService {
    // Atributos Cartelera principal del cine, y la lista de peliculas disponibles
    private final Cartelera cartelera;
    private final List<Pelicula> peliculas;
    
    /**
     * Constructor que inicializa la cartelera y lista de peliculas vacias.
     * Carga automaticamente las peliculas y funciones iniciales al crear el servicio.
     */
    public CarteleraService(){
        this.cartelera = new Cartelera();
        this.peliculas = new ArrayList();
        
        cargarPeliculasIniciales();
        cargarFuncionesIniciales();        
    }
    
    /**
     * Carga las peliculas iniciales en memoria.
     * Estas peliculas estan precargadas para simular una base de datos.
     */
    private void cargarPeliculasIniciales() {
        peliculas.add(new Pelicula(1, "Dune", 2021, "Ciencia ficcion", "155 min"));
        peliculas.add(new Pelicula(2, "Joker", 2019, "Drama", "122 min"));
        peliculas.add(new Pelicula(3, "The Batman", 2022, "Accion", "176 min"));
        peliculas.add(new Pelicula(4, "Interstellar", 2014, "Ciencia ficcion", "169 min"));
        peliculas.add(new Pelicula(5, "Oppenheimer", 2023, "Biografia", "180 min"));
    }
    
    /**
     * Genera funciones de ejemplo a partir de las peliculas cargadas.
     * Crea proyecciones en diferentes dias, horarios y salas para cada pelicula.
     */
    private void cargarFuncionesIniciales() {
        cartelera.agregarFuncion(new Funcion(peliculas.get(0), "Viernes", "20:00", "Sala 1", 5000.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(1), "Sabado", "17:00", "Sala 2", 4800.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(2), "Domingo", "14:00", "Sala 3", 5100.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(3), "Domingo", "22:00", "Sala 1", 5200.0));
        cartelera.agregarFuncion(new Funcion(peliculas.get(4), "Lunes", "19:00", "Sala 2", null)); // Usa precio por defecto
    }
    
    /**
     * Delega la tarea de mostrar la cartelera a la clase Cartelera.
     * Muestra todas las funciones disponibles en formato legible.
     */
    public void mostrarCartelera() {
        cartelera.mostrarCartelera();
    }
    
    /**
     * Busca una pelicula en la lista por nombre, ignorando mayusculas y minusculas.
     * 
     * @param nombre Nombre de la pelicula a buscar
     * @return Pelicula encontrada o null si no existe
     */
    public Pelicula buscarPeliculaPorNombre(String nombre){
        for (Pelicula p : peliculas) {
            if (p.getTitulo().equalsIgnoreCase(nombre)){
                return p; // Si existe la devolvemos
            }
        }
        return null; // Si no existe vamos a devolver NULL
    }
    
    /**
     * Busca la pelicula por ID. Metodo preferido para trabajar con peliculas.
     * 
     * @param id Identificador unico de la pelicula
     * @return Pelicula encontrada o null si no existe
     */
    public Pelicula buscarPeliculaPorId(int id) {
        for (Pelicula p : peliculas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Agrega una funcion/proyeccion a la cartelera principal del cine.
     * Actua como puente entre el servicio y la clase Cartelera.
     * Delega la responsabilidad de almacenar la funcion en la clase Cartelera.
     * 
     * @param funcion Funcion a agregar a la cartelera
     */
    public void agregarFuncion(Funcion funcion) {
        cartelera.agregarFuncion(funcion);
    }
    
    /**
     * Devuelve la lista completa de peliculas disponibles en el servicio.
     * Permite acceder a las peliculas precargadas sin exponer la estructura interna (encapsulamiento).
     * 
     * @return Lista de todas las peliculas disponibles
     */
    public List<Pelicula> obtenerPeliculas() {
        return peliculas;
    }
    
    /**
     * Devuelve la cartelera actual del cine, que contiene todas las proyecciones disponibles.
     * Se usa cuando otras clases necesitan acceder a la lista completa de funciones.
     * 
     * @return Cartelera con todas las funciones disponibles
     */
    public Cartelera obtenerCartelera() {
        return cartelera;
    }
    
    /**
     * Obtiene una funcion especifica de la cartelera por su indice.
     * Util para cuando el usuario selecciona una funcion de una lista numerada.
     * 
     * @param indice Posicion de la funcion en la lista (0-based)
     * @return Funcion en la posicion indicada, o null si el indice es invalido
     */
    public Funcion obtenerFuncion(int indice) {
        List<Funcion> funciones = cartelera.listarFunciones();
        if (indice >= 0 && indice < funciones.size()) {
            return funciones.get(indice);
        }
        return null;
    }
    
    /**
     * Obtiene el numero total de funciones disponibles en la cartelera.
     * Util para validar selecciones del usuario y mostrar rangos validos.
     * 
     * @return Cantidad total de funciones en la cartelera
     */
    public int getTotalFunciones() {
        return cartelera.listarFunciones().size();
    }
}