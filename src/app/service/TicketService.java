package app.service;

import app.domain.OrdenDeCompra;
import app.domain.MedioPago;
import app.domain.Combo;
import java.util.stream.Collectors;

/**
 * Servicio unificado para gestionar pagos y formato de tickets.
 * Combina la funcionalidad de procesamiento de pagos y generacion de tickets.
 */
public class TicketService {

    // ---------------- PagoService ----------------
    
    /**
     * Servicio encargado del procesamiento de pagos.
     * Maneja la seleccion de medios de pago y la simulacion de transacciones.
     */
    public static class PagoService {

        /**
         * Asigna un medio de pago a la orden de compra.
         * 
         * @param orden Orden de compra a la que asignar el medio de pago
         * @param medio Medio de pago seleccionado por el usuario
         */
        public void elegirMedioPago(OrdenDeCompra orden, MedioPago medio) {
            orden.setMedioPago(medio);
        }

        /**
         * Simula el procesamiento del pago de la orden.
         * En una implementacion real, aqui se conectaria con pasarelas de pago.
         * 
         * @param orden Orden de compra a procesar
         * @return true si el pago fue exitoso, false en caso contrario
         */
        public boolean procesar(OrdenDeCompra orden) {
            System.out.println("Procesando pago con " + orden.getMedioPago().getNombre() + "...");
            // Simulacion de procesamiento exitoso
            return true;
        }
    }

    // ----- FormateoService ------
    
    /**
     * Servicio encargado del formato y presentacion de tickets.
     * Genera representaciones legibles de la orden de compra para el usuario.
     */
    public static class FormateoService {

        /**
         * Genera un ticket formateado con toda la informacion de la compra.
         * Incluye detalles de la funcion, asientos, combos y calculos de dinero.
         * 
         * @param orden Orden de compra a formatear como ticket
         * @return String con el ticket formateado listo para imprimir/mostrar
         */
        public String formatearTicket(OrdenDeCompra orden) {
            // Obtener el medio de pago para calcular descuentos
            MedioPago medio = orden.getMedioPago();
            
            // Calcular montos financieros
            double subtotalTickets = orden.getCantidadTickets() * orden.getPrecioTicket();
            double descuento = medio.calcularDescuento(subtotalTickets);
            double totalTickets = subtotalTickets - descuento;
            
            // Calcular precio del combo si existe
            double totalCombo = 0.0;
            Combo combo = orden.getCombo();
            if (combo != null) {
                totalCombo = combo.getPrecio();
            }
            
            double totalFinal = totalTickets + totalCombo;

            // Formatear lista de asientos
            String asientos = orden.getAsientos().stream()
                    .map(a -> "(" + a + ")")
                    .collect(Collectors.joining(", "));

            // Construir el ticket con formato
            StringBuilder sb = new StringBuilder();
            sb.append("***** TICKET CINE LOS IDOS *****\n");
            sb.append("Pelicula: ").append(orden.getFuncion().getPelicula().getTitulo()).append("\n");
            sb.append("Dia: ").append(orden.getFuncion().getFecha()).append("  |  Horario: ").append(orden.getFuncion().getHorario()).append("\n");
            sb.append("Sala: ").append(orden.getFuncion().getSala()).append("\n\n");
            sb.append("Entradas: ").append(orden.getCantidadTickets())
              .append(" x $").append(String.format("%.2f", orden.getPrecioTicket())).append("\n");
            sb.append("Asientos: ").append(asientos).append("\n");
            
            // Informacion del combo si existe
            if (combo != null) {
                sb.append("Combo: ").append(combo.getNombre()).append("  $").append(String.format("%.2f", totalCombo)).append("\n");
            } else {
                sb.append("Combo: No seleccionado\n");
            }
            
            sb.append("\n");
            sb.append("Promo aplicada: ").append(medio.getNombre()).append("\n");
            sb.append("Subtotal tickets: $").append(String.format("%.2f", subtotalTickets)).append("\n");
            sb.append("Descuento: -$").append(String.format("%.2f", descuento)).append("\n");
            sb.append("Total tickets: $").append(String.format("%.2f", totalTickets)).append("\n");
            
            if (combo != null) {
                sb.append("Total combos:  $").append(String.format("%.2f", totalCombo)).append("\n");
            }
            
            sb.append("----------------------------------\n");
            sb.append("TOTAL FINAL:   $").append(String.format("%.2f", totalFinal)).append("\n");
            sb.append("****¡Gracias por elegirnos!****\n");

            return sb.toString();
        }
    }
}