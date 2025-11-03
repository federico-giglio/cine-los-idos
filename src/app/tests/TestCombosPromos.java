package app.tests;

// Importa las clases necesarias del dominio, servicio y utilidades
import app.domain.Combo;
import app.domain.OrdenDeCompra;
import app.domain.MedioPago;
import app.service.CheckoutService;
import app.service.util.Input;
import app.service.util.Validate;

import java.util.List;

/**
 * Clase de prueba para probar la seleccion de combos y medios de pago.
 * Simula el flujo completo de seleccion de combos y medios de pago/promociones.
 */
public class TestCombosPromos {

    /**
     * Metodo principal que ejecuta el test de combos y medios de pago.
     * 
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Crea el servicio de checkout y la orden de compra vacia
        CheckoutService checkout = new CheckoutService();
        OrdenDeCompra orden = new OrdenDeCompra();

        // Variables de control para el flujo del menu
        boolean finalizarCompra = false;
        boolean comboElegido = false;
        boolean medioPagoElegido = false;

        // Bucle principal del menu
        while (!finalizarCompra) {
            // Muestra las opciones disponibles
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Elegir combo");
            System.out.println("2. Elegir medio de pago");
            System.out.println("3. Ver seleccion actual");
            System.out.println("4. Calcular total");
            System.out.println("0. Finalizar compra");

            // Lee la opcion del usuario
            int opcion = Input.leerEntero("Seleccione una opcion");

            // Ejecuta la accion correspondiente segun la opcion elegida
            switch (opcion) {
                case 1 -> {
                    // Permite elegir un combo y marca que ya se eligio
                    elegirCombo(orden, checkout);
                    comboElegido = true;
                }
                case 2 -> {
                    // Permite elegir medio de pago (ya no requiere combo primero)
                    elegirMedioPago(orden, checkout);
                    medioPagoElegido = true;
                }
                case 3 -> {
                    // Muestra la seleccion actual
                    mostrarSeleccion(orden);
                }
                case 4 -> {
                    // Calcula y muestra el total
                    calcularTotal(orden, checkout);
                }
                case 0 -> finalizarCompra = true; // Sale del menu
                default -> System.out.println("Opcion invalida."); // Maneja entradas incorrectas
            }
        }

        // Mensaje final de despedida
        System.out.println("Gracias por venir a Cine Los Idos!");
    }

    /**
     * Metodo para mostrar y seleccionar un combo.
     * 
     * @param orden Orden de compra a la que asignar el combo
     * @param checkout Servicio de checkout para obtener combos disponibles
     */
    private static void elegirCombo(OrdenDeCompra orden, CheckoutService checkout) {
        List<Combo> combos = checkout.combosDisponibles(); // Obtiene la lista de combos
        System.out.println("\n=== COMBOS DISPONIBLES ===");
        for (int i = 0; i < combos.size(); i++) {
            System.out.println((i + 1) + ". " + combos.get(i)); // Muestra cada combo con indice
        }

        int opcion = Input.leerEntero("Seleccione un combo"); // Lee la opcion del usuario
        if (Validate.rangoEntero(opcion, 1, combos.size())) {
            // Si la opcion es valida, asigna el combo a la orden
            checkout.elegirCombo(orden, combos.get(opcion - 1));
            System.out.println("Combo seleccionado: " + combos.get(opcion - 1));
        } else {
            System.out.println("Opcion invalida."); // Maneja seleccion fuera de rango
        }
    }

    /**
     * Metodo para mostrar y seleccionar un medio de pago.
     * 
     * @param orden Orden de compra a la que asignar el medio de pago
     * @param checkout Servicio de checkout para obtener medios de pago disponibles
     */
    private static void elegirMedioPago(OrdenDeCompra orden, CheckoutService checkout) {
        List<MedioPago> mediosPago = checkout.mediosPagoDisponibles(); // Obtiene la lista de medios de pago
        System.out.println("\n=== MEDIOS DE PAGO DISPONIBLES ===");
        for (int i = 0; i < mediosPago.size(); i++) {
            System.out.println((i + 1) + ". " + mediosPago.get(i).getNombre()); // Muestra cada medio de pago con indice
        }

        int opcion = Input.leerEntero("Seleccione un medio de pago"); // Lee la opcion del usuario
        if (Validate.rangoEntero(opcion, 1, mediosPago.size())) {
            // Si la opcion es valida, asigna el medio de pago a la orden
            checkout.elegirMedioPago(orden, mediosPago.get(opcion - 1));
            System.out.println("Medio de pago seleccionado: " + mediosPago.get(opcion - 1).getNombre());
        } else {
            System.out.println("Opcion invalida."); // Maneja seleccion fuera de rango
        }
    }

    /**
     * Muestra la seleccion actual del usuario (combo y medio de pago).
     * 
     * @param orden Orden de compra de la que mostrar la seleccion
     */
    private static void mostrarSeleccion(OrdenDeCompra orden) {
        System.out.println("\n=== SELECCION ACTUAL ===");
        System.out.println("Combo: " + (orden.getCombo() != null ? orden.getCombo() : "Ninguno"));
        System.out.println("Medio de pago: " + (orden.getMedioPago() != null ? orden.getMedioPago().getNombre() : "No seleccionado"));
        
        // Mostrar cantidad de tickets si esta establecida
        if (orden.getCantidadTickets() > 0) {
            System.out.println("Tickets: " + orden.getCantidadTickets());
        }
    }

    /**
     * Calcula y muestra el total de la orden actual.
     * 
     * @param orden Orden de compra a calcular
     * @param checkout Servicio de checkout para realizar calculos
     */
    private static void calcularTotal(OrdenDeCompra orden, CheckoutService checkout) {
        // Establecer una cantidad de tickets para prueba si no esta establecida
        if (orden.getCantidadTickets() == 0) {
            orden.setCantidadTickets(2); // Valor por defecto para pruebas
        }
        
        System.out.println("\n=== CALCULO DE TOTAL ===");
        double subtotal = checkout.subtotalTickets(orden);
        double descuento = checkout.descuento(orden);
        double total = checkout.totalFinal(orden);
        
        System.out.println("Subtotal tickets: $" + String.format("%.2f", subtotal));
        System.out.println("Descuento: -$" + String.format("%.2f", descuento));
        
        if (orden.getCombo() != null) {
            System.out.println("Combo: $" + String.format("%.2f", orden.getCombo().getPrecio()));
        }
        
        System.out.println("TOTAL FINAL: $" + String.format("%.2f", total));
    }
}