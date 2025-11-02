package app.tests;

// Importa las clases necesarias del dominio, servicio y utilidades
import app.domain.Combo;
import app.domain.OrdenDeCompra;
import app.domain.promo.Promocion;
import app.service.CheckoutService;
import app.service.util.Input;
import app.service.util.Validate;

import java.util.List;

public class TestCombosPromos {

    public static void main(String[] args) {
        // Crea el servicio de checkout y la orden de compra vacía
        CheckoutService checkout = new CheckoutService();
        OrdenDeCompra orden = new OrdenDeCompra();

        // Variables de control para el flujo del menú
        boolean finalizarCompra = false;
        boolean comboElegido = false;
        boolean promoElegida = false;

        // Bucle principal del menú
        while (!finalizarCompra) {
            // Muestra las opciones disponibles
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Elegir combo");
            System.out.println("2. Elegir promoción");
            System.out.println("3. Ver selección actual");
            System.out.println("0. Finalizar compra");

            // Lee la opción del usuario
            int opcion = Input.leerEntero("Seleccione una opción");

            // Ejecuta la acción correspondiente según la opción elegida
            switch (opcion) {
                case 1 -> {
                    // Permite elegir un combo y marca que ya se eligió
                    elegirCombo(orden, checkout);
                    comboElegido = true;
                }
                case 2 -> {
                    // Solo permite elegir promoción si ya se eligió combo
                    if (!comboElegido) {
                        System.out.println("⚠️ Primero debe elegir un combo antes de seleccionar una promoción.");
                    } else {
                        elegirPromocion(orden, checkout);
                        promoElegida = true;
                    }
                }
                case 3 -> {
                    // Muestra la selección actual si ya se eligió combo y promoción
                    if (!comboElegido) {
                        System.out.println("⚠️ Primero debe elegir un combo.");
                    } else if (!promoElegida) {
                        System.out.println("⚠️ Primero debe elegir una promoción.");
                    } else {
                        mostrarSeleccion(orden);
                    }
                }
                case 0 -> finalizarCompra = true; // Sale del menú
                default -> System.out.println("⚠️ Opción inválida."); // Maneja entradas incorrectas
            }
        }

        // Mensaje final de despedida
        System.out.println("👋 Gracias por venir a Cine Los Idos!");
    }

    // Método para mostrar y seleccionar un combo
    private static void elegirCombo(OrdenDeCompra orden, CheckoutService checkout) {
        List<Combo> combos = checkout.combosDisponibles(); // Obtiene la lista de combos
        System.out.println("\n=== COMBOS DISPONIBLES ===");
        for (int i = 0; i < combos.size(); i++) {
            System.out.println((i + 1) + ". " + combos.get(i)); // Muestra cada combo con índice
        }

        int opcion = Input.leerEntero("Seleccione un combo"); // Lee la opción del usuario
        if (Validate.rangoEntero(opcion, 1, combos.size())) {
            // Si la opción es válida, asigna el combo a la orden
            checkout.elegirCombo(orden, combos.get(opcion - 1));
            System.out.println("✅ Combo seleccionado: " + combos.get(opcion - 1));
        } else {
            System.out.println("⚠️ Opción inválida."); // Maneja selección fuera de rango
        }
    }

    // Método para mostrar y seleccionar una promoción
    private static void elegirPromocion(OrdenDeCompra orden, CheckoutService checkout) {
        List<Promocion> promos = checkout.promocionesDisponibles(); // Obtiene la lista de promociones
        System.out.println("\n=== PROMOCIONES DISPONIBLES ===");
        for (int i = 0; i < promos.size(); i++) {
            System.out.println((i + 1) + ". " + promos.get(i).getNombre()); // Muestra cada promoción con índice
        }

        int opcion = Input.leerEntero("Seleccione una promoción"); // Lee la opción del usuario
        if (Validate.rangoEntero(opcion, 1, promos.size())) {
            // Si la opción es válida, asigna la promoción a la orden
            checkout.elegirPromocion(orden, promos.get(opcion - 1));
            System.out.println("✅ Promoción seleccionada: " + promos.get(opcion - 1).getNombre());
        } else {
            System.out.println("⚠️ Opción inválida."); // Maneja selección fuera de rango
        }
    }

    // Muestra la selección actual del usuario (combo y promoción)
    private static void mostrarSeleccion(OrdenDeCompra orden) {
        System.out.println("\n=== SELECCIÓN ACTUAL ===");
        System.out.println("Combo: " + (orden.getCombo() != null ? orden.getCombo() : "Ninguno"));
        System.out.println("Promoción: " + (orden.getPromocion() != null ? orden.getPromocion().getNombre() : "Sin Promo"));
    }
}