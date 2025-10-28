// Cine_Los_Idos: Clase TestModuloAsientos
/*
En esta clase test, se prueba el flujo completo de reserva de asientos, simulando un escenario real de uso en la app principal.
*/ 


package app.tests;

import app.domain.OrdenDeCompra;
import app.service.AsientosService;

public class TestModuloAsientos {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA COMPLETA MÓDULO ASIENTOS ===");
        
        AsientosService service = new AsientosService();
        OrdenDeCompraMock orden = new OrdenDeCompraMock();
        
        // Simular flujo de reserva
        System.out.println("\n--- INICIANDO RESERVA ---");
        
        // Ver disponibilidad
        int personas = 3;
        boolean disponibles = service.tieneAsientosSufucientes(personas);
        System.out.println("Hay " + personas + " asientos libres?" + disponibles);
        
        if (disponibles){
            // Reservar asientos
            System.out.println("\nReservando asientos...");
            int[][] asientos = {{2,3}, {2,4}, {2,5}};
            
            for(int[] asiento : asientos){
                boolean exito = service.reservar(orden, asiento[0], asiento[1]);
                System.out.println("Asiento (" + asiento[0] + "," + asiento[1] + "):" +(exito ? "RESERVADO" : "FALLO"));         
            }
        }
        
        // Resumen de la reserva de asientos
        System.out.println("\n--- RESUMEN FINAL ---");
        System.out.println("Asientos reservados: " + orden.getAsientosReservados());
        System.out.println("Operación exitosa? " + (ordengetAsientosReservados = personas));
        System.out.println("\n === PRUEBA COMPLETADA ===");
    }
    
    //Creamos una clase estática simulando una Orden de Compra (mock)
    static class OrdenDeCompraMock extends OrdenDeCompra {
        private int asientosReservados = 0;
        
        @Override
        public void agregarAsiento(int fila, int columna){
            asientosReservados ++;
        }
        
        public int getAsientosReservados(){
            return asientosReservados;
        }                
    }    
}   
    
