package app.domain;

//Acá realizamos la clase que va a manejar a los objetos Película

public class Pelicula {
    
    //Los atributos encapsulados porque tienen que ser inmutables y sólo leídos por método get
    private final int id;
    private final String titulo;
    private final Integer anio;
    private final String genero;
    private final String duracion; //Lo trabajamos como string directamente
    
    //Utilizaremos dos constructores mediante la sobrecarga de constructores, por si tuviéramos que importar desde un JSON (o donde sea) películas en un futuro y le faltasen datos
    
    public Pelicula(int id, String titulo, Integer anio, String genero, String duracion){
        
        //Primero validamos que el título de la película esté, no vale de nada traer una película sin título, y utilizamos la expresión try-catch para devolver una excepción 
        //"throw new" sirve para eso, para devolver una excepción si tenemos datos inválidos (nulo o en blanco)
        if(titulo == null || titulo.trim().isEmpty()){ // Acá usamos trim() para eliminar espacios delante y atrás, y usamos isEmpty() para verificar que no esté vacío, ya que si sólo contiene espacios los elimina y lo deja vacío.
            throw new IllegalArgumentException("====>>>EL TÍTULO DE LA PELÍCULA ES OBLIGATORIO<<<====");
        }
        //A través de las variables de referencia asignamos valores de los parámetros del constructor a las variables de instancia de la clase
        this.id = id;
        this.titulo = titulo;
        this.anio = (anio != null) ? anio : 0; //si el año NO sea null, traemos año, y si es null asignamos 0 como valor predeterminado
        this.genero = (genero != null && !genero.trim().isEmpty()) ? genero : "Genero no especificado"; //si el genero NO es null Y NO está en blanco, mostrar género, sino valor predeterminado
        this.duracion = (duracion != null && !duracion.trim().isEmpty()) ? duracion : "Duracion no especificada";//si la duración NO es null Y NO está en blanco, mostramos duración, sino valor predeterminado
        
    } 
    
    // Constructor simplificado cuando traemos sólo título, no es necesario pero es por si se utiliza mucho el traer películas sólo con el nombre, es una estructura más simplificada y aplicamos "sobrecarga de constructores"
    
    public Pelicula (int id, String titulo){
        this(id, titulo, null, null, null); //Si existiera null en el resto, se crea sin problemas, si bien manejamos esa excepción arriba, es más legible si la declaramos así y aplicamos conocimiento adquirido en clase
    }
    
    //Ahora realizamos los getters para traer lo que necesitemos, deben ser public ya que tienen que poder ser llamados desde todos lados
    public int getId()        { return id; }
    public String getTitulo() {return titulo;}
    public Integer getAnio() {return anio;}
    public String getGenero() {return genero;}
    public String getDuracion() {return duracion;}
    
    //Ahora damos formato final con @Override para devolver el nombre final de la película COMPLETAMENTE FORMATEADO
    
    @Override
    public String toString(){
        return id + ". " + titulo + ", " + " (" + (anio == 0 ? "Anio no especificado" : anio) + "), " + " Genero: " + genero + ", " + " Duracion: " + duracion + ".";
    }
    
}
