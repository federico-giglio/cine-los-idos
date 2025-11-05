/**
 *Clase principal (Main) que inicia la aplicación del cine Los Idos 
 **/

package app.main;

import app.service.MenuService;

public class Main {
    
    /**
     *Clase Main que inicia la aplicación llamando al constructor MenuService desde la Clase MenuService 
     * y llama construtor iniciar para mostrar la cartelera y el menú como primera instancia
     **/
    
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        menuService.iniciar();
    }
}