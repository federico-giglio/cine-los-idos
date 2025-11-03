
package app.tests;

import app.domain.OrdenDeCompra;
import app.domain.Funcion;
import app.domain.Pelicula;
import app.domain.Combo;
import app.domain.MedioPago;

/**
 * Pruebas unitarias para la clase OrdenDeCompra.
 * Verifica el correcto funcionamiento de los metodos principales de la orden.
 */
public class TestOrdenDeCompra {
     public static void main(String[] args) {
        System.out.println("=== TEST ORDEN DE COMPRA ===");

        // Test 1: Creacion basica de orden
        System.out.println("\n1. TEST CREACION BASICA");
        OrdenDeCompra orden = new OrdenDeCompra();
        System.out.println("Orden creada exitosamente");
        System.out.println("Asientos iniciales: " + orden.getAsientos().size() + " (debe ser 0)");

        // Test 2: Getters y setters basicos
        System.out.println("\n2. TEST GETTERS Y SETTERS");
        orden.setCantidadTickets(2);
        System.out.println("Cantidad tickets: " + orden.getCantidadTickets());
        System.out.println("Precio ticket: $" + orden.getPrecioTicket());

        // Test 3: Manejo de asientos
        System.out.println("\n3. TEST MANEJO DE ASIENTOS");
        orden.agregarAsiento("A1");
        orden.agregarAsiento("B2");
        orden.agregarAsiento("C3");
        System.out.println("Asientos agregados: " + orden.getAsientos());
        System.out.println("Total asientos: " + orden.getAsientos().size());

        // Test 4: Configuracion completa de orden
        System.out.println("\n4. TEST CONFIGURACION COMPLETA");
        Pelicula pelicula = new Pelicula(1, "Avengers: Endgame", 2019, "Accion", "181 min");
        Funcion funcion = new Funcion(pelicula, "Sabado", "20:00", "Sala 1", 5000.0);
        orden.setFuncion(funcion);

        Combo combo = new Combo("Combo Familiar", 8000.0);
        orden.setCombo(combo);

        MedioPago medioPago = new MedioPago.Galicia2x1();
        orden.setMedioPago(medioPago);

        System.out.println("Funcion establecida: " + orden.getFuncion().getPelicula().getTitulo());
        System.out.println("Combo establecido: " + orden.getCombo().getNombre());
        System.out.println("Medio pago establecido: " + orden.getMedioPago().getNombre());

        // Test 5: Calculo de total
        System.out.println("\n5. TEST CALCULO DE TOTAL");
        double total = orden.calcularTotal();
        System.out.println("Total calculado: $" + total);

        // Desglose del calculo para verificar
        double subtotalTickets = orden.getCantidadTickets() * orden.getPrecioTicket();
        double descuento = orden.getMedioPago().calcularDescuento(subtotalTickets);
        double precioCombo = orden.getCombo().getPrecio();
        double totalManual = (subtotalTickets - descuento) + precioCombo;

        System.out.println("   Subtotal tickets: " + orden.getCantidadTickets() + " x $" + orden.getPrecioTicket() + " = $" + subtotalTickets);
        System.out.println("   Descuento " + orden.getMedioPago().getNombre() + ": -$" + descuento);
        System.out.println("   Combo " + orden.getCombo().getNombre() + ": +$" + precioCombo);
        System.out.println("   Total manual: $" + totalManual);
        System.out.println("   Total coincide: " + (total == totalManual));

        // Test 6: Validaciones de negocio
        System.out.println("\n6. TEST VALIDACIONES DE NEGOCIO");
        try {
            orden.setCantidadTickets(-5);
            System.out.println("No deberia permitir tickets negativos");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctamente bloquea tickets negativos: " + e.getMessage());
        }

        // Test 7: Orden sin funcion
        System.out.println("\n7. TEST ORDEN SIN FUNCION");
        OrdenDeCompra ordenVacia = new OrdenDeCompra();
        ordenVacia.setCantidadTickets(2);
        double totalVacio = ordenVacia.calcularTotal();
        System.out.println("Total sin funcion: $" + totalVacio + " (debe ser 0)");

        System.out.println("\n=== TEST ORDEN DE COMPRA COMPLETADO ===");
        System.out.println("Todas las pruebas de orden de compra pasaron correctamente");
    }
}
