package app.service;

import app.domain.Combo;
import app.domain.OrdenDeCompra;
import app.domain.promo.Promocion;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de checkout: calcula totales y orquesta selección de combo y promoción.
 * No realiza I/O de consola.
 */
public class CheckoutService {

    public List<Combo> combosDisponibles() {
        List<Combo> combos = new ArrayList<>();
        combos.add(new Combo("Sin combo", 0.0));
        combos.add(new Combo("Combo Básico", 5000.0));
        combos.add(new Combo("Combo Pareja", 6000.0));
        combos.add(new Combo("Combo Familiar", 10000.0));
        return combos;
    }

    public List<Promocion> promocionesDisponibles() {
        List<Promocion> promos = new ArrayList<>();
        promos.add(new Promocion.PromoDosPorUno("2x1 Galicia"));
        promos.add(new Promocion.PromoDosPorUno("2x1 Nación"));
        promos.add(new Promocion.PromoClubLaNacion("10% Club La Nación"));
        promos.add(new Promocion.PromoTarjetaNaranja("25% Tarjeta Naranja"));
        promos.add(new Promocion.PromoSinPromo());
        return promos;
    }

    public void elegirCombo(OrdenDeCompra orden, Combo combo) {
        if (orden != null) {
            orden.setCombo(combo);
        }
    }

    public void elegirPromocion(OrdenDeCompra orden, Promocion promo) {
        if (orden != null) {
            orden.setPromocion(promo);
        }
    }

    public double subtotalTickets(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        int cantidad = Math.max(0, orden.getCantidadTickets());
        double precio = Math.max(0.0, orden.getPrecioTicket());
        return cantidad * precio;
    }

    public double descuento(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        Promocion promo = orden.getPromocion();
        int cantidad = Math.max(0, orden.getCantidadTickets());
        double precio = Math.max(0.0, orden.getPrecioTicket());
        if (promo == null) {
            promo = new Promocion.PromoSinPromo();
        }
        return promo.calcularDescuento(cantidad, precio);
    }

    public double totalFinal(OrdenDeCompra orden) {
        if (orden == null) return 0.0;
        double subtotal = subtotalTickets(orden);
        double precioCombo = 0.0;
        Combo combo = orden.getCombo();
        if (combo != null) {
            precioCombo = Math.max(0.0, combo.getPrecio());
        }
        double desc = descuento(orden);
        double total = subtotal + precioCombo - desc;
        return Math.max(0.0, total);
    }
}