package practica1;

import java.util.ArrayList;
import java.util.Scanner;

public class Equipo{
    
    static Scanner in = new Scanner (System.in);
    private int eidequipo;
    private Estadio eestadio;
    private int epuntos; //posición que está en la liga
    public ArrayList<Jugador> ejugador = new ArrayList<Jugador>();
    
    public Equipo(int idequipo, int puntos){
        this.eidequipo = idequipo;
        this.epuntos = puntos;
    }
    
    //public Equipo SetEquipo(int idequipo, Estadio estadio, int posicion, ArrayList<Jugador> jugador){
        //Equipo equipo = new Equipo();
        //equipo.Equipo(idequipo, estadio, posicion, jugador);
        //return equipo;
    //}
    
    public void AltaJugador(Jugador jugador){
        ejugador.add(jugador);
    }
    
    public void BajaJugador(int id){
        //Declaraciones
        int i;
        
        //Bucle para encontrar al jugador
        for(i = 0; i < ejugador.size(); i++){
            if(ejugador.get(i).GetPersonaId() == id){
                System.out.println("Ya hay un jugador con el id: " + id);
                ejugador.remove(i);
            }
        }
    }
    
    public Jugador GetEquipoJugador(int id){
        //Declaraciones
        Jugador jugador = null;
        int i;
        
        //Bucle para encontrar al jugador
        for(i = 0; i < ejugador.size(); i++){
            if(ejugador.get(i).GetPersonaId() == id){
                jugador = ejugador.get(i);
            }
        }
        return jugador;
    }
    
    //public int EquipoId(){
    //    int id;
    //    
    //    System.out.println("Introduce la id del equipo");
    //    id = in.nextInt();
    //    return id;
    //}
    
    public int GetEquipoId(){
        return eidequipo;
    }
    
    //public int EquipoPuntos(){
    //    int puntos;
    //    
    //    System.out.println("Introduce los puntos del equipo");
    //    puntos = in.nextInt();
    //    return puntos;
    //}
    
    public int GetEquipoPuntos(){
        return epuntos;
    }
    
    public void AltaEstadio(Estadio estadio){
        this.eestadio = estadio;
    }
    
    public Estadio GetEquipoEstadio(){
        return eestadio;
    }
            
}