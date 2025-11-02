package app;

import app.domain.Ticket.*;
import app.service.TicketService.*;

import java.util.Arrays;

/**
 * Prueba funcional por consola del sistema de cine.
 */
public class TestTicket {

    public static void main(String[] args) {

        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setPelicula("Duna 2");
        orden.setDia("Sábado");
        orden.setHorario("20:00");
        orden.setCantidadTickets(2);
        orden.setPrecioUnitario(5000);
        orden.setAsientos(Arrays.asList("A1", "A2"));
        orden.setCombo("Combo Básico");
        orden.setPrecioCombo(5000);

        PagoService pagoService = new PagoService();
        FormateoService formateoService = new FormateoService();

        MedioPago medio = new TarjetaGalicia();
        pagoService.elegirMedioPago(orden, medio);
        pagoService.procesar(orden);

        String ticket = formateoService.formatearTicket(orden);
        System.out.println(ticket);
    }
}
