// Cine_Los_Idos: Clase TestAsientosService
/*
Set de pruebas de AsientosService
Esta clase prueba el servicio que orquesta las operaciones de reserva, verificando integracion entre Sala y OrdenDeCompra
*/

package app.tests;

import app.domain.OrdenDeCompra;
import app.service.AsientosService;

/**
 * Clase de prueba para el servicio de asientos.
 * Verifica el correcto funcionamiento de la reserva de asientos
 * y la integracion con la orden de compra.
 */
public class TestAsientosService {
    
    /**
     * Metodo principal que ejecuta todas las pruebas del servicio de asientos.
     * 
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("=== TEST DE ASIENTOS SERVICE ===");
        
        AsientosService service = new AsientosService();
        
        // 1. Probar disponibilidad de asientos
        System.out.println("\n1. Probando disponibilidad:");
        System.out.println("Hay 5 asientos libres? " + service.tieneAsientosSuficientes(5));
        System.out.println("Hay 50 asientos libres? " + service.tieneAsientosSuficientes(50));
        
        // 2. Probar reservas con orden mock
        System.out.println("\n2. Probando reservas:");
        OrdenDeCompraMock orden = new OrdenDeCompraMock();
        
        System.out.println("Reservando (1,1): " + service.reservar(orden, 1, 1));
        System.out.println("Reservando (1,2): " + service.reservar(orden, 1, 2));
        System.out.println("Reservando (1,1) otra vez: " + service.reservar(orden, 1, 1));
        System.out.println("Total asientos en orden: " + orden.getAsientosReservados());
        System.out.println("Lista de asientos: " + orden.getAsientos());
    
        // 3. Probar regeneracion de la sala
        System.out.println("\n3. Probando regeneracion de sala: ");
        service.generarSalaNueva();
        System.out.println("Nueva sala generada");
        
        // 4. Probar asientos disponibles
        System.out.println("\n4. Asientos disponibles en nueva sala: " + service.getAsientosDisponibles());
        
        System.out.println("\n === PRUEBA COMPLETADA ===");    
    }
    
    /**
     * Clase mock para simular una orden de compra en las pruebas.
     * Extiende la clase real pero permite verificar el estado interno durante las pruebas.
     */
    static class OrdenDeCompraMock extends OrdenDeCompra {
        private int asientosReservados = 0;
        
        /**
         * Sobrescribe el metodo para contar asientos reservados durante las pruebas.
         * 
         * @param asiento Asiento en formato string (ej: "A1")
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