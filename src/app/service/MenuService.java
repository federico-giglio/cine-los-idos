package app.service;

import app.service.util.Input;
import app.domain.*;
import java.util.List;

/**
 * 
 * Servicio (Clase) que orquesta todo el flujo de la aplicación. Es llamado desde el main
 * 
 **/

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
     * 
     * Metodo que llama al constructor mostrarBienvenida y detecta el input ingresado por el usuario.
     * 
     * */
    
    public void iniciar() {
        mostrarBienvenida();
        
        while (true) {
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
                    System.out.println("¡Gracias por usar Cine Los Idos! Hasta pronto.");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
            Input.leerTexto("Presione Enter para continuar...");
        }
    }
    
    /**
     * 
     * Metodo que muestra el menu principal.
     * 
     * */
    
    private int mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          CINE LOS IDOS");
        System.out.println("=".repeat(40));
        System.out.println("1. Ver Cartelera");
        System.out.println("2. Comprar Tickets");
        System.out.println("3. Elegir Asientos");
        System.out.println("4. Combos y Promociones");
        System.out.println("5. Pago y Ticket Final");
        System.out.println("6. Salir");
        System.out.println("=".repeat(40));
        
        return Input.leerEntero("Seleccione una opción (1-6)");
    }
    
     /**
     * 
     * Metodo que muestra la cartelera luminosa (no la pude hacer parpadear)
     * 
     * */
    
    private void mostrarBienvenida() {
        System.out.println("✨".repeat(50));
        System.out.println("          BIENVENIDO A CINE LOS IDOS");
        System.out.println("✨".repeat(50));
        System.out.println();
    }
    
     /**
     * 
     * Metodo que llama al constructor mostrarCartelera desde la clase carteleraService y presenta el mensaje de la cartelera
     * 
     * */
    
    private void verCartelera() {
        System.out.println("\n🎬 CARTELERA DISPONIBLE:");
        carteleraService.mostrarCartelera();
    }
    
     /**
     * 
     * Metodo que permite elegir funcion y cantidad de tickets llamando a diferentes constructores de la clase carteleraservice y compraservice
     * Obtiene el numero total de funciones disponibles en la cartelera.
     * Obtiene una funcion especifica de la cartelera por su indice.
     * Inicializa una nueva orden de compra con los datos básicos.
     * Obtiene un resumen detallado del estado actual de la compra.
     * 
     * */    
    
    private void comprarTickets() {
        System.out.println("\n🎟️  COMPRA DE TICKETS");
        
        // Muestra nuevamente la cartelera para guiar en la compra del ticket llamando al constructor mostrarCartelera desde la clase carteleraService.
     
        carteleraService.mostrarCartelera();
        
        // Seleccionar función verificando la disponibilidad llamando al constructor getTotalFunciones
        int totalFunciones = carteleraService.getTotalFunciones();
        if (totalFunciones == 0) {
            System.out.println("No hay funciones disponibles.");
            return;
        }
        
        int opcionFuncion = Input.leerEntero("Seleccione una función (1-" + totalFunciones + ")");
        Funcion funcion = carteleraService.obtenerFuncion(opcionFuncion - 1);
        
        if (funcion == null) {
            System.out.println("❌ Función no válida.");
            return;
        }
        
        // Seleccionar cantidad de tickets condicionando un maximo y minimo
        int cantidadTickets = Input.leerEntero("¿Cuántos tickets desea? (1-10)");
        if (cantidadTickets < 1 || cantidadTickets > 10) {
            System.out.println("❌ Cantidad no válida. Debe ser entre 1 y 10.");
            return;
        }
        
        // Iniciar compra llamando al constructor iniciarCompra desde la clase compraservice
        ordenActual = compraService.iniciarCompra(funcion, cantidadTickets);
        System.out.println("✅ Tickets agregados a la orden.");
        
        // Mouestra el resumen parcial de la compra
        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
    }
    
     /**
     * 
     * Metodo que muestra la lista de asientos llamando a diferentes contructores de las clases compraservice y asientosservice
     * Verifica si la orden esta lista para proceder a la siguiente etapa.
     * Valida que haya suficientes asientos libres para la cantidad solicitada.
     * Obtiene la cantidad actual de asientos libres en la sala.
     * Reserva los asientos
     * 
     * */    
    
    private void elegirAsientos() {
        if (!compraService.estaListaParaAsientos(ordenActual)) {
            System.out.println("❌ Primero debe seleccionar una función y cantidad de tickets.");
            return;
        }
        
        System.out.println("\n💺 SELECCIÓN DE ASIENTOS");
        
        // Verificar disponibilidad
        if (!asientosService.tieneAsientosSuficientes(ordenActual.getCantidadTickets())) {
            System.out.println("❌ No hay suficientes asientos disponibles.");
            return;
        }
        
        System.out.println("Asientos disponibles: " + asientosService.getAsientosDisponibles());
        mostrarMapaAsientos();
        
        // Reservar asientos
        for (int i = 0; i < ordenActual.getCantidadTickets(); i++) {
            boolean asientoValido = false;
            while (!asientoValido) {
                System.out.println("\nSeleccione asiento " + (i + 1) + ":");
                int fila = Input.leerEntero("Fila (1-5)");
                int columna = Input.leerEntero("Asiento (1-8)");
                
                if (asientosService.reservar(ordenActual, fila, columna)) {
                    System.out.println("✅ Asiento reservado: Fila " + fila + ", Asiento " + columna);
                    asientoValido = true;
                } else {
                    System.out.println("❌ Asiento ocupado o inválido. Intente otro.");
                }
            }
        }
        
        System.out.println("✅ Todos los asientos han sido reservados.");
        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
    }
    
     /**
     * 
     * Metodo que crea el mapa (matriz) de los asientos disponibles y ocupados
     * 
     * */    
    
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
     * 
     * Metodo que muestra los combos y promociones llamando a diferentes constructores de las clases compraservice y checkoutservice
     * Valida que todos los asientos reservados correspondan a la cantidad de tickets.
     * Proporciona la lista de todos los combos disponibles en el cine.
     * Registra el combo seleccionado por el usuario en su orden de compra.
     * Proporciona la lista de medios de pago aceptados por el cine.
     * Registra el medio de pago seleccionado por el usuario en su orden.
     * Obtiene un resumen detallado del estado actual de la compra (pero mas actualizado).
     * 
     * */    
    
    private void aplicarCombosPromociones() {
        if (!compraService.validarAsientos(ordenActual)) {
            System.out.println("❌ Primero debe seleccionar los asientos.");
            return;
        }
        
        System.out.println("\n🍿 COMBOS Y PROMOCIONES");
        
        // Seleccionar combo
        List<Combo> combos = checkoutService.combosDisponibles();
        System.out.println("\nCombos disponibles:");
        for (int i = 0; i < combos.size(); i++) {
            System.out.println((i + 1) + ". " + combos.get(i));
        }
        
        int opcionCombo = Input.leerEntero("Seleccione un combo (1-" + combos.size() + ")");
        if (opcionCombo > 0 && opcionCombo <= combos.size()) {
            checkoutService.elegirCombo(ordenActual, combos.get(opcionCombo - 1));
            System.out.println("✅ Combo seleccionado: " + combos.get(opcionCombo - 1).getNombre());
        }
        
        // Seleccionar medio de pago
        List<MedioPago> mediosPago = checkoutService.mediosPagoDisponibles();
        System.out.println("\nMedios de pago disponibles:");
        for (int i = 0; i < mediosPago.size(); i++) {
            System.out.println((i + 1) + ". " + mediosPago.get(i).getNombre());
        }
        
        int opcionPago = Input.leerEntero("Seleccione medio de pago (1-" + mediosPago.size() + ")");
        if (opcionPago > 0 && opcionPago <= mediosPago.size()) {
            checkoutService.elegirMedioPago(ordenActual, mediosPago.get(opcionPago - 1));
            System.out.println("✅ Medio de pago seleccionado: " + mediosPago.get(opcionPago - 1).getNombre());
        }
        
        // Mostrar resumen con totales
        System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
        System.out.println("💰 Total a pagar: $" + ordenActual.calcularTotal());
    }
    
     /**
     * 
     * Metodo que muestra el tiquet final de la compra llamando a diferentes constructores de las Clases compraservice
     * Verifica si la orden esta lista para proceder al pago.
     * Procesa el pago.
     * Muestra el ticket.
     * 
     * */    
    
    private void procesarPagoYMostrarTicket() {
        if (!compraService.estaListaParaPago(ordenActual)) {
            System.out.println("❌ Complete todos los pasos anteriores antes del pago.");
            System.out.println("\n" + compraService.obtenerResumenCompra(ordenActual));
            return;
        }
        
        System.out.println("\n💳 PROCESANDO PAGO");
        System.out.println("Total a pagar: $" + ordenActual.calcularTotal());
        
        // Procesar pago
        if (pagoService.procesar(ordenActual)) {
            System.out.println("✅ Pago procesado exitosamente!");
            
            // Mostrar ticket
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           TICKET DE COMPRA");
            System.out.println("=".repeat(50));
            System.out.println(formateoService.formatearTicket(ordenActual));
            
            // Reiniciar para nueva compra
            ordenActual = new OrdenDeCompra();
            asientosService.generarSalaNueva();
        } else {
            System.out.println("❌ Error en el procesamiento del pago.");
        }
    }
}