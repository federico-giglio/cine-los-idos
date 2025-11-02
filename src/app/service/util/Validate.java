package app.service.util;

/**
 * Utilidad para validar entradas y reglas de negocio.
 */
public class Validate {

    public static boolean rangoEntero(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }

    public static boolean noVacio(String texto) {
        return texto != null && !texto.isBlank();
    }
}