package app.tests;

import app.domain.OrdenDeCompra;
import app.domain.MedioPago;
import app.domain.Funcion;
import app.domain.Pelicula;
import app.domain.Combo;
import app.service.TicketService.PagoService;
import app.service.TicketService.FormateoService;
import java.util.Arrays;

/**
 * Prueba funcional por consola del sistema de cine.
 * Demuestra la generacion completa de un ticket con el nuevo sistema unificado.
 */
public class TestTicket {

    /**
     * Metodo principal que ejecuta la prueba del sistema de tickets.
     * Crea una orden de compra completa y genera un ticket formateado.
     * 
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Crear una pelicula para la funcion
        Pelicula pelicula = new Pelicula(1, "Duna 2", 2024, "Ciencia ficcion", "166 min");
        
        // Crear una funcion con la pelicula
        Funcion funcion = new Funcion(pelicula, "Sabado", "20:00", "Sala 1", 5000.0);
        
        // Crear la orden de compra y configurarla
        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setFuncion(funcion);
        orden.setCantidadTickets(2);
        orden.agregarAsiento("A1");
        orden.agregarAsiento("A2");
        
        // Configurar el combo
        Combo combo = new Combo("Combo Basico", 5000.0);
        orden.setCombo(combo);
        
        // Configurar servicios
        PagoService pagoService = new PagoService();
        FormateoService formateoService = new FormateoService();

        // Seleccionar medio de pago y procesar
        MedioPago medio = new MedioPago.Galicia2x1();
        pagoService.elegirMedioPago(orden, medio);
        
        // Procesar el pago
        boolean pagoExitoso = pagoService.procesar(orden);
        System.out.println("Pago procesado: " + (pagoExitoso ? "EXITOSO" : "FALLIDO"));
        System.out.println();
        
        // Calcular el total antes de generar el ticket
        orden.calcularTotal();
        
        // Generar y mostrar el ticket
        String ticket = formateoService.formatearTicket(orden);
        System.out.println(ticket);
        
        // Mostrar informacion adicional de debug
        System.out.println("=== INFORMACION ADICIONAL ===");
        System.out.println("Total calculado: $" + orden.calcularTotal());
        System.out.println("Medio de pago: " + orden.getMedioPago().getNombre());
        System.out.println("Combo: " + orden.getCombo().getNombre());
    }
}