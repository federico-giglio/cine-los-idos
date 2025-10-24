
package app.tests;

import app.domain.Pelicula;

//Pruebas para la Clase Película

public class TestPelicula {
    public static void main(String[] args) {
        //Acá probaremos la creación de películas
        
        //Película completa 
        try {
        Pelicula pelicula1 = new Pelicula (1, "Dune", 2021, "Ciencia ficcion", "155 min");
        System.out.println("vvvv-Pelicula con todos los datos-vvvv");
        System.out.println(pelicula1);
        System.out.println("==========================================="); 
        } catch (Exception e) { System.out.println("[[[Falló película 1]]]" + e.getMessage()); }
        
        try {
        Pelicula pelicula2 = new Pelicula (2, "Pelicula desconocida");
        System.out.println("vvvv-Pelicula Desconocida-vvvv");
        System.out.println(pelicula2);
        System.out.println("===========================================");
        } catch (Exception e) { System.out.println("[[[Falló película 2]]]" + e.getMessage()); }        
        
        try {
        Pelicula pelicula3 = new Pelicula (3, "Avatar 2", 2022, null, null);
        System.out.println("vvvv-Pelicula con titulo y anio-vvvv");
        System.out.println(pelicula3);
        System.out.println("===========================================");
        } catch (Exception e) { System.out.println("[[[Falló película 3]]]" + e.getMessage()); }       
        
        try {
        System.out.println("vvv-Pelicula Sin Titulo que debe arrojar exception-vvvv");
        Pelicula pelicula4 = new Pelicula (4, "");
        System.out.println(pelicula4);
        } catch (Exception e) { System.out.println("[[[Falló película 4]]]" + e.getMessage()); }        
                
    }
}
