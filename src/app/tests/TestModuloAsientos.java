// Cine_Los_Idos: Clase TestModuloAsientos
/*
En esta clase test, se prueba el flujo completo de reserva de asientos, 
simulando un escenario real de uso en la app principal.
*/ 

package app.tests;

import app.domain.OrdenDeCompra;
import app.service.AsientosService;

/**
 * Clase de prueba para el modulo completo de asientos.
 * Simula un escenario real de reserva multiple de asientos.
 */
public class TestModuloAsientos {
    
    /**
     * Metodo principal que ejecuta la prueba completa del modulo de asientos.
     * 
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("=== PRUEBA COMPLETA MODULO ASIENTOS ===");
        
        AsientosService service = new AsientosService();
        OrdenDeCompraMock orden = new OrdenDeCompraMock();
        
        // Simular flujo de reserva
        System.out.println("\n--- INICIANDO RESERVA ---");
        
        // Ver disponibilidad
        int personas = 3;
        boolean disponibles = service.tieneAsientosSuficientes(personas);
        System.out.println("Hay " + personas + " asientos libres? " + disponibles);
        
        if (disponibles){
            // Reservar asientos
            System.out.println("\nReservando asientos...");
            int[][] asientos = {{2,3}, {2,4}, {2,5}};
            
            for(int[] asiento : asientos){
                boolean exito = service.reservar(orden, asiento[0], asiento[1]);
                System.out.println("Asiento (" + asiento[0] + "," + asiento[1] + "): " + (exito ? "RESERVADO" : "FALLO"));         
            }
        }
        
        // Resumen de la reserva de asientos
        System.out.println("\n--- RESUMEN FINAL ---");
        System.out.println("Asientos reservados: " + orden.getAsientosReservados());
        System.out.println("Lista de asientos: " + orden.getAsientos());
        System.out.println("Operacion exitosa? " + (orden.getAsientosReservados() == personas));
        System.out.println("\n === PRUEBA COMPLETADA ===");
    }
    
    /**
     * Clase mock para simular una orden de compra en las pruebas.
     * Permite verificar el estado interno durante las pruebas del modulo completo.
     */
    static class OrdenDeCompraMock extends OrdenDeCompra {
        private int asientosReservados = 0;
        
        /**
         * Sobrescribe el metodo para contar asientos reservados durante las pruebas.
         * 
         * @param asiento Asiento en formato string (ej: "B3")
         */
        @Override
        public void agregarAsiento(String asiento) {
            asientosReservados++;
            System.out.println(" - Asiento " + asiento + " agregado a orden");
        }
        
        /**
         * Obtiene el numero de asientos reservados en esta orden mock.
         * 
         * @return Cantidad de asientos reservados
         */
        public int getAsientosReservados() {
            return asientosReservados;
        }                
    }    
}