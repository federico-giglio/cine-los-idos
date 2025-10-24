
package app.domain;

//Esta "función" va a representar una proyección específica de una película (con día, hora y sala). La función es UNA proyección concreta: una película + una fecha + un horario + una sala + un precio de ticket.

public class Funcion {
    
    //Estos valores no se tienen que poder cambiar (final) y tienen que ser obtenido sólo mediante getters
    private final Pelicula pelicula;
    private final String fecha;
    private final String horario;
    private final String sala;
    private final double precioTicket;
    
    //Constructor para crear "objetos Funcion", recibe como parámetro los datos que necesitamos para inicializar el objeto.
    public Funcion(Pelicula pelicula, String fecha, String horario, String sala, Double precioTicket){
        if (pelicula == null){ // Con esto vamos a validar que la película no sea NULL
            throw new IllegalArgumentException("La función necesita tener una película válida.");
        }
        
        this.pelicula = pelicula;
        this.fecha = (fecha != null && !fecha.trim().isEmpty()) ? fecha.trim() : "Fecha no especificada"; //Si la fecha no es null o vacía, la usamos. Sino asignamos un string por defecto
        this.horario = (horario != null && !fecha.trim().isEmpty()) ? horario.trim() : "Horario no especificado"; //Si el horario no es null o vacío, la usamos. Sino asignamos un string por defecto
        this.sala = (sala != null && !fecha.trim().isEmpty()) ? sala.trim() : "Sala no especificada"; // Si la sala no es null o vacía, la usamos. Sino asignamos un string por defecto
        //Para el precio vamos a establecer un valor por defecto de 5000 en caso de que sea nulo o menor o igual a 0.
        double precioBase = 5000.0;
        this.precioTicket = (precioTicket != null && precioTicket >= 0 ) ? precioTicket : precioBase; 
        
    
    }
    //Ahora realizaremos los getters para leer los valores desde fuera de la clase, pero sin permitir que se modifiquen.    
    public Pelicula getPelicula() { return pelicula; }
    public String getFecha() { return fecha; }
    public String getHorario() {return horario; }
    public String getSala() { return sala; }
    public double getPrecioTicket() { return precioTicket; }
    
    
    //Finalmente vamos a devolver un string formateado con la información de la función 
    @Override
    public String toString() {
        return "Pelicula: " + pelicula.getId() + " - " + pelicula.getTitulo() + //traemos título de Pelicula.java
                " (" + (pelicula.getAnio() == 0 ? "Anio no especificado": pelicula.getAnio() + ") ") +
                " | Sala: " + sala +
                " | Horario: " + horario +
                " | Fecha:" + fecha + 
                " | Precio: $" + (long)precioTicket;

        }
    
    }
