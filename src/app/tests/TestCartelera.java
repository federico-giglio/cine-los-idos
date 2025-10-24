package app.tests;

import app.domain.*; //Traemos todo de domain

// Probaremos el funcionamiento de Funcion y Cartelera.
public class TestCartelera {
    public static void main(String[] args) {

        //Creamos películas usando la clase Pelicula que creamos antes.
        Pelicula dune   = new Pelicula(1, "Dune", 2021, "Ciencia ficción", "155 min");
        Pelicula joker  = new Pelicula(2, "Joker", 2019, "Drama", "122 min");
        Pelicula batman = new Pelicula(3, "The Batman", 2022, "Acción", "176 min");

        //Creamos funciones de cada película, probamos distintos escenarios como algunos con precio nulo, otros con texto faltante, y todos con películas válidas
        Funcion f1 = new Funcion(dune,   "Viernes",  "20:00", "Sala 1", 5000.0);
        Funcion f2 = new Funcion(joker,  "Sábado",   "17:00", "Sala 2", 5200.0);
        Funcion f3 = new Funcion(batman, "Domingo",  "14:00", "Sala 3", null); //Acá debería tomar el precio por defecto (5000)
        Funcion f4 = new Funcion(dune,   "Domingo",  "20:00", "Sala 1", 5300.0);

        //Crear la cartelera y agregar las funciones
        Cartelera cartelera = new Cartelera();
        cartelera.agregarFuncion(f1);
        cartelera.agregarFuncion(f2);
        cartelera.agregarFuncion(f3);
        cartelera.agregarFuncion(f4);

        //Mostrar la cartelera completa en consola
        cartelera.mostrarCartelera();
    }
}