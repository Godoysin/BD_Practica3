package practica1;

import java.sql.SQLException;
import java.util.Scanner;

public class AppFutbolMenu {
    
    static Scanner in = new Scanner (System.in);
    static Boolean bucle = false;
    static int eleccion;
    static AppFutbol f = new AppFutbol();
    static ConexionBD conn = null;
    
    public static void main(String[] args) throws SQLException{
    	
    	EjecutarBD();
        
        do{
            
            System.out.println("Programa de base de datos");
            System.out.println("----------------------");
            System.out.println("1  .- Alta Equipo");
            System.out.println("2  .- Baja Equipo");
            System.out.println("3  .- Alta Jugador");
            System.out.println("4  .- Baja Jugador");
            System.out.println("5  .- Alta Albitro");
            System.out.println("6  .- Baja Albitro");
            System.out.println("7  .- Alta Estadio");
            System.out.println("8  .- Alta Partido");
            System.out.println("9  .- Baja Partido");
            System.out.println("10 .- Listar Equipos");
            System.out.println("11 .- Listar Estadios");
            System.out.println("12 .- Listar Arbitros");
            System.out.println("13 .- Devolver Total Partidos");
            System.out.println("14 .- Listar Información de Partidos dada una fecha");
            System.out.println("15 .- Listar los partidos hechos por un equipo");
            System.out.println("16 .- Listar los jugadores que hay de una posición");
            System.out.println("17 .- Listar los jugadores de un equipo y sus posiciones");
            System.out.println("18 .- Cargar Sistema");
            System.out.println("19 .- Salvar los datos");
            System.out.println("20 .- Calcular el campeón");
            System.out.println("21 .- Calcular posiciones");
            System.out.println("22 .- Cargar de MySQL");
            System.out.println("23 .- Guardar a MySQL");
            System.out.println("24 .- Salir");
            Espacio();
            System.out.println("Seleccione una opción");
            eleccion = in.nextInt();
            
            switch(eleccion){
                
                case 1:
                    Espacio();
                            
                    System.out.println("Ha seleccionado: Alta Equipo");
                    f.AltaEquipo();
                    
                    Espacio();
                    break;
                    
                case 2:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Baja Equipo");
                    f.BajaEquipo();
                    
                    Espacio();
                    break;
                
                case 3:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Alta Jugador");
                    f.AltaJugador();
                    
                    Espacio();
                    break;
                    
                case 4:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Baja Jugador");
                    f.BajaJugador();
                    
                    Espacio();
                    break;
                    
                case 5:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Alta Albitro");
                    f.AltaArbitro();
                    
                    Espacio();
                    break;
                    
                case 6:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Baja Albitro");
                    f.BajaArbitro();
                    
                    Espacio();
                    break;
                    
                case 7:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Alta estadio");
                    f.AltaEstadio();
                    
                    Espacio();
                    break;
                    
                case 8:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Alta Partido");
                    f.AltaPartido();
                    
                    Espacio();
                    break;
                    
                case 9:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Baja Partido");
                    f.BajaPartido();
                    
                    Espacio();
                    break;
                    
                case 10:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar Equipos");
                    f.ListarEquipos();
                    
                    Espacio();
                    break;
                    
                case 11:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar Estadios");
                    f.ListarEstadios();
                    
                    Espacio();
                    break;
                    
                case 12:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar Arbitros");
                    f.ListarArbitros();
                    
                    Espacio();
                    break;
                    
                case 13:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Devolver Total Partidos");
                    f.ContarPartidos();
                    
                    Espacio();
                    break;
                    
                case 14:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar Información de Partidos dada una fecha");
                    f.ListarPartidos();
                    
                    Espacio();
                    break;
                    
                case 15:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar los Partidos hechos por un equipo");
                    f.ListarPartidosEquipo();
                    
                    Espacio();
                    break;
                    
                case 16:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar los jugadores que hay en una posición");
                    f.ListarJugadores();
                    
                    Espacio();
                    break;
                    
                case 17:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Listar los jugadores de un equipo y sus posiciones");
                    f.ListarJugadoresEquipo();
                    
                    Espacio();
                    break;
                    
                case 18:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Cargar sistema");
                    f.CargarDatos();
                    
                    Espacio();
                    break;
                
                case 19:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Salvar los datos");
//                    f.Salvar();
                    
                    Espacio();
                    break;
                    
                case 20:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Calcular el campeón");
                    f.CalcularCampeonTemporada();
                    
                    Espacio();
                    break;
                    
                case 21:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Calcular posiciones");
                    f.CalcularPosicionesEquipos();
                    
                    Espacio();
                    break;
                    
                case 22:
                	Espacio();
                    
                    System.out.println("Ha seleccionado: Cargar de MySQL");
				try {
					f.CargarMySQL();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    
                    Espacio();
                    break;
                    
                case 23:
                	Espacio();
                    
                    System.out.println("Ha seleccionado: Guardar a MySQL");
                    f.GuardarMySQL();
                    
                    Espacio();
                    break;
                    
                case 24:
                    Espacio();
                    
                    System.out.println("Ha seleccionado: Salir");
                    conn.cerrarconexion();
                    bucle=true;
                    
                    Espacio();
                    break;
                    
                default:
                    Espacio();
                    
                    System.out.println("Opción no válida");
                    
                    Espacio();
                    break;
            
            }
            
        }while(bucle == false);
        
    }
    
    public static void Espacio(){
        System.out.println("");
    }
    
    public static void EjecutarBD(){
    	conn = ConexionBD.getInstancia();
    }
    
    public static ConexionBD Conexion(){
    	return conn;
    }
}


