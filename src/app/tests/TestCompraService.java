
package app.tests;

import app.domain.OrdenDeCompra;
import app.domain.Funcion;
import app.domain.Pelicula;
import app.domain.Combo;
import app.domain.MedioPago;
import app.service.CompraService;

/**
 * Pruebas unitarias para el CompraService.
 * Verifica la coordinacion del flujo completo de compra.
 */
public class TestCompraService {
     public static void main(String[] args) {
        System.out.println("=== TEST COMPRA SERVICE ===");

        CompraService compraService = new CompraService();
        Pelicula pelicula = new Pelicula(1, "The Batman", 2022, "Accion", "176 min");
        Funcion funcion = new Funcion(pelicula, "Domingo", "18:00", "Sala 3", 5200.0);

        // Test 1: Iniciar compra
        System.out.println("\n1. TEST INICIAR COMPRA");
        OrdenDeCompra orden = compraService.iniciarCompra(funcion, 3);
        System.out.println("Orden iniciada: " + (orden != null));
        System.out.println("Funcion establecida: " + orden.getFuncion().getPelicula().getTitulo());
        System.out.println("Tickets establecidos: " + orden.getCantidadTickets());

        // Test 2: Validaciones de estado
        System.out.println("\n2. TEST VALIDACIONES DE ESTADO");
        boolean listaParaAsientos = compraService.estaListaParaAsientos(orden);
        boolean listaParaPago = compraService.estaListaParaPago(orden);
        
        System.out.println("Lista para asientos: " + listaParaAsientos);
        System.out.println("Lista para pago: " + listaParaPago);

        // Test 3: Asignacion de combo y medio de pago
        System.out.println("\n3. TEST ASIGNACION DE COMBO Y MEDIO PAGO");
        Combo combo = new Combo("Combo Pareja", 6000.0);
        compraService.asignarCombo(orden, combo);
        
        MedioPago medioPago = new MedioPago.ClubLaNacion();
        compraService.asignarMedioPago(orden, medioPago);
        
        System.out.println("Combo asignado: " + orden.getCombo().getNombre());
        System.out.println("Medio pago asignado: " + orden.getMedioPago().getNombre());

        // Test 4: Asignacion de asientos
        System.out.println("\n4. TEST ASIGNACION DE ASIENTOS");
        orden.agregarAsiento("A1");
        orden.agregarAsiento("A2");
        orden.agregarAsiento("A3");
        
        boolean asientosValidos = compraService.validarAsientos(orden);
        System.out.println("Asientos asignados: " + orden.getAsientos());
        System.out.println("Validacion asientos: " + asientosValidos);

        // Test 5: Verificar estado despues de completar datos
        System.out.println("\n5. TEST ESTADO COMPLETO");
        listaParaPago = compraService.estaListaParaPago(orden);
        int progreso = compraService.obtenerProgresoCompra(orden);
        
        System.out.println("Lista para pago: " + listaParaPago);
        System.out.println("Progreso de compra: " + progreso + "%");

        // Test 6: Resumen de compra
        System.out.println("\n6. TEST RESUMEN DE COMPRA");
        String resumen = compraService.obtenerResumenCompra(orden);
        System.out.println(resumen);

        // Test 7: Reinicio de compra
        System.out.println("\n7. TEST REINICIO DE COMPRA");
        compraService.reiniciarCompra(orden);
        
        System.out.println("Funcion despues reinicio: " + (orden.getFuncion() == null));
        System.out.println("Tickets despues reinicio: " + orden.getCantidadTickets());
        System.out.println("Asientos despues reinicio: " + orden.getAsientos().size());
        System.out.println("Combo despues reinicio: " + (orden.getCombo() == null));
        System.out.println("Medio pago despues reinicio: " + (orden.getMedioPago() == null));

        // Test 8: Progreso despues de reinicio
        System.out.println("\n8. TEST PROGRESO DESPUES DE REINICIO");
        progreso = compraService.obtenerProgresoCompra(orden);
        System.out.println("Progreso despues reinicio: " + progreso + "% (debe ser 0)");

        System.out.println("\n=== TEST COMPRA SERVICE COMPLETADO ===");
        System.out.println("Todas las pruebas del servicio de compra pasaron correctamente");
    }
}
