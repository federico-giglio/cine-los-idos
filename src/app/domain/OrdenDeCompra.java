 package app.domain;

import app.domain.promo.Promocion;

/**
 * Representa una orden de compra que incluye:
 * - La cantidad de tickets
 * - El precio unitario de cada ticket
 * - Un combo opcional (ej. gaseosa + pochoclos)
 * - Una promoción opcional (ej. 2x1, 10% Club La Nación, etc.)
 *
 * Esta clase actúa como "contenedor de datos" que luego usan los servicios
 * (CheckoutService) y los menús (MenuPrincipal, MenuTicket) para calcular
 * subtotales, descuentos y el total final.
 */
public class OrdenDeCompra {
    // Número de tickets que el cliente quiere comprar
    private int cantidadTickets;

    // Precio fijo de cada ticket (constante en este caso: $5000)
    private final double precioTicket = 5000;

    // Combo elegido por el cliente (puede ser null si no selecciona ninguno)
    private Combo combo;

    // Promoción elegida por el cliente (puede ser null si no selecciona ninguna)
    private Promocion promocion;

    // --- Getters y Setters ---

    // Devuelve la cantidad de tickets seleccionados
    public int getCantidadTickets() {
        return cantidadTickets;
    }

    // Permite establecer la cantidad de tickets
    public void setCantidadTickets(int cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }

    // Devuelve el precio unitario del ticket (siempre 5000 en este diseño)
    public double getPrecioTicket() {
        return precioTicket;
    }

    // Devuelve el combo elegido (o null si no se eligió ninguno)
    public Combo getCombo() {
        return combo;
    }

    // Permite asignar un combo a la orden
    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    // Devuelve la promoción elegida (o null si no se eligió ninguna)
    public Promocion getPromocion() {
        return promocion;
    }

    // Permite asignar una promoción a la orden
    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

  
    // Si quisieras permitir cambiar el precio del ticket dinámicamente,
    // deberías quitar el "final" en la declaración de precioTicket
    // y completar este método. Actualmente lanza una excepción.
    public void setPrecioTicket(double d) {
        throw new UnsupportedOperationException("El precio del ticket es fijo y no se puede modificar.");
    }
}