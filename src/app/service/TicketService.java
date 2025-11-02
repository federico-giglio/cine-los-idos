package app.service;

import app.domain.Ticket.OrdenDeCompra;
import app.domain.Ticket.MedioPago;
import java.util.stream.Collectors;

/**
 * Contiene PagoService y TicketService (unificados)
 */
public class TicketService {

    // ---------------- PagoService ----------------
    public static class PagoService {

        public void elegirMedioPago(OrdenDeCompra orden, MedioPago medio) {
            orden.setMedioPago(medio);
        }

        public boolean procesar(OrdenDeCompra orden) {
            System.out.println("Procesando pago con " + orden.getMedioPago().getNombre() + "...");
            return true;
        }
    }

    // ---------------- TicketService ----------------
    public static class FormateoService {

        public String formatearTicket(OrdenDeCompra orden) {
            var medio = orden.getMedioPago();
            double subtotalTickets = orden.getCantidadTickets() * orden.getPrecioUnitario();
            double descuento = medio.calcularDescuento(subtotalTickets);
            double totalTickets = subtotalTickets - descuento;
            double totalCombo = orden.getPrecioCombo();
            double totalFinal = totalTickets + totalCombo;

            String asientos = orden.getAsientos().stream()
                    .map(a -> "(" + a + ")")
                    .collect(Collectors.joining(", "));

            StringBuilder sb = new StringBuilder();
            sb.append("🎬 ***** TICKET CINE LOS IDOS *****\n");
            sb.append("Película: ").append(orden.getPelicula()).append("\n");
            sb.append("Día: ").append(orden.getDia()).append("  |  Horario: ").append(orden.getHorario()).append("\n\n");
            sb.append("Entradas: ").append(orden.getCantidadTickets())
              .append(" x $").append(String.format("%.2f", orden.getPrecioUnitario())).append("\n");
            sb.append("Asientos: ").append(asientos).append("\n");
            sb.append("Combo: ").append(orden.getCombo()).append("  $").append(String.format("%.2f", totalCombo)).append("\n\n");
            sb.append("Promo aplicada: ").append(medio.getNombre()).append("\n");
            sb.append("Subtotal tickets: $").append(String.format("%.2f", subtotalTickets)).append("\n");
            sb.append("Descuento: -$").append(String.format("%.2f", descuento)).append("\n");
            sb.append("Total tickets: $").append(String.format("%.2f", totalTickets)).append("\n");
            sb.append("Total combos:  $").append(String.format("%.2f", totalCombo)).append("\n");
            sb.append("----------------------------------\n");
            sb.append("TOTAL FINAL:   $").append(String.format("%.2f", totalFinal)).append("\n");
            sb.append("****¡Gracias por elegirnos!****\n");

            return sb.toString();
        }
    }
}
