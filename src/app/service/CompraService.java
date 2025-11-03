
package app.service;
import app.domain.OrdenDeCompra;
import app.domain.Funcion;
import app.domain.Combo;
import app.domain.MedioPago;
import java.util.List;

/**
 * Servicio que gestiona el proceso completo de compra.
 * Coordina la seleccion de funcion, tickets, asientos, combos y medio de pago.
 * 
 * Este servicio actua como el orquestador principal del flujo de compra.
 */

public class CompraService {
    /**
     * Inicializa una nueva orden de compra con los datos básicos.
     * 
     * @param funcion Funcion seleccionada por el usuario
     * @param cantidadTickets Numero de tickets a comprar
     * @return Nueva orden de compra inicializada
     */
    public OrdenDeCompra iniciarCompra(Funcion funcion, int cantidadTickets) {
        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setFuncion(funcion);
        
        if (cantidadTickets > 0) {
            orden.setCantidadTickets(cantidadTickets);
        }
        
        return orden;
    }

    /**
     * Verifica si la orden esta lista para proceder a la siguiente etapa.
     * Una orden esta lista si tiene funcion y cantidad de tickets definidos.
     * 
     * @param orden Orden de compra a validar
     * @return true si la orden esta lista para continuar
     */
    public boolean estaListaParaAsientos(OrdenDeCompra orden) {
        return orden != null && 
               orden.getFuncion() != null && 
               orden.getCantidadTickets() > 0;
    }

    /**
     * Verifica si la orden esta lista para proceder al pago.
     * Requiere: funcion, tickets, asientos y medio de pago.
     * 
     * @param orden Orden de compra a validar
     * @return true si la orden esta lista para pagar
     */
    public boolean estaListaParaPago(OrdenDeCompra orden) {
        return estaListaParaAsientos(orden) &&
               orden.getAsientos() != null &&
               orden.getAsientos().size() == orden.getCantidadTickets() &&
               orden.getMedioPago() != null;
    }

    /**
     * Asigna un combo a la orden de compra.
     * 
     * @param orden Orden de compra a modificar
     * @param combo Combo seleccionado por el usuario
     */
    public void asignarCombo(OrdenDeCompra orden, Combo combo) {
        if (orden != null) {
            orden.setCombo(combo);
        }
    }

    /**
     * Asigna un medio de pago a la orden de compra.
     * 
     * @param orden Orden de compra a modificar
     * @param medioPago Medio de pago seleccionado por el usuario
     */
    public void asignarMedioPago(OrdenDeCompra orden, MedioPago medioPago) {
        if (orden != null) {
            orden.setMedioPago(medioPago);
        }
    }

    /**
     * Obtiene un resumen detallado del estado actual de la compra.
     * Util para mostrar al usuario el progreso de su compra.
     * 
     * @param orden Orden de compra a resumir
     * @return String con el resumen detallado
     */
    public String obtenerResumenCompra(OrdenDeCompra orden) {
        if (orden == null) {
            return "No hay orden de compra activa";
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("=== RESUMEN DE TU COMPRA ===\n");

        // Informacion de la función
        if (orden.getFuncion() != null) {
            resumen.append("Pelicula: ").append(orden.getFuncion().getPelicula().getTitulo()).append("\n");
            resumen.append("Dia: ").append(orden.getFuncion().getFecha()).append("\n");
            resumen.append("Horario: ").append(orden.getFuncion().getHorario()).append("\n");
            resumen.append("Sala: ").append(orden.getFuncion().getSala()).append("\n");
        } else {
            resumen.append("Pelicula: No seleccionada\n");
        }

        // Informacion de tickets y asientos
        resumen.append("Tickets: ").append(orden.getCantidadTickets()).append("\n");
        
        if (!orden.getAsientos().isEmpty()) {
            resumen.append("Asientos: ").append(orden.getAsientos()).append("\n");
        } else {
            resumen.append("Asientos: Por seleccionar\n");
        }

        // Informacion de combo
        if (orden.getCombo() != null) {
            resumen.append("Combo: ").append(orden.getCombo().getNombre())
                   .append(" ($").append(orden.getCombo().getPrecio()).append(")\n");
        } else {
            resumen.append("Combo: No seleccionado\n");
        }

        // Informacion de medio de pago
        if (orden.getMedioPago() != null) {
            resumen.append("Medio de pago: ").append(orden.getMedioPago().getNombre()).append("\n");
        } else {
            resumen.append("Medio de pago: Por seleccionar\n");
        }

        // Total calculado
        double total = orden.calcularTotal();
        if (total > 0) {
            resumen.append("Total: $").append(String.format("%.2f", total)).append("\n");
        }

        return resumen.toString();
    }

    /**
     * Valida que todos los asientos reservados correspondan a la cantidad de tickets.
     * 
     * @param orden Orden de compra a validar
     * @return true si la cantidad de asientos coincide con los tickets
     */
    public boolean validarAsientos(OrdenDeCompra orden) {
        return orden != null && 
               orden.getAsientos().size() == orden.getCantidadTickets();
    }

    /**
     * Reinicia completamente una orden de compra para comenzar de nuevo.
     * Mantiene la instancia pero limpia todos los datos.
     * 
     * @param orden Orden de compra a reiniciar
     */
    public void reiniciarCompra(OrdenDeCompra orden) {
        if (orden != null) {
            orden.setFuncion(null);
            orden.setCantidadTickets(0);
            orden.setCombo(null);
            orden.setMedioPago(null);
            orden.getAsientos().clear();
        }
    }

    /**
     * Obtiene el progreso actual de la compra como porcentaje.
     * Util para mostrar una barra de progreso al usuario.
     * 
     * @param orden Orden de compra a evaluar
     * @return Porcentaje de completitud (0-100)
     */
    public int obtenerProgresoCompra(OrdenDeCompra orden) {
        if (orden == null) return 0;

        int pasosCompletados = 0;
        int totalPasos = 5; // funcion, tickets, asientos, combo, medio pago

        if (orden.getFuncion() != null) pasosCompletados++;
        if (orden.getCantidadTickets() > 0) pasosCompletados++;
        if (orden.getAsientos().size() == orden.getCantidadTickets()) pasosCompletados++;
        if (orden.getCombo() != null) pasosCompletados++;
        if (orden.getMedioPago() != null) pasosCompletados++;

        return (pasosCompletados * 100) / totalPasos;
    }
}

