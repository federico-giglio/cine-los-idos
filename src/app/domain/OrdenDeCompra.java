package app.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden de compra completa del cine.
 * Contiene toda la informacion necesaria para procesar una compra:
 * - La funcion seleccionada (pelicula, dia, horario, sala)
 * - La cantidad de tickets y los asientos especificos
 * - El combo de comida seleccionado (si aplica)
 * - El medio de pago elegido (que incluye promociones)
 */
public class OrdenDeCompra {
    // Numero de tickets que el cliente desea comprar
    private int cantidadTickets;

    // Precio fijo de cada ticket - valor constante
    private final double precioTicket = 5000;

    // Combo de comida seleccionado por el cliente (opcional)
    private Combo combo;

    // Medio de pago elegido por el cliente (determina los descuentos)
    private MedioPago medioPago;

    // Funcion cinematografica seleccionada
    private Funcion funcion;
    
    // Lista de asientos especificos reservados
    private List<String> asientos;

    /**
     * Constructor que inicializa una orden de compra vacia.
     * La lista de asientos comienza vacia y se va llenando.
     */
    public OrdenDeCompra() {
        this.asientos = new ArrayList<>();
    }

    /**
     * Obtiene la cantidad de tickets en la orden.
     */
    public int getCantidadTickets() {
        return cantidadTickets;
    }

    /**
     * Establece la cantidad de tickets para la orden.
     * La cantidad debe ser un numero positivo.
     */
    public void setCantidadTickets(int cantidadTickets) {
        if (cantidadTickets < 0) {
            throw new IllegalArgumentException("La cantidad de tickets no puede ser negativa");
        }
        this.cantidadTickets = cantidadTickets;
    }

    /**
     * Obtiene el precio unitario fijo de cada ticket.
     */
    public double getPrecioTicket() {
        return precioTicket;
    }

    /**
     * Obtiene el combo de comida seleccionado.
     */
    public Combo getCombo() {
        return combo;
    }

    /**
     * Establece el combo de comida para la orden.
     */
    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    /**
     * Obtiene el medio de pago seleccionado para la orden.
     */
    public MedioPago getMedioPago() {
        return medioPago;
    }

    /**
     * Establece el medio de pago para la orden.
     */
    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * Obtiene la funcion cinematografica seleccionada.
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     * Establece la funcion cinematografica para la orden.
     */
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    /**
     * Obtiene la lista de asientos reservados.
     */
    public List<String> getAsientos() {
        return asientos;
    }

    /**
     * Agrega un asiento a la lista de asientos reservados.
     */
    public void agregarAsiento(String asiento) {
        this.asientos.add(asiento);
    }

    /**
     * Calcula el monto total a pagar por la orden completa.
     * Considera: tickets, descuentos por medio de pago, y combo.
     */
    public double calcularTotal() {
        if (funcion == null) {
            return 0;
        }
        
        double subtotalTickets = cantidadTickets * precioTicket;
        double descuento = 0;
        if (medioPago != null) {
            descuento = medioPago.calcularDescuento(subtotalTickets);
        }
        
        double precioCombo = 0;
        if (combo != null) {
            precioCombo = combo.getPrecio();
        }
        
        double totalFinal = (subtotalTickets - descuento) + precioCombo;
        return Math.max(0, totalFinal);
    }
}