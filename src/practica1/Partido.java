package practica1;

import java.util.ArrayList;

public class Partido{
    
    private int pidpartido;
    private Estadio pestadio;
    private Fecha pfecha; //o String x
    private Equipo pequipo1;
    private Equipo pequipo2;
    private Boolean pida;
    private ArrayList<Arbitro> parbitro;
    private ArrayList<Jugador> pjugador1;
    private ArrayList<Jugador> pjugador2;
    private int pgolesEq1;
    private int pgolesEq2;
    
    public Partido(int idpartido, Estadio estadio, Fecha fecha, Equipo equipo1, Equipo equipo2, Boolean ida, 
            ArrayList<Arbitro> arbitro, ArrayList<Jugador> jugador1, ArrayList<Jugador> jugador2, int golesEq1, int golesEq2){
        this.pidpartido = idpartido;
        this.pestadio = estadio;
        this.pfecha = fecha;
        this.pequipo1 = equipo1;
        this.pequipo2 = equipo2;
        this.pida = ida;
        this.parbitro = arbitro;
        this.pjugador1 = jugador1;
        this.pjugador2 = jugador2;
        this.pgolesEq1 = golesEq1;
        this.pgolesEq2 = golesEq2;
    }
    
    public void AltaArbitro(Arbitro arbitro){
        parbitro.add(arbitro);
    }
    
    public int GetPartidoId(){
        return pidpartido;
    }
    
    public Estadio GetParidoEstadio(){
        return pestadio;
    }
    
    public Fecha GetPartidoFecha(){
        return pfecha;
    }
    
    public Equipo GetPartidoEquipo1(){
        return pequipo1;
    }
    
    public Equipo GetPartidoEquipo2(){
        return pequipo2;
    }
    
    public Boolean GetPartidoIda(){
        return pida;
    }
    
    public ArrayList<Arbitro> GetPartidoArbitro(){
    	return parbitro;
    }
    
    public ArrayList<Jugador> GetPartidoJugadorEq1(){
    	return pjugador1;
    }
    
    public ArrayList<Jugador> GetPartidoJugadorEq2(){
    	return pjugador2;
    }
    
    public Arbitro GetPartidoArbitro_Id(int id){
        //Declaraciones
        Arbitro arbitro = null;
        int i;
        
        //Bucle para encontrar al jugador
        for(i = 0; i < parbitro.size(); i++){
            if(parbitro.get(i).GetPersonaId() == id){
                arbitro = parbitro.get(i);
            }
        }
        return arbitro;
    }
    
    public Jugador GetPartidoJugadorEq1_Id(int id){
        //Declaraciones
        Jugador jugador = null;
        int i;
        
        //Bucle para encontrar al jugador
        for(i = 0; i < pjugador1.size(); i++){
            if(pjugador1.get(i).GetPersonaId() == id){
                jugador = pjugador1.get(i);
            }
        }
        return jugador;
    }
    
    public Jugador GetPartidoJugadorEq2_Id(int id){
        //Declaraciones
        Jugador jugador = null;
        int i;
        
        //Bucle para encontrar al jugador
        for(i = 0; i < pjugador2.size(); i++){
            if(pjugador2.get(i).GetPersonaId() == id){
                jugador = pjugador2.get(i);
            }
        }
        return jugador;
    }
    
    public int GetPartidoGolesEq1(){
        return pgolesEq1;
    }
    
    public int GetPartidoGolesEq2(){
        return pgolesEq2;
    }
}