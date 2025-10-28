// Cine_Los_Idos: Clase TestAsientosService
/*
Set de pruebas de AsientosService
Esta clase prueba el servicio que orquesta las operaciones de reserva, verificando integración entre Sala y OrdenDeCompra
*/

package app.tests;

import app.domain.OrdenDeCompra;
import app.service.AsientosService;

public class TestAsientosService {
    public static void main(String[] args) {
        System.out.println("=== TEST DE ASIENTOS SERVICE ===");
        
        AsientosService service = new AsientosService();
        
        // 1. Probar disponibilidad de asientos
        System.out.println("\n1. Probando disponibilidad:");
        System.out.println("Hay 5 asientos libres?" + service.tieneAsientosSufucientes(5));
        System.out.println("Hay 50 asientos libres?" + service.tieneAsientosSufucientes(50));
        
        // 2. Probar reservas con orden mock (boceto)
        System.out.println("\n2. Probando reservas:");
        OrdenDeCompraMock orden = new OrdenDeCompraMock();
        
        System.out.println("Reservando (1,1): " + service.reservar(orden, 1, 1));
        System.out.println("Reservando (1,2): " + service.reservar(orden, 1, 2));
        System.out.println("Reservando (1,1) otra vez: " + service.reservar(orden, 1, 1));
        System.out.println("Total asientos en orden:" + orden.getAsientosReservados());
    
    
        // 3. Probar regeneración de la sala
        System.out.println("\n3. Probando regeneración de sala: ");
        service.generarSalaNueva();
        System.out.println("Nueva sala generada");
        
        System.out.println("\n === PRUEBA COMPLETADA ===");    
    }
    
    static class OrdenDeCompraMock extends OrdenDeCompra{
        private int asientosReservados = 0;
        
        @Override
        public void agregarAsiento(int fila, int columna){
            asientosReservados ++;
            System.out.println(" - Asiento (" + fila + "," + columna + ") agregado a orden");
        }
        
        public int getAsientosReservados(){
            return asientosReservados;
        }
    }    
}
