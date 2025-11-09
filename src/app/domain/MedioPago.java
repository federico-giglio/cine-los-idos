package app.domain;

/**
 * Sistema unificado de medios de pago y promociones.
 * Cada medio de pago incluye su propia promocion/descuento.
 * Ejemplos: Tarjeta Galicia 2x1, Efectivo sin descuento, etc.
 */
public abstract class MedioPago {
    protected final String nombre;
    
    /**
     * Constructor base para todos los medios de pago.
     */
    public MedioPago(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el nombre del medio de pago para mostrar al usuario.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Calcula el descuento a aplicar segun el medio de pago.
     * Cada subclase implementa su propia logica de descuento.
     */
    public abstract double calcularDescuento(double subtotal);
    
    /**
     * Medio de pago: Tarjeta Galicia con promocion 2x1 (50% de descuento).
     * Ejemplo: Si compras 2 tickets, pagas solo 1.
     */
    public static class Galicia2x1 extends MedioPago {
        public Galicia2x1() { 
            super("Tarjeta Galicia 2x1"); 
        }
        
        @Override 
        public double calcularDescuento(double subtotal) { 
            return subtotal * 0.5; 
        }
    }
    
    /**
     * Medio de pago: Tarjeta Nacion con promocion 2x1 (50% de descuento).
     */
    public static class Nacion2x1 extends MedioPago {
        public Nacion2x1() { 
            super("Tarjeta Nacion 2x1"); 
        }
        
        @Override 
        public double calcularDescuento(double subtotal) { 
            return subtotal * 0.5; 
        }
    }
    
    /**
     * Medio de pago: Club La Nacion con 10% de descuento.
     */
    public static class ClubLaNacion extends MedioPago {
        public ClubLaNacion() { 
            super("Club La Nacion 10%"); 
        }
        
        @Override 
        public double calcularDescuento(double subtotal) { 
            return subtotal * 0.1; 
        }
    }
    
    /**
     * Medio de pago: Efectivo (sin promociones de descuento).
     */
    public static class Efectivo extends MedioPago {
        public Efectivo() { 
            super("Efectivo"); 
        }
        
        @Override 
        public double calcularDescuento(double subtotal) { 
            return 0; 
        }
    }
    
    /**
     * Metodo para mostrar el medio de pago en combos y listas.
     */
    @Override
    public String toString() {
        return nombre;
    }
}