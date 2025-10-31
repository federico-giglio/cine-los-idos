package app.domain.promo;

/**
 * Promoción abstracta para aplicar descuentos sobre tickets.
 * Cada promoción implementa su forma de calcular el descuento.
 */
public abstract class Promocion {
    protected final String nombre;

    protected Promocion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double calcularDescuento(int cantidad, double precio);

    @Override
    public String toString() {
        return nombre;
    }

    // ✅ Promoción "2x1" (50% de descuento)
    public static class PromoDosPorUno extends Promocion {
        public PromoDosPorUno(String nombre) { super(nombre); }

        @Override
        public double calcularDescuento(int cantidad, double precio) {
            if (cantidad <= 0 || precio <= 0) return 0.0;
            return cantidad * precio * 0.50;
        }
    }

    // ✅ Promoción "10% Club La Nación"
    public static class PromoClubLaNacion extends Promocion {
        public PromoClubLaNacion(String nombre){ super(nombre); }

        @Override
        public double calcularDescuento(int cantidadTickets, double precioTicket){
            return cantidadTickets * precioTicket * 0.10;
        }
    }

    // ✅ Promoción "25% Tarjeta Naranja"
    public static class PromoTarjetaNaranja extends Promocion {
        public PromoTarjetaNaranja(String nombre){ super(nombre); }

        @Override
        public double calcularDescuento(int cantidadTickets, double precioTicket){
            return cantidadTickets * precioTicket * 0.25;
        }
    }

    // ✅ Promoción "Sin Promo" (descuento 0)
    public static class PromoSinPromo extends Promocion {
        public PromoSinPromo() { super("Sin Promo"); }

        @Override
        public double calcularDescuento(int cantidad, double precio) {
            return 0.0;
        }
    }
}