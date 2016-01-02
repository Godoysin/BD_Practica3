package practica1;

import java.util.Scanner;

public class Estadio{
    
    static Scanner in = new Scanner (System.in);
    private int eidestadio;
    private String edireccion;
    private String eciudad;
    private int ecapacidad;

    public Estadio(int idestadio, String direccion, String ciudad, int capacidad){
        this.eidestadio = idestadio;
        this.edireccion = direccion;
        this.eciudad = ciudad;
        this.ecapacidad = capacidad;
        
    }
    public Estadio(Estadio estadio){
        this.eidestadio = estadio.GetEstadioId();
        this.edireccion = estadio.GetEstadioDireccion();
        this.eciudad = estadio.GetEstadioCiudad();
        this.ecapacidad = estadio.GetEstadioCapacidad();
        
    }
    
    //public int EstadioId(){
    //    int id;
    //    
    //    System.out.println("Introduce la id del estadio");
    //    id = in.nextInt();
    //    return id;
    //}
    
    public int GetEstadioId(){
        return eidestadio;
    }
    
    //public String EstadioDireccion(){
    //    String direccion;
    //    
    //    System.out.println("Introduce la dirección del estadio");
    //    direccion = in.next();
    //    return direccion;
    //}
    
    public String GetEstadioDireccion(){
        return edireccion;
    }
    
    //public String EstadioCiudad(){
    //  	String ciudad;
    //    
    //    System.out.println("Introduce la ciudad del estadio");
    //    ciudad = in.next();
    //    return ciudad;
    //}
    
    public String GetEstadioCiudad(){
        return eciudad;
    }
    
    //public int EstadioCapacidad(){
    //    int capacidad;
    //    
    //    System.out.println("Introduce la capacidad del estadio");
    //    capacidad = in.nextInt();
    //    return capacidad;
    //}
    
    public int GetEstadioCapacidad(){
        return ecapacidad;
    }
}