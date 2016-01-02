package practica1;

import java.util.Scanner;

public class Jugador extends Personas{
    
    static Scanner in = new Scanner (System.in);
    private int jsalario;
    private String jposicion;
    private Boolean jtitular;
    private int jnum;
        
    public Jugador(Personas persona, 
            int salario, String posicion, Boolean titular, int num){
        super(persona);
        this.jsalario = salario;
        this.jposicion = posicion;
        this.jtitular = titular;
        this.jnum = num;       
    }
    
    //public int JugadorSalario(){
    //    int salario;
    //    
    //    System.out.println("Introduce el salario del jugador");
    //    salario = in.nextInt();
    //    return salario;
    //}
    
    public int GetJugadorSalario(){
        return jsalario;
    }
    
    //public String JugadorPosicion(){
    //    String posicion;
    //    
    //    System.out.println("Introduce la posicion del jugador");
    //    posicion = in.next();
    //    return posicion;
    //}
    
    public String GetJugadorPosicion(){
        return jposicion;
    }
    
    //public Boolean JugadorTitular(){
    //    Boolean titular,bucle;
    //    String aux;
    //    bucle = false;
    //    titular = false;
    //    
    //    do{
    //        System.out.println("Indica si el jugador es titular S/N");
    //        aux = in.next();
    //        if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
    //            titular = true;
    //            bucle = true;
    //        }
    //        else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
    //            titular = false;
    //            bucle = true;
    //        }
    //        else{
    //            System.out.println("Error");
    //        }
    //    }while(bucle == false);
    //    return titular;
    //}
    
    public Boolean GetJugadorTitular(){
        return jtitular;
    }
    
    //public int JugadorNumero(){
    //    int numero;
    //    
    //    System.out.println("Introduce el numero del jugador");
    //    numero = in.nextInt();
    //    return numero;
    //}
    
    public int GetJugadorNumero(){
        return jnum;
    }
}
