
package app.domain;

/**
 * Representa un combo de productos del cine.
 * Un combo puede incluir, por ejemplo, pochoclos + gaseosa,
 * y tiene un nombre y un precio fijo.
 *
 * Esta clase es un simple contenedor de datos (POJO),
 * que luego se usa en la OrdenDeCompra y en los menús.
 */
public class Combo {
    // Nombre del combo (ejemplo: "Combo Básico", "Combo Familiar")
    private final String nombre;

    // Precio del combo (ejemplo: 5000.0)
    private final double precio;

    // Constructor: inicializa el combo con un nombre y un precio.
    // Ambos atributos son finales, por lo que no se pueden modificar después.
    public Combo(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Devuelve el nombre del combo
    public String getNombre() {
        return nombre;
    }

    // Devuelve el precio del combo
    public double getPrecio() {
        return precio;
    }

    // Sobrescribe el método toString() para mostrar el combo de forma legible.
    // Ejemplo: "Combo Básico ($5000.0)"
    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}