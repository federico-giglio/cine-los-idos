package app.service;

import app.service.util.Input;
import app.domain.*;
import java.util.List;

/**
 * Servicio principal que coordina el flujo completo de la aplicacion de cine.
 * Gestiona la interaccion del usuario con todos los modulos del sistema.
 */
public class MenuService {
    private CarteleraService carteleraService;
    private CompraService compraService;
    private AsientosService asientosService;
    private CheckoutService checkoutService;
    private TicketService.PagoService pagoService;
    private TicketService.FormateoService formateoService;
    
    private OrdenDeCompra ordenActual;
    
    public MenuService() {
        this.carteleraService = new CarteleraService();
        this.compraService = new CompraService();
        this.asientosService = new AsientosService();
        this.checkoutService = new CheckoutService();
        this.pagoService = new TicketService.PagoService();
        this.formateoService = new TicketService.FormateoService();
        this.ordenActual = new OrdenDeCompra();
    }
    
    /**
     * Punto de entrada principal del servicio de menu.
     * Inicia el flujo interactivo y maneja la navegacion entre opciones.
     */
    public void iniciar() {
        mostrarBienvenida();
        Input.leerTexto("Presione Enter para continuar al menu principal...");
        
        while (true) {
            limpiarPantalla();
            int opcion = mostrarMenuPrincipal();
            
            switch (opcion) {
                case 1:
                    verCartelera();
                    break;
                case 2:
                    comprarTickets();
                    break;
                case 3:
                    elegirAsientos();
                    break;
                case 4:
                    aplicarCombosPromociones();
                    break;
                case 5:
                    procesarPagoYMostrarTicket();
                    break;
                case 6:
                    System.out.println("Gracias por usar Cine Los Idos! Hasta pronto.");
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
            Input.leerTexto("Presione Enter para continuar...");
        }
    }
    
    /**
     * Presenta el menu principal con opciones numeradas.
     * Centra visualmente el titulo para mejor presentacion.
     */
    private int mostrarMenuPrincipal() {
        System.out.println("=".repeat(50));
        System.out.println(centrarTexto("CINE LOS IDOS", 50));
        System.out.println("=".repeat(50));
        System.out.println("1. Ver Cartelera");
        System.out.println("2. Comprar Tickets");
        System.out.println("3. Elegir Asientos");
        System.out.println("4. Combos y Promociones");
        System.out.println("5. Pago y Ticket Final");
        System.out.println("6. Salir");
        System.out.println("=".repeat(50));
        
        return Input.leerEntero("Seleccione una opcion (1-6)");
    }
    
    /**
     * Muestra pantalla de bienvenida al iniciar la aplicacion.
     * Utiliza formato centrado para mejor presentacion visual.
     */
    private void mostrarBienvenida() {
        limpiarPantalla();
        System.out.println("=".repeat(50));
        System.out.println(centrarTexto("BIENVENIDO A CINE LOS IDOS", 50));
        System.out.println("=".repeat(50));
        System.out.println();
    }
    
    /**
     * Muestra la cartelera completa con todas las funciones disponibles.
     * Delega la visualizacion al servicio especializado.
     */
    private void verCartelera() {
        System.out.println("\nCARTELERA DISPONIBLE:");
        carteleraService.mostrarCartelera();
    }
    
    /**
     * Proceso de compra de tickets: seleccion de funcion y cantidad.
     * Valida entradas y actualiza la orden de compra actual.
     */
    private void comprarTickets() {
        System.out.println("\nCOMPRA DE TICKETS");
        
        carteleraService.mostrarCartelera();
        
        int totalFunciones = carteleraService.getTotalFunciones();
        if (totalFunciones == 0) {
            System.out.println("No hay funciones disponibles.");
            return;
        }
        
        int opcionFuncion = Input.leerEntero("Seleccione una funcion (1-" + totalFunciones + ")");
        Funcion funcion = carteleraService.obtenerFuncion(opcionFuncion - 1);
        
        if (funcion == null) {
            System.out.println("Funcion no valida.");
            return;
        }
        
        int cantidadTickets = Input.leerEntero("Cuantos tickets desea? (1-10)");
        if (cantidadTickets < 1 || cantidadTickets > 10) {
            System.out.println("Cantidad no valida. Debe ser entre 1 y 10.");
            return;
        }
        
        ordenActual = compraService.iniciarCompra(funcion, cantidadTickets);
        System.out.println("Tickets agregados a la orden.");
        
        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
    }
    
    /**
     * Gestiona la seleccion de asientos mostrando mapa de disponibilidad.
     * Valida que haya suficientes asientos libres antes de proceder.
     */
    private void elegirAsientos() {
        if (!compraService.estaListaParaAsientos(ordenActual)) {
            System.out.println("Primero debe seleccionar una funcion y cantidad de tickets.");
            return;
        }
        
        System.out.println("\nSELECCION DE ASIENTOS");
        
        if (!asientosService.tieneAsientosSuficientes(ordenActual.getCantidadTickets())) {
            System.out.println("No hay suficientes asientos disponibles.");
            return;
        }
        
        System.out.println("Asientos disponibles: " + asientosService.getAsientosDisponibles());
        mostrarMapaAsientos();
        
        for (int i = 0; i < ordenActual.getCantidadTickets(); i++) {
            boolean asientoValido = false;
            while (!asientoValido) {
                System.out.println("\nSeleccione asiento " + (i + 1) + ":");
                int fila = Input.leerEntero("Fila (1-5)");
                int columna = Input.leerEntero("Asiento (1-8)");
                
                if (asientosService.reservar(ordenActual, fila, columna)) {
                    System.out.println("Asiento reservado: Fila " + fila + ", Asiento " + columna);
                    asientoValido = true;
                } else {
                    System.out.println("Asiento ocupado o invalido. Intente otro.");
                }
            }
        }
        
        System.out.println("Todos los asientos han sido reservados.");
        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
    }
    
    /**
     * Muestra representacion visual de la sala con asientos disponibles/ocupados.
     * Utiliza matriz 5x8 con simbolos para indicar estado de cada asiento.
     */
    private void mostrarMapaAsientos() {
        System.out.println("\n   Mapa de Asientos:");
        System.out.println("    1   2   3   4   5   6   7   8");
        
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < 8; j++) {
                String estado = asientosService.getSala().estaLibre(i + 1, j + 1) ? "[-]" : "[*]";
                System.out.print(estado + " ");
            }
            System.out.println();
        }
        System.out.println("[-] Libre  [*] Ocupado");
    }
    
    /**
     * Gestiona seleccion de combos de comida y medios de pago disponibles.
     * Aplica descuentos segun el medio de pago seleccionado.
     */
    private void aplicarCombosPromociones() {
        if (!compraService.validarAsientos(ordenActual)) {
            System.out.println("Primero debe seleccionar los asientos.");
            return;
        }

        System.out.println("\nCOMBOS Y PROMOCIONES");

        // Seleccionar combo con validacion
        List<Combo> combos = checkoutService.combosDisponibles();
        System.out.println("\nCombos disponibles:");
        for (int i = 0; i < combos.size(); i++) {
            System.out.println((i + 1) + ". " + combos.get(i));
        }

        int opcionCombo = Input.leerEntero("Seleccione un combo (1-" + combos.size() + ")");
        if (opcionCombo > 0 && opcionCombo <= combos.size()) {
            checkoutService.elegirCombo(ordenActual, combos.get(opcionCombo - 1));
            System.out.println("Combo seleccionado: " + combos.get(opcionCombo - 1).getNombre());
        } else {
            System.out.println("Opcion de combo no valida. Continuando sin combo.");
        }

        // Seleccionar medio de pago con validacion
        List<MedioPago> mediosPago = checkoutService.mediosPagoDisponibles();
        System.out.println("\nMedios de pago disponibles:");
        for (int i = 0; i < mediosPago.size(); i++) {
            System.out.println((i + 1) + ". " + mediosPago.get(i).getNombre());
        }

        int opcionPago = Input.leerEntero("Seleccione medio de pago (1-" + mediosPago.size() + ")");
        if (opcionPago > 0 && opcionPago <= mediosPago.size()) {
            checkoutService.elegirMedioPago(ordenActual, mediosPago.get(opcionPago - 1));
            System.out.println("Medio de pago seleccionado: " + mediosPago.get(opcionPago - 1).getNombre());
        } else {
            System.out.println("X Opcion de medio de pago no valida. Debe seleccionar un medio de pago... Volviendo al menú principal... Vuelva a intentarlo... X");
            return; // Importante: si no selecciona medio de pago, no puede continuar
        }

        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
        System.out.println("Total a pagar: $" + ordenActual.calcularTotal());
    }
    
    /**
     * Procesa el pago final y genera el ticket de compra. Incluye pausas para
     * mejor experiencia de usuario al mostrar el ticket.
     */
    private void procesarPagoYMostrarTicket() {
        if (!compraService.estaListaParaPago(ordenActual)) {
            System.out.println("Complete todos los pasos anteriores antes del pago.");
            System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
            return;
        }

        System.out.println("\nPROCESANDO PAGO");
        System.out.println("Total a pagar: $" + ordenActual.calcularTotal());

        // Simulacion de procesamiento con mas tiempo
        System.out.print("Procesando");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000); //espera 1 seg en cada punto
                System.out.print(".");
            } catch (InterruptedException e) {
            }
        }
        System.out.println();

        if (pagoService.procesar(ordenActual)) {
            System.out.println("Pago procesado exitosamente!");

            System.out.println("\nGenerando tu ticket...");
            try {
                Thread.sleep(3000); //espera 3 seg para simular la devolución de un ticket
            } catch (InterruptedException e) {
            }

            System.out.println("\n" + "=".repeat(50));
            System.out.println(centrarTexto("TICKET DE COMPRA", 50));
            System.out.println("=".repeat(50));
            System.out.println(formateoService.formatearTicket(ordenActual));

            ordenActual = new OrdenDeCompra();
            asientosService.generarSalaNueva();

            System.out.println("\n" + "=".repeat(50));
            System.out.println("Compra finalizada exitosamente!");
        } else {
            System.out.println("Error en el procesamiento del pago.");
        }
    }
    
    /**
     * Limpia la pantalla de consola para mejor legibilidad.
     * Compatible con Windows y sistemas Unix/Linux.
     */
    private void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 30; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Centra un texto dentro de un ancho especifico agregando espacios.
     * 
     * @param texto Texto que se desea centrar
     * @param ancho Ancho total deseado para el texto centrado
     * @return Cadena de texto centrada con espacios
     */
    private String centrarTexto(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacios)) + texto;
    }
}