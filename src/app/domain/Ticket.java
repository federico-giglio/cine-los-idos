package app.domain;

import java.util.List;

/**
 * Clase contenedora de entidades y medios de pago.
 */
public class Ticket {

    // ============================================
    // ====== ENTIDAD PRINCIPAL ORDENCOMPRA =======
    // ============================================
    public static class OrdenDeCompra {
        private String pelicula;
        private String dia;
        private String horario;
        private int cantidadTickets;
        private double precioUnitario;
        private List<String> asientos;
        private String combo;
        private double precioCombo;
        private MedioPago medioPago;

        // Getters y setters
        public String getPelicula() { return pelicula; }
        public void setPelicula(String pelicula) { this.pelicula = pelicula; }

        public String getDia() { return dia; }
        public void setDia(String dia) { this.dia = dia; }

        public String getHorario() { return horario; }
        public void setHorario(String horario) { this.horario = horario; }

        public int getCantidadTickets() { return cantidadTickets; }
        public void setCantidadTickets(int cantidadTickets) { this.cantidadTickets = cantidadTickets; }

        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

        public List<String> getAsientos() { return asientos; }
        public void setAsientos(List<String> asientos) { this.asientos = asientos; }

        public String getCombo() { return combo; }
        public void setCombo(String combo) { this.combo = combo; }

        public double getPrecioCombo() { return precioCombo; }
        public void setPrecioCombo(double precioCombo) { this.precioCombo = precioCombo; }

        public MedioPago getMedioPago() { return medioPago; }
        public void setMedioPago(MedioPago medioPago) { this.medioPago = medioPago; }
    }

    // ============================================
    // ============ MEDIOS DE PAGO ================
    // ============================================

    public static abstract class MedioPago {
        protected String nombre;

        public MedioPago(String nombre) { this.nombre = nombre; }
        public String getNombre() { return nombre; }
        public double calcularDescuento(double subtotalTickets) { return 0.0; }
        @Override
        public String toString() { return nombre; }
    }

    public static class TarjetaGalicia extends MedioPago {
        public TarjetaGalicia() { super("Tarjeta Galicia"); }
        @Override public double calcularDescuento(double subtotal) { return subtotal * 0.5; }
    }

    public static class TarjetaNacion extends MedioPago {
        public TarjetaNacion() { super("Tarjeta Nación"); }
        @Override public double calcularDescuento(double subtotal) { return subtotal * 0.5; }
    }

    public static class ClubLaNacion extends MedioPago {
        public ClubLaNacion() { super("Club La Nación"); }
        @Override public double calcularDescuento(double subtotal) { return subtotal * 0.3; }
    }

    public static class TarjetaNaranja extends MedioPago {
        public TarjetaNaranja() { super("Tarjeta Naranja"); }
    }

    public static class PromoSinPromo extends MedioPago {
        public PromoSinPromo() { super("Sin promoción"); }
    }
}

