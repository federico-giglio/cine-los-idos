package app.service;

import app.domain.Combo;
import app.domain.MedioPago;
import app.domain.OrdenDeCompra;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el proceso de finalizacion de compra.
 * Proporciona opciones al usuario, registra sus selecciones
 * y calcula los montos correspondientes.
 */
public class CheckoutService {

    /**
     * Proporciona la lista de todos los combos disponibles en el cine.
     * Incluye desde la opcion "Sin combo" hasta combos para familias.
     */
    public List<Combo> combosDisponibles() {
        List<Combo> combos = new ArrayList<>();
        combos.add(new Combo("Sin combo", 0.0));
        combos.add(new Combo("Combo Basico", 5000.0));
        combos.add(new Combo("Combo Pareja", 6000.0));
        combos.add(new Combo("Combo Familiar", 10000.0));
        return combos;
    }

    /**
     * Proporciona la lista de medios de pago aceptados por el cine.
     * Cada medio de pago incluye automaticamente su promocion asociada.
     */
    public List<MedioPago> mediosPagoDisponibles() {
        List<MedioPago> mediosPago = new ArrayList<>();
        mediosPago.add(new MedioPago.Galicia2x1());
        mediosPago.add(new MedioPago.Nacion2x1());
        mediosPago.add(new MedioPago.ClubLaNacion());
        mediosPago.add(new MedioPago.Efectivo());
        return mediosPago;
    }

    /**
     * Registra el combo seleccionado por el usuario en su orden de compra.
     */
    public void elegirCombo(OrdenDeCompra orden, Combo combo) {
        if (orden != null) {
            orden.setCombo(combo);
        }
    }

    /**
     * Registra el medio de pago seleccionado por el usuario en su orden.
     * El medio de pago determina el descuento que se aplicara.
     */
    public void elegirMedioPago(OrdenDeCompra orden, MedioPago medioPago) {
        if (orden != null) {
            orden.setMedioPago(medioPago);
        }
    }

    /**
     * Calcula el costo de los tickets antes de aplicar descuentos o combos.
     */
    public double subtotalTickets(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        int cantidad = Math.max(0, orden.getCantidadTickets());
        double precio = Math.max(0.0, orden.getPrecioTicket());
        return cantidad * precio;
    }

    /**
     * Calcula el monto de descuento segun el medio de pago.
     */
    public double descuento(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        MedioPago medioPago = orden.getMedioPago();
        if (medioPago == null) {
            return 0.0;
        }
        double subtotal = subtotalTickets(orden);
        return medioPago.calcularDescuento(subtotal);
    }

    /**
     * Calcula el monto total final que el usuario debe pagar.
     * Considera: tickets, descuentos por medio de pago, y combos.
     */
    public double totalFinal(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        double subtotal = subtotalTickets(orden);
        double precioCombo = 0.0;
        Combo combo = orden.getCombo();
        if (combo != null) {
            precioCombo = Math.max(0.0, combo.getPrecio());
        }
        double descuento = descuento(orden);
        double total = (subtotal - descuento) + precioCombo;
        return Math.max(0.0, total);
    }
}