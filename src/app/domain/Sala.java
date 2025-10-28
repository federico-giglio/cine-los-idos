// Cine_Los_Idos: Clase Sala.java
/*
Esta es la clase Sala, la cual contiene el constructor de la sala lógica para la ocupación aleatoria de asientos.
*/

package app.domain;

import java.util.Random;

public class Sala {
    //Constantes (Filas y Columnas => Matriz de la Sala)
    public static final int FILAS = 5;
    public static final int COLUMNAS = 8;
    
    //Enum para los estados de los asientos: Libre/Ocupado => Un enum es un tipo de dato que contiene constantes predefinidas (Libre/Ocupado)
    public enum Estado{ LIBRE, OCUPADO }
    
    //Creamos la matriz de asientos 
    private Estado [][] asientos;
    
    public Sala() {  //Constructor de la matriz, que a su vez genera una ocupación aleatoria de asientos con el método generarOcupacionAleatoria()
        asientos = new Estado[FILAS][COLUMNAS];  //Se inicializa la matriz asientos con un tamaño de Filas/Columnas (5,8)
        generarOcupacionAleatoria();     //Asigna de manera aleatoria un estado a cada elemento de la matriz (Libre/Ocupado)
    }
    
    //MÉTODOS:
    //Método generarOcupacionAleatoria: asigna valores al azar de 0-7 para generar ocupación de los asientos al crear la sala.
    public void generarOcupacionAleatoria(){
        Random rand = new Random();
        for (int i = 0; i < FILAS; i++){
            for(int j = 0; j < COLUMNAS; j++){
                //Aproximadamente 1/7 de probabilidad de estar ocupado, es decir genera un número aleatorio entre 0-6
                if (rand.nextInt(7)==0){ //Cuando el número aleatorio generado es == 0 el asiento pasa a Estado.OCUPADO.
                    asientos[i][j] = Estado.OCUPADO;
                } else {
                    asientos[i][j] = Estado.LIBRE;
                }
            }
        }             
  }  

    //Método estaLibre: Primero chequea que el número de filas y columnas esté dentro de los límites con el método esAsientoValido, y luego lo asigna como LIBRE.
    public boolean estaLibre(int fila,  int columna){
               if (!esAsientoValido(fila, columna)) return false;
               return asientos[fila-1][columna-1] == Estado.LIBRE;
           } 
    //Método reservar: retorna false si el asiento está ocupado, y si no retorna true y ocupa el asiento (Estado.OCUPADO) 
    public boolean reservar(int fila, int columna){
                if (!estaLibre(fila, columna)) return false;
                asientos[fila-1][columna-1] = Estado.OCUPADO;
                return true;
            }                                                                                                           
    //Método esAsientoValido: corrobora que el número de asiento (fila/columna) esté dentro de los límites de la sala.
    private boolean esAsientoValido(int fila, int columna){
        return (fila >= 1 && fila <= FILAS && columna >= 1 && columna <= COLUMNAS);             
    }
    
    //Creamos los métodos Getters para acceder a los asientos, filas y columnas.
    public Estado[][] getAsientos(){
        return asientos;}
    
    public int getFilas(){
        return FILAS;}
    
    public int getColumnas(){
        return COLUMNAS;}    
}
