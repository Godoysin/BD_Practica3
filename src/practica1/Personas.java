package practica1;

import java.util.Scanner;

public class Personas{
    
    static Scanner in = new Scanner (System.in);
    private int pid;
    private String pnombre;
    private String pemail;
    private String ptlf;
    
    public Personas(int id, String nombre, String email, String tlf){
        this.pid = id;
        this.pnombre = nombre;
        this.pemail = email;
        this.ptlf = tlf;
    }
    
    public Personas(Personas persona){
        this.pid = persona.GetPersonaId();
        this.pnombre = persona.GetPersonaNombre();
        this.pemail = persona.GetPersonaEmail();
        this.ptlf = persona.GetPersonaTlf();
    }
    
    //public int PersonaId(){
    //    int id;
    //    
    //    System.out.println("Introduce la id de la persona");
    //    id = in.nextInt();
    //    return id;
    //}
    
    public int GetPersonaId(){
        return pid;
    }
    
    //public String PersonaNombre(){
    //    String nombre;
    //    
    //    System.out.println("Introduce el nombre de la persona");
    //    nombre = in.next();
    //    return nombre;
    //}
    
    public String GetPersonaNombre(){
        return pnombre;
    }
    
    //public String PersonaEmail(){
    //    String email;
    //    
    //    System.out.println("Introduce el email de la persona");
    //    email = in.next();
    //    return email;
    //}
    
    public void SetEmail(String email){
        this.pemail = email;
    }
    
    public String GetPersonaEmail(){
        return pemail;   
    }
    
    //public String PersonaTlf(){
    //    String tlf;
    //    
    //    System.out.println("Introduce el tlf de la persona");
    //    tlf = in.next();
    //    return tlf;
    //}
    
    public String GetPersonaTlf(){
        return ptlf;  
    }
    
    public void SetTlf(String tlf){
        this.ptlf = tlf;
    }
}
