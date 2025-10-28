// Cine_Los_Idos: Clase TestSala
/*
Set de pruebas de la Clase Sala. 
El objetivo es probar que la funcionalidad se comporta de manera esperada.
*/

package app.tests;

import app.domain.Sala;

public class TestSala {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE SALA ===");
        
        //1. Crear Sala => Creamos un método imprimrSala para mostrar el esado de la Sala actual.
        System.out.println("\n1. Creando una nueva sala...");
        Sala sala = new Sala();
        imprimirSala(sala);      
    
        //2. Probar consultas de asientos
        System.out.println("\n2. Probando consultas de asientos:");
        probarConsultas(sala);
        
        // 3. Probar reservas
        System.out.println("\n3. Probando reservas:");
        probarReservas(sala);
        
        // 4. Estado final (con asientos reservados)
        System.out.println("\n4. Estado final de la sala:");
        imprimirSala(sala);
        
        System.out.println("\n=== TEST COMPLETADO ===");
            
    }
    
    //Métodos para Tests (se deberán aplicar en main o en la clase Sala:
    
    // Muestra por consola la sala, con la ocupación actual de asientos. 
    public static void imprimirSala(Sala sala) {
    System.out.println("\n     SALA DE CINE:\n");
    System.out.print("   ");
    for (int col = 1; col <= Sala.COLUMNAS; col++) {
        System.out.print(col + " ");
    }
    System.out.println();
    
    for (int fila = 1; fila <= Sala.FILAS; fila++) {
        System.out.print(fila + "  ");
        for (int col = 1; col <= Sala.COLUMNAS; col++) {
            System.out.print(sala.estaLibre(fila, col) ? "L " : "X ");
        }
        System.out.println();
    }
}
    
    // Pruebas para chequear si los asientos están libres/ocupados
    private static void probarConsultas(Sala sala){
        // Consultar asientos válidos
        System.out.println("Asiento (1,1) libre? " + sala.estaLibre(1,1));
        System.out.println("Asiento (3,4) libre? " + sala.estaLibre(3, 4));
        System.out.println("Asiento (5,8) libre? " + sala.estaLibre(5, 8));
        
        // Consultar asientos inválidos
        System.out.println("Asiento (0,1) libre? " + sala.estaLibre(0, 1));
        System.out.println("Asiento (6,1) libre? " + sala.estaLibre(6, 1));
        System.out.println("Asiento (1,0) libre? " + sala.estaLibre(1, 0));
        System.out.println("Asiento (1,9) libre? " + sala.estaLibre(1, 9));
    }
    
    // Pruebas para chequear la funcionalidad de reservar asientos
    private static void probarReservas(Sala sala){
        // Reservar asiento válido y libre
        System.out.println("Reservando asiento (2,3): " + sala.reservar(2,3));
        // Intentar reservar el mismo asiento otra vez (debe fallar)
        System.out.println("Reservando asiento (2,3) otra vez " + sala.reservar(2,3));
        // Reservar otro asiento valido
        System.out.println("Reservando asiento (4,5): "+ sala.reservar(4,5));
        
        //Intentar reservar asientos inválidos
        System.out.println("Reservando asiento (0,1)" + sala.reservar(0,1));
        System.out.println("Reservando asiento (1,9) " + sala.reservar(1,9));             
    }    
}
