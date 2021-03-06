package practica1;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppFutbol{
	
	static Scanner in = new Scanner (System.in);
	HashMap<Integer, Equipo> mEquipo = new HashMap<Integer, Equipo>(); //el integer ser� el idequipo
	HashMap<Integer, Jugador> mJugador = new HashMap<Integer, Jugador>(); //Integer ser� idjugador
	HashMap<Integer, Arbitro> mArbitro = new HashMap<Integer, Arbitro>(); // ..igual
	HashMap<Integer, Estadio> mEstadio = new HashMap<Integer, Estadio>(); //..igual
	ArrayList<Partido> mPartido = new ArrayList<Partido>();
	
	public AppFutbol(){//Aqu� se pueden cargar los datos o en un nuevo m�todo
		
	}
	public void AltaEquipo() throws SQLException{
		//Declaraciones
		ResultSet results;
		String sql;
		int id, puntos;
		Boolean bucle;
		//Pido la id y busco que no est� repetida
		do{
			id = EquipoId();
			results = ConsultaEquipo();
			bucle = false;
			while(results.next()){
				if(results.getInt("id") == id){
					System.out.println("Ya hay un equipo con esa id");
					bucle = true;
				}
			}
		}while(bucle);
		//Como no est� repetido creo el nuevo Equipo
		puntos = EquipoPuntos();
		sql = "INSERT INTO EQUIPO (id, estadio, puntos) VALUES ("
				+ id + ", " + null + ", " + puntos + ");";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void BajaEquipo() throws SQLException{
		//Declaraciones
		ResultSet results;
		results = ConsultaEquipo();
		int id;
		String sql, aux;
		Boolean bucle, mostrar, error;
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Doy la opci�n de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("�Desea que se le muestren los equipos en el sistema? S/N");
					aux = in.next();
					if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
						mostrar = true;
						bucle = false;
					}
					else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
						mostrar = false;
						bucle = false;
					}
					else{
						System.out.println("Error");
					}
				}while(bucle);
			}
			catch(Exception e){
				System.out.println("Error");
				error = true;
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Si quiere verlos
				if(mostrar){
					ListarEquipos();
				}
				//Borro el equipo por id
				bucle = true;
				do{
					id = EquipoId();
					results = ConsultaEquipo();
					while(results.next()){
						if(results.getInt("id") == id){
							bucle = false;
						}
					}
				}while(bucle);
				results = ConsultaJugador();
				while(results.next()){
					if(results.getInt("equipo") == id){
						//Borrar equipo de jugador
						sql = "UPDATE JUGADOR SET equipo=null WHERE JUGADOR.equipo =" + id + ";";
						AppFutbolMenu.Conexion().ejecutar(sql);
					}
				}
				results = ConsultaPartido();
				while(results.next()){
					if(results.getInt("equipo1") == id){
						//Borrar equipo1 de partido
						sql = "UPDATE PARTIDO SET equipo1=null WHERE PARTIDO.equipo1 =" + id + ";";
						AppFutbolMenu.Conexion().ejecutar(sql);
					}
				}results = ConsultaPartido();
				while(results.next()){
					if(results.getInt("equipo2") == id){
						//Borrar equipo2 de partido
						sql = "UPDATE PARTIDO SET equipo2=null WHERE PARTIDO.equipo1 =" + id + ";";
						AppFutbolMenu.Conexion().ejecutar(sql);
					}
				}
				results = ConsultaEquipo();
				while(results.next()){
					if(results.getInt("id") == id){
						//Borrar equipo
						RemoveEquipo(id);
					}
				}
			}
		}
	}
	public void AltaJugador() throws SQLException{ //Se da de alta en un equipo y si no est� en el sistema tambi�n
		//Declaraciones
		ResultSet results;
		String nombre, email, tlf, posicion, aux;
		int id, id2, salario, num;
		Boolean bucle, titular, error, mostrar;
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Pido la id y busco que no est� repetida
			bucle = mostrar = true;
			error = false;
			id2 = -1;
			do{
				id = PersonaId();
				bucle = false;
				results = ConsultaPersonas();
				while(results.next()){
					if(results.getInt("DNI") == id){
						System.out.println("Ya hay una persona con esa id");
						bucle = true;
					}
				}
			}while(bucle);
			//Como no est� repetido creo el nuevo Jugador
			nombre = PersonaNombre();
			email = PersonaEmail();
			tlf = PersonaTlf();
			salario = JugadorSalario();
			posicion = JugadorPosicion();
			titular = JugadorTitular();
			num = JugadorNumero();
			if(id == -1 || nombre.compareTo("-1") == 0 || email.compareTo("-1") == 0 
					|| tlf.compareTo("-1") == 0 || salario == -1 || posicion.compareTo("-1") == 0
					|| num == -1){
				error = true;
			}
			else{
				//A�ado al jugador primero a la tabla de personas y despu�s a la de jugador
				AddPersonas(id, nombre, email, tlf);
				AddJugador(id, salario, posicion, titular, num, null);
				//Doy la opcion de mostrar los equipos en los que meter al jugador
				bucle = true;
				try{
					do{
						System.out.println("�Desea que se le muestren los equipos en el sistema? S/N");
						aux = in.next();
						if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
							mostrar = true;
							bucle = false;
						}
						else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
							mostrar = false;
							bucle = false;
						}
						else{
							System.out.println("Error");
						}
					}while(bucle);
				}
				catch(Exception e){
					System.out.println("Error");
					error = true;
				}
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Muestro los equipos
				if(mostrar){
					ListarEquipos();
				}
				//Pido que meta al jugador en un equipo
				bucle = true;
				do{
					System.out.println("Introduzca al jugador en un equipo");
					id2 = EquipoId();
					if(id2 == -1){
						bucle = false;
					}
					results = ConsultaEquipo();
					while(results.next()){
						if(results.getInt("id") == id2){
							AddJugador(id, salario, posicion, titular, num, String.valueOf(id2));
							bucle = false;
						}
					}
				}while(bucle);
			}
			if(id2 == -1){
				System.out.println("Ha habido un error");
			}
		}
	}
	public void BajaJugador() throws SQLException{ // de un equipo, no del sistema
		//Declaraciones
		ResultSet results;
		results = ConsultaJugador();
		int dni;
		Boolean bucle, mostrar, error;
		String aux;
		if(!results.next()){
			System.out.println("No hay jugadores en la base de datos");
		}
		else{
			//Doy la opci�n de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("�Desea que se le muestren los jugadores en el sistema? S/N");
					aux = in.next();
					if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
						mostrar = true;
						bucle = false;
					}
					else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
						mostrar = false;
						bucle = false;
					}
					else{
						System.out.println("Error");
					}
				}while(bucle);
			}
			catch(Exception e){
				System.out.println("Error");
				error = true;
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Si quiere verlos
				if(mostrar){
					results = ConsultaJugador();
					System.out.println("Hay los siguientes jugadores:");
					while(results.next()){			
						System.out.println("El jugador con dni: " +results.getInt("dni"));
					}
				}
				//Elijo al jugador por dni
				bucle = true;
				do{
					dni = PersonaId();
					results = ConsultaJugador();
					while(results.next()){
						if(results.getInt("DNI") == dni){
							bucle = false;
						}
					}
				}while(bucle);
				//Borro al jugador de jugadores_partido
				results = ConsultaJugadores_partido();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemoveJugadores_partido(dni);
					}
				}
				//Borro al jugador
				results = ConsultaJugador();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemoveJugador(dni);
					}
				}
				//Borro al jugador de persona
				results = ConsultaPersonas();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemovePersonas(dni);
					}
				}
			}
		}
	}
	public void AltaArbitro() throws SQLException{
		//Declaraciones
		ResultSet results;
		int id;
		String nombre, email, tlf, tipo;
		Boolean bucle;
		//Pido la id y busco que no est� repetida
		do{
			id = PersonaId();
			bucle = false;
			results = ConsultaPersonas();
			while(results.next()){
				if(results.getInt("DNI") == id){
					System.out.println("Ya hay una persona con esa id");
					bucle = true;
				}
			}
		}while(bucle);
		//Como no est� repetido creo el nuevo Arbitro
		nombre = PersonaNombre();
		email = PersonaEmail();
		tlf = PersonaTlf();
		tipo = ArbitroTipo();
		//A�ade al arbitro primero a personas y despu�s a albitro
		AddPersonas(id, nombre, email, tlf);
		AddArbitro(id, tipo);
	}
	public void BajaArbitro() throws SQLException{
		//Declaraciones
		ResultSet results;
		results = ConsultaArbitro();
		Boolean bucle, mostrar, error;
		String aux;
		int dni;
		if(!results.next()){
			System.out.println("No hay arbitros en la base de datos");
		}
		else{
			//Doy la opci�n de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("�Desea que se le muestren los arbitros en el sistema? S/N");
					aux = in.next();
					if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
						mostrar = true;
						bucle = false;
					}
					else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
						mostrar = false;
						bucle = false;
					}
					else{
						System.out.println("Error");
					}
				}while(bucle);
			}
			catch(Exception e){
				System.out.println("Error");
				error = true;
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Si quiere verlos
				if(mostrar){
					ListarArbitros();
				}
				//Elijo el arbitro por dni
				bucle = true;
				do{
					dni = PersonaId();
					results = ConsultaArbitro();
					while(results.next()){
						if(results.getInt("DNI") == dni){
							bucle = false;
						}
					}
				}while(bucle);
				//Borro el arbitro de arbitros_partido
				results = ConsultaArbitros_partido();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemoveArbitros_partido(dni);
					}
				}
				//Borro el arbitro
				results = ConsultaArbitro();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemoveArbitro(dni);
					}
				}
				//Borro el arbitro de persona
				results = ConsultaPersonas();
				while(results.next()){
					if(results.getInt("DNI") == dni){
						RemovePersonas(dni);
					}
				}
			}
		}
	}
	public void AltaEstadio() throws SQLException{ //del sistema
		//Declaraciones
		ResultSet results;
		int id, capacidad, id2;
		String ciudad, direccion, aux;
		Boolean bucle, error, mostrar;
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Pido la id y busco que la id no est� repetida
			error = false;
			mostrar = true;
			id2 = 0;
			do{
				id = EstadioId();
				bucle = false;
				results = ConsultaEstadio();
				while(results.next()){
					if(results.getInt("id") == id){
						System.out.println("Ya hay un estadio con esa id");
						bucle = true;
					}
				}
			}while(bucle);
			//Como no est� repetido creo el nuevo Equipo
			ciudad = EstadioCiudad();
			direccion = EstadioDireccion();
			capacidad = EstadioCapacidad();
			//Muestro los equipos
			if(id == -1 || ciudad.compareTo("-1") == 0 || direccion.compareTo("-1") == 0 
					|| capacidad == -1){
				error = true;
			}
			else{
				//Doy la opcion de mostrar los equipos en los que meter al estadio
				bucle = true;
				try{
					do{
						System.out.println("�Desea que se le muestren los equipos en el sistema? S/N");
						aux = in.next();
						if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
							mostrar = true;
							bucle = false;
						}
						else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
							mostrar = false;
							bucle = false;
						}
						else{
							System.out.println("Error");
						}
					}while(bucle);
				}
				catch(Exception e){
					System.out.println("Error");
					error = true;
				}
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Muestro los equipos
				if(mostrar){
					ListarEquipos();
				}
				//Meto al estadio en la base de datos y en un equipo
				AddEstadio(id, direccion, ciudad, capacidad);
				//Pido que meta al jugador en un equipo
				bucle = true;
				do{
					System.out.println("Introduzca al estadio en un equipo");
					id2 = EquipoId();
					if(id2 == -1){
						bucle = false;
					}
					//Lo a�ado a un equipo
					results = ConsultaEquipo();
					while(results.next()){
						if(results.getInt("id") == id2){
							AddEquipo(results.getInt("id"), id, results.getInt("puntos"));
							bucle = false;
						}
					}
				}while(bucle);
			}
			if(id2 == -1){
				System.out.println("Ha habido un error");
			}
		}
	}
	public void AltaPartido() throws SQLException, java.text.ParseException{
		//Declaraciones
		ResultSet results;
		Boolean bucle, ida, aniadir, repetido;
		String aux;
		int ocurrencias, id, idequipo, idestadio, golesEq1, golesEq2, idarbitro, anio, mes, dia, equipo1, equipo2;
		ocurrencias = id = golesEq1 = golesEq2 = equipo1 = equipo2 = idestadio = 0;
		aniadir = ida = true;
		//Tengo equipos
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Tengo estadios
			results = ConsultaEstadio();
			if(!results.next()){
				System.out.println("No hay estadios en la base de datos");
			}
			else{
				//Tengo arbitros
				results = ConsultaArbitro();
				if(!results.next()){
					System.out.println("No hay arbitros en la base de datos");
				}
				else{
					ocurrencias = 0;
					String sql = "SELECT DISTINCT equipo FROM JUGADOR;";
					results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
					while(results.next()){
						ocurrencias++;
					}
					//Tengo dos o m�s equipos con jugadores
					if(ocurrencias >= 2){
						//Compruebo que no se repita la id del partido
						do{
							bucle = false;
							id = PartidoId();
							results = ConsultaPartido();
							while(results.next()){
								if(results.getInt("id") == id){
									System.out.println("Ya hay un partido con esa id");
									bucle = true;
								}
							}
						}while(bucle);
						//A�ado los equipos
						sql = "SELECT DISTINCT equipo FROM JUGADOR;";
						results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
						while(results.next()){
							System.out.println("El equipo con id: " + results.getInt("equipo")
									+ " tiene jugadores");
						}
						do{
							bucle = true;
							System.out.println("Introduzca el primer equipo que juega el partido");
							idequipo = EquipoId();
							sql = "SELECT DISTINCT equipo FROM JUGADOR;";
							results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
							while(results.next()){
								if(results.getInt("equipo") == idequipo){
									equipo1 = idequipo;
									bucle = false;
								}
							}
						}while(bucle);
						do{
							bucle = true;
							System.out.println("Introduzca el segundo equipo que juega el partido");
							idequipo = EquipoId();
							results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
							while(results.next()){
								if(results.getInt("equipo") == idequipo && equipo1 != idequipo){
									equipo2 = idequipo;
									bucle = false;
								}
							}
							
						}while(bucle);
						ListarEstadios();
						do{
							bucle = true;
							System.out.println("Introduzca el estadio donde se juega el partido");
							idestadio = EstadioId();
							results = ConsultaEstadio();
							while(results.next()){
								if(results.getInt("id") == idestadio){
									bucle = false;
								}
							}
						}while(bucle);
						System.out.println("Equipo 1");
						golesEq1 = PartidoGoles();
						System.out.println("Equipo 2");
						golesEq2 = PartidoGoles();
						ida = PartidoIda();
					}
					//Introduzco la fecha
					System.out.println("Introduzca la fecha del partido");
					anio = FechaAnio();
					mes = FechaMes();
					dia = FechaDia();
					Fecha fecha = new Fecha(anio, mes, dia);
					AddPartido(id, idestadio, fecha.GetFechaasString(), equipo1, equipo2, ida, golesEq1, golesEq2);
					results = ConsultaJugador();
					while(results.next()){
						if(results.getInt("equipo") == equipo1){
							AddJugadores_partido(results.getInt("DNI"), id);
						}
						if(results.getInt("equipo") == equipo2){
							AddJugadores_partido(results.getInt("DNI"), id);
						}
					}
					//A�ado un �rbitro
					ListarArbitros();
					do{
						bucle = true;
						System.out.println("Introduzca los �rbitros del partido");
						idarbitro = PersonaId();
						results = ConsultaArbitro();
						while(results.next()){
							if(results.getInt("dni") == idarbitro){
								AddArbitros_partido(idarbitro, id);
								bucle = false;
							}
						}
					}while(bucle);
					//Doy la opci�n de a�adir m�s
					do{
						bucle = true;
						do{
							System.out.println("�Quieres a�adir otro �rbitro? S/N");
							aux = in.next();
							if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
								aniadir = true;
								bucle = false;
							}
							else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
								aniadir = false;
								bucle = false;
							}
							else{
								System.out.println("Error");
							}
						}while(bucle);
						if(aniadir){
							do{
								bucle = true;
								idarbitro = PersonaId();
								results = ConsultaArbitro();
								while(results.next()){
									if(results.getInt("DNI") == idarbitro){
										repetido = false;
										ResultSet results2 = ConsultaArbitros_partido();
										while(results2.next()){
											if(results2.getInt("DNI") == results.getInt("DNI")){
												System.out.println("Ese �rbitro ya est� en el partido");
												repetido = true;
											}
										}
										if(!repetido){
											AddArbitros_partido(idarbitro, id);
											bucle = false;
										}
									}
								}
							}while(bucle);
						}
					}while(aniadir);
				}
			}
		}
	}
	public void BajaPartido() throws SQLException{
		//Declaraciones
		ResultSet results;
		results = ConsultaPartido();
		Boolean bucle, mostrar, error;
		String aux;
		int id;
		if(!results.next()){
			System.out.println("No hay partidos en la base de datos");
		}
		else{
			//Doy la opci�n de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("�Desea que se le muestren los partidos en el sistema? S/N");
					aux = in.next();
					if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
						mostrar = true;
						bucle = false;
					}
					else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
						mostrar = false;
						bucle = false;
					}
					else{
						System.out.println("Error");
					}
				}while(bucle);
			}
			catch(Exception e){
				System.out.println("Error");
				error = true;
			}
			if(error){
				System.out.println("Ha habido un error");
			}
			else{
				//Si quiere verlos
				if(mostrar){
					results = ConsultaPartido();
					while(results.next()){
						System.out.println("El partido con id: " + results.getInt("id") + " que se jug� el: " + results.getDate("fecha"));
					}
				}
				//Borro el equipo por id
				bucle = true;
				do{
					id = PartidoId();
					results = ConsultaPartido();
					while(results.next()){
						if(results.getInt("id") == id){
							bucle = false;
						}
					}
				}while(bucle);
				//Borro el partido de arbitros_partido
				results = ConsultaArbitros_partido();
				while(results.next()){
					if(results.getInt("partido") == id){
						RemoveArbitros_partido2(id);
					}
				}
				//Borro el partido de jugadores_partido
				results = ConsultaJugadores_partido();
				while(results.next()){
					if(results.getInt("partido") == id){
						RemoveJugadores_partido2(id);
					}
				}
				//Borro el partido
				results = ConsultaPartido();
				while(results.next()){
					if(results.getInt("id") == id){
						RemovePartido(id);
					}
				}
			}
		}
	}
	public void ListarEquipos() throws SQLException{
		ResultSet results;
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			results = ConsultaEquipo();
			System.out.println("Los equipos son:");
			while(results.next()){
				System.out.print("El equipo con id: " + results.getInt("id"));
				System.out.println(" y puntuaci�n de: " + results.getInt("puntos"));
			}
		}
	}
	public void ListarEstadios() throws SQLException{
		ResultSet results;
		results = ConsultaEstadio();
		if(!results.next()){
			System.out.println("No hay estadios en la base de datos");
		}
		else{
			System.out.println("Los estadios son:");
			results = ConsultaEstadio();
			while(results.next()){
				System.out.print("El estadio con id: " + results.getInt("id"));
				System.out.print(" que est� en la ciudad de: " + results.getString("ciudad"));
				System.out.print(" en la calle: " + results.getString("direcci�n"));
				System.out.println(" y con capacidad para: " + results.getInt("capacidad"));
			}
		}
	}
	public void ListarArbitros() throws SQLException{
		ResultSet results;
		String sql = "";
		results = ConsultaArbitro();
		if(!results.next()){
			System.out.println("No hay arbitro en la base de datos");
		}
		else{
			sql = "SELECT * FROM PERSONAS, ARBITRO WHERE PERSONAS.DNI=ARBITRO.DNI;";
			results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
			System.out.println("Los arbitros son:");
			while(results.next()){
				System.out.print("El arbitro con id: " + results.getInt("DNI"));
				System.out.print(", de nombre: " + results.getString("nombre"));
				System.out.print(", con email: " + results.getString("email"));
				System.out.print(", con tlf: " + results.getString("tlf"));
				System.out.println(" es: " + results.getString("tipo"));
			}
		}
	}
	public void ContarPartidos() throws SQLException{
		//Declaraciones
		ResultSet results;
		results = ConsultaPartido();
		int i = 0;
		if(!results.next()){
			System.out.println("No hay partidos en la base de datos");
		}
		else{
			results = ConsultaPartido();
			while(results.next()){
				i++;
			}
			System.out.println("Hay " + i + " partidos");
		}
	}
	public void ListarPartidos() throws SQLException{ //devuelve info del partido dada una fecha
		//Declaraciones
		ResultSet results;
		results = ConsultaPartido();
		int anio, mes, dia;
		if(!results.next()){
			System.out.println("No hay partidos en la base de datos");
		}
		else{
			results = ConsultaPartido();
			System.out.println("Selecciona el d�a del que mirar los partidos");
			anio = FechaAnio();
			mes = FechaMes();
			mes++;
			dia = FechaDia();
			while(results.next()){
				Date fecha = results.getDate("fecha");
				if(fecha.toString().substring(0, 4).compareTo(String.valueOf(anio)) == 0
						&& fecha.toString().substring(5, 7).compareTo(String.valueOf(mes)) == 0
						&& fecha.toString().substring(8, 10).compareTo(String.valueOf(dia)) == 0){
					System.out.print("El partido con id: " + results.getInt("id"));
					System.out.print(", en el que juegan los equipos con id: " + results.getInt("equipo1"));
					System.out.print(" y " + results.getInt("equipo2"));
					System.out.print(" se jug� el ");
					System.out.println(fecha);
				}
			}
		}
	}
	public void ListarPartidosEquipo() throws SQLException{//Devuelve la info del partido dado un equipo
		//Declaraciones
		ResultSet results;
		results = ConsultaPartido();
		String sql = "";
		int id;
		Boolean bucle;
		if(!results.next()){
			System.out.println("No hay partidos en la base de datos");
		}
		else{
			ListarEquipos();
			do{
				bucle = true;
				id = EquipoId();
				results = ConsultaEquipo();
				while(results.next()){
					if(results.getInt("id") == id){
						bucle = false;
					}
				}
			}while(bucle);
			sql = "SELECT * FROM PARTIDO WHERE equipo1=" + id + " OR equipo2=" + id + ";";
			results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
			while(results.next()){
				System.out.print("El equipo con id: " + id);
				System.out.print(", juega el partido con id: " + results.getInt("id"));
				if(results.getInt("equipo1") == id){
					System.out.println(", marc� " + results.getInt("goleseq1") + " goles");
				}
				else{
					System.out.println(", marc� " + results.getInt("goleseq2") + " goles");
				}
			}
		}
	}
	public void ListarJugadores() throws SQLException{//dada una posici�n en el campo
		//Declaraciones
		String posicion, sql = "";
		ResultSet results;
		results = ConsultaJugador();
		if(!results.next()){
			System.out.println("No hay jugadores en la base de datos");
		}
		else{
			System.out.println("Seleccione la posici�n");
			posicion = JugadorPosicion();
			if(posicion.compareTo("-1") == 0){
				System.out.println("Ha habido un error");
			}
			else{
				sql = "SELECT * FROM PERSONAS, JUGADOR WHERE PERSONAS.DNI=JUGADOR.DNI;";
				results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
				System.out.println("Los jugadores en la posici�n de " + posicion + " son:");
				while(results.next()){
					if(results.getString("posici�n").compareTo(posicion) == 0){
						System.out.print("El jugador con id: " + results.getInt("DNI"));
						System.out.print(", de nombre: " + results.getString("nombre"));
						System.out.print(", con email: " + results.getString("email"));
						System.out.print(", con tlf: " + results.getString("tlf"));
						System.out.print(", gana: " + results.getInt("salario"));
						System.out.print(", tiene el n�mero: " + results.getInt("numero"));
						if(results.getBoolean("titular")){
							System.out.println(" y es titular");
						}
						else{
							System.out.println(" y no es titular");
						}
					}
				}
			}
		}
	}
	public void ListarJugadoresEquipo() throws SQLException{//dado un equipo
		//Declaraciones
		ResultSet results;
		results = ConsultaEquipo();
		String sql;
		int id;
		Boolean novacio = true, bucle = true;
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Compruebo que los equipos tengan jugadores
			results = ConsultaJugador();
			while(!results.next()){
				novacio = false;
			}
			if(novacio){
				ListarEquipos();
				do{
					System.out.println("Seleccione el equipo");
					id = EquipoId();
					if(id == -1){
						System.out.println("Ha habido un error");
					}
					results = ConsultaEquipo();
					while(results.next()){
						if(results.getInt("id") == id){
							bucle = false;
						}
					}
				}while(bucle);
				System.out.println("Los jugadores en el equipo de id: " + id + " son:");
				sql = "SELECT * FROM PERSONAS, JUGADOR WHERE equipo=" + id + " AND PERSONAS.DNI=JUGADOR.DNI;";
				results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
				while(results.next()){
					System.out.print("El jugador con id: " + results.getInt("DNI"));
					System.out.print(", de nombre: " + results.getString("nombre"));
					System.out.print(", con email: " + results.getString("email"));
					System.out.print(", con tlf: " + results.getString("tlf"));
					System.out.print(", gana: " + results.getInt("salario"));
					System.out.print(", tiene el n�mero: " + results.getInt("numero"));
					System.out.print(", juega de: " + results.getString("posici�n"));
					if(results.getBoolean("titular")){
						System.out.println(" y es titular");
					}
					else{
						System.out.println(" y no es titular");
					}
				}
			}
		}
	}
	public void Salvar(){
		//Declaraciones
		int i, j;
		Iterator<Integer> it;
		Object key;
		try{
			//Salvo los equipos
			BufferedWriter bwEq = new BufferedWriter(new FileWriter("Equipo.txt"));
			if(mEquipo.isEmpty() == false){
				it = mEquipo.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					bwEq.write(String.valueOf(mEquipo.get(key).GetEquipoId()));
					bwEq.newLine();
					if(mEquipo.get(key).GetEquipoEstadio() == null){
						bwEq.write("0");
						bwEq.newLine();
					}
					else{
						bwEq.write("1");
						bwEq.newLine();
						bwEq.write(String.valueOf(mEquipo.get(key).GetEquipoEstadio().GetEstadioId()));
						bwEq.newLine();
						bwEq.write(mEquipo.get(key).GetEquipoEstadio().GetEstadioDireccion());
						bwEq.newLine();
						bwEq.write(mEquipo.get(key).GetEquipoEstadio().GetEstadioCiudad());
						bwEq.newLine();
						bwEq.write(String.valueOf(mEquipo.get(key).GetEquipoEstadio().GetEstadioCapacidad()));
						bwEq.newLine();
					}
					bwEq.write(String.valueOf(mEquipo.get(key).GetEquipoPuntos()));
					bwEq.newLine();
					if(mEquipo.get(key).ejugador.isEmpty()){
						bwEq.write("0");
						bwEq.newLine();
					}
					else{
						bwEq.write(String.valueOf(mEquipo.get(key).ejugador.size()));
						bwEq.newLine();
						for(i = 0; i < mEquipo.get(key).ejugador.size(); i++){
							bwEq.write(String.valueOf(mEquipo.get(key).ejugador.get(i).GetPersonaId()));
							bwEq.newLine();
							bwEq.write(mEquipo.get(key).ejugador.get(i).GetPersonaNombre());
							bwEq.newLine();
							bwEq.write(mEquipo.get(key).ejugador.get(i).GetPersonaEmail());
							bwEq.newLine();
							bwEq.write(mEquipo.get(key).ejugador.get(i).GetPersonaTlf());
							bwEq.newLine();
							bwEq.write(String.valueOf(mEquipo.get(key).ejugador.get(i).GetJugadorSalario()));
							bwEq.newLine();
							bwEq.write(mEquipo.get(key).ejugador.get(i).GetJugadorPosicion());
							bwEq.newLine();
							bwEq.write(String.valueOf(mEquipo.get(key).ejugador.get(i).GetJugadorTitular()));
							bwEq.newLine();
							bwEq.write(String.valueOf(mEquipo.get(key).ejugador.get(i).GetJugadorNumero()));
							bwEq.newLine();
						}
					}
				}
			}
			bwEq.close();
			//Salvo los jugadores
			BufferedWriter bwJu = new BufferedWriter(new FileWriter("Jugadores.txt"));
			if(mJugador.isEmpty() == false){
				it = mJugador.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					bwJu.write(String.valueOf(mJugador.get(key).GetPersonaId()));
					bwJu.newLine();
					bwJu.write(mJugador.get(key).GetPersonaNombre());
					bwJu.newLine();
					bwJu.write(mJugador.get(key).GetPersonaEmail());
					bwJu.newLine();
					bwJu.write(mJugador.get(key).GetPersonaTlf());
					bwJu.newLine();
					bwJu.write(String.valueOf(mJugador.get(key).GetJugadorSalario()));
					bwJu.newLine();
					bwJu.write(mJugador.get(key).GetJugadorPosicion());
					bwJu.newLine();
					bwJu.write(String.valueOf(mJugador.get(key).GetJugadorTitular()));
					bwJu.newLine();
					bwJu.write(String.valueOf(mJugador.get(key).GetJugadorNumero()));
					bwJu.newLine();
				}
			}
			bwJu.close();
			//Salvo los estadios
			BufferedWriter bwEst = new BufferedWriter(new FileWriter("Estadio.txt"));
			if(mEstadio.isEmpty() == false){
				it = mEstadio.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					bwEst.write(String.valueOf(mEstadio.get(key).GetEstadioId()));
					bwEst.newLine();
					bwEst.write(mEstadio.get(key).GetEstadioDireccion());
					bwEst.newLine();
					bwEst.write(mEstadio.get(key).GetEstadioCiudad());
					bwEst.newLine();
					bwEst.write(String.valueOf(mEstadio.get(key).GetEstadioCapacidad()));
					bwEst.newLine();
					
				}
			}
			bwEst.close();
			//Salvo los arbitros
			BufferedWriter bwArb = new BufferedWriter(new FileWriter("Arbitro.txt"));
			if(mArbitro.isEmpty() == false){
				it = mArbitro.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					bwArb.write(mArbitro.get(key).GetArbitroTipo());
					bwArb.newLine();
					bwArb.write(String.valueOf(mArbitro.get(key).GetPersonaId()));
					bwArb.newLine();
					bwArb.write(mArbitro.get(key).GetPersonaNombre());
					bwArb.newLine();
					bwArb.write(mArbitro.get(key).GetPersonaEmail());
					bwArb.newLine();
					bwArb.write(mArbitro.get(key).GetPersonaTlf());
					bwArb.newLine();
				}
			}
			bwArb.close();
			//Guardo los partidos
			BufferedWriter bwPar = new BufferedWriter(new FileWriter("Partidos.txt"));
			if(mPartido.isEmpty() == false){
				for(i = 0; i < mPartido.size(); i++){
					//PartidoId
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoId()));
					bwPar.newLine();
					//Estadio
					bwPar.write(String.valueOf(mPartido.get(i).GetParidoEstadio().GetEstadioId()));
					bwPar.newLine();
					bwPar.write(mPartido.get(i).GetParidoEstadio().GetEstadioDireccion());
					bwPar.newLine();
					bwPar.write(mPartido.get(i).GetParidoEstadio().GetEstadioCiudad());
					bwPar.newLine();
					bwPar.write(String.valueOf(mPartido.get(i).GetParidoEstadio().GetEstadioCapacidad()));
					bwPar.newLine();
					//Fecha
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoFecha().GetFechaAnio()));
					bwPar.newLine();
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoFecha().GetFechaMes()));
					bwPar.newLine();
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoFecha().GetFechaDia()));
					bwPar.newLine();
					//Equipo1
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().GetEquipoId()));
					bwPar.newLine();
					if(mPartido.get(i).GetPartidoEquipo1().GetEquipoEstadio() == null){
						bwPar.write("0");
						bwPar.newLine();
					}
					else{
						bwPar.write("1");
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().GetEquipoEstadio().GetEstadioId()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().GetEquipoEstadio().GetEstadioDireccion());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().GetEquipoEstadio().GetEstadioCiudad());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().GetEquipoEstadio().GetEstadioCapacidad()));
						bwPar.newLine();
					}
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().GetEquipoPuntos()));
					bwPar.newLine();
					if(mPartido.get(i).GetPartidoEquipo1().ejugador.isEmpty()){
						bwPar.write("0");
						bwPar.newLine();
					}
					else{
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.size()));
						bwPar.newLine();
						for(j = 0; j < mPartido.get(i).GetPartidoEquipo1().ejugador.size(); j++){
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaId()));
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaNombre());
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaEmail());
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaTlf());
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorSalario()));
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorPosicion());
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorTitular()));
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorNumero()));
							bwPar.newLine();
						}
					}
					//Equipo2
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().GetEquipoId()));
					bwPar.newLine();
					if(mPartido.get(i).GetPartidoEquipo2().GetEquipoEstadio() == null){
						bwPar.write("0");
						bwPar.newLine();
					}
					else{
						bwPar.write("1");
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().GetEquipoEstadio().GetEstadioId()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().GetEquipoEstadio().GetEstadioDireccion());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().GetEquipoEstadio().GetEstadioCiudad());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().GetEquipoEstadio().GetEstadioCapacidad()));
						bwPar.newLine();
					}
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().GetEquipoPuntos()));
					bwPar.newLine();
					if(mPartido.get(i).GetPartidoEquipo2().ejugador.isEmpty()){
						bwPar.write("0");
						bwPar.newLine();
					}
					else{
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.size()));
						bwPar.newLine();
						for(j = 0; j < mPartido.get(i).GetPartidoEquipo2().ejugador.size(); j++){
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaId()));
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaNombre());
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaEmail());
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaTlf());
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorSalario()));
							bwPar.newLine();
							bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorPosicion());
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorTitular()));
							bwPar.newLine();
							bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorNumero()));
							bwPar.newLine();
						}
					}
					//Ida
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoIda()));
					bwPar.newLine();
					//Arbitros
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoArbitro().size()));
					bwPar.newLine();
					for(j = 0; j < mPartido.get(i).GetPartidoArbitro().size(); j++){
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoArbitro().get(j).GetPersonaId()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoArbitro().get(j).GetPersonaNombre());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoArbitro().get(j).GetPersonaEmail());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoArbitro().get(j).GetPersonaTlf());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoArbitro().get(j).GetArbitroTipo());
						bwPar.newLine();
					}
					//JugadoresEq1
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.size()));
					bwPar.newLine();
					for(j = 0; j < mPartido.get(i).GetPartidoEquipo1().ejugador.size(); j++){
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaId()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaNombre());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaEmail());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetPersonaTlf());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorSalario()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorPosicion());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorTitular()));
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo1().ejugador.get(j).GetJugadorNumero()));
						bwPar.newLine();
					}
					//JugadoresEq2
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.size()));
					bwPar.newLine();
					for(j = 0; j < mPartido.get(i).GetPartidoEquipo2().ejugador.size(); j++){
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaId()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaNombre());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaEmail());
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetPersonaTlf());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorSalario()));
						bwPar.newLine();
						bwPar.write(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorPosicion());
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorTitular()));
						bwPar.newLine();
						bwPar.write(String.valueOf(mPartido.get(i).GetPartidoEquipo2().ejugador.get(j).GetJugadorNumero()));
						bwPar.newLine();
					}
					//GolesEq1
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoGolesEq1()));
					bwPar.newLine();
					//GolesEq2
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoGolesEq2()));
					bwPar.newLine();
				}
			}
			bwPar.close();
		}
		catch(IOException e){
			System.out.println("Error de E/S");
		}
	}
	public void CargarDatos(){
		//Declaraciones
		int i, id, idestadio, capacidad, idequipo, puntos, salario, numero, idpartido, aux, anio, mes, dia, golesEq1, golesEq2;
		String direccion, ciudad, nombre, email, tlf, posicion, tipo;
		Boolean titular, ida;
		Partido partido;
		Fecha fecha;
		Equipo equipo, equipo1, equipo2;
		Estadio estadio, estadio1, estadio2;
		Arbitro arbitro;
		Jugador jugador, jugador1, jugador2;
		Personas persona, persona1, persona2;
		ArrayList<Arbitro> aarbitro = new ArrayList<Arbitro>();
		ArrayList<Jugador> ajugador1 = new ArrayList<Jugador>();
		ArrayList<Jugador> ajugador2 = new ArrayList<Jugador>();
		estadio = null;
		String linea = null;
		try{
			//Cargo los equipos
			BufferedReader brEq = new BufferedReader(new FileReader("Equipo.txt"));
			if(mEquipo.isEmpty()){
				while((linea = brEq.readLine()) != null){
					estadio = null;
					idequipo = Integer.parseInt(linea);
					aux = Integer.parseInt(brEq.readLine());
					if(aux == 0){
						estadio = null;
					}
					else{
						idestadio = Integer.parseInt(brEq.readLine());
						direccion = brEq.readLine();
						ciudad = brEq.readLine();
						capacidad = Integer.parseInt(brEq.readLine());
						estadio = new Estadio(idestadio, direccion, ciudad, capacidad);
					}
					puntos = Integer.parseInt(brEq.readLine());
					equipo = new Equipo(idequipo, puntos);
					if(estadio != null){
						equipo.AltaEstadio(estadio);
					}
					aux = Integer.parseInt(brEq.readLine());
					if(aux == 0){
						jugador = null;
					}
					else{
						for(i = 0; i < aux; i++){
							id = Integer.parseInt(brEq.readLine());
							nombre = brEq.readLine();
							email = brEq.readLine();
							tlf = brEq.readLine();
							salario = Integer.parseInt(brEq.readLine());
							posicion = brEq.readLine();
							titular = Boolean.parseBoolean(brEq.readLine());
							numero = Integer.parseInt(brEq.readLine());
							persona = new Personas(id, nombre, email, tlf);
							jugador = new Jugador(persona, salario, posicion, titular, numero);
							equipo.AltaJugador(jugador);
						}
					}
					mEquipo.put(idequipo, equipo);
				}
			}
			brEq.close();
			//Cargo los jugadores
			BufferedReader brJu = new BufferedReader(new FileReader("Jugadores.txt"));
			if(mEstadio.isEmpty()){
				while((linea = brJu.readLine()) != null){
					id = Integer.parseInt(linea);
					nombre = brJu.readLine();
					email = brJu.readLine();
					tlf = brJu.readLine();
					salario = Integer.parseInt(brJu.readLine());
					posicion = brJu.readLine();
					titular = Boolean.parseBoolean(brJu.readLine());
					numero = Integer.parseInt(brJu.readLine());
					persona = new Personas(id, nombre, email, tlf);
					jugador = new Jugador(persona, salario, posicion, titular, numero);
					mJugador.put(id, jugador);
				}
			}
			brJu.close();
			//Cargo los estadios
			BufferedReader brEst = new BufferedReader(new FileReader("Estadio.txt"));
			if(mEstadio.isEmpty()){
				while((linea = brEst.readLine()) != null){
					idestadio = Integer.parseInt(linea);
					direccion = brEst.readLine();
					ciudad = brEst.readLine();
					capacidad = Integer.parseInt(brEst.readLine());
					estadio = new Estadio(idestadio, direccion, ciudad, capacidad);
					mEstadio.put(idestadio, estadio);
				}
			}
			brEst.close();
			//Cargo los �rbitros
			BufferedReader brArb = new BufferedReader(new FileReader("Arbitro.txt"));
			if(mArbitro.isEmpty()){
				while((linea = brArb.readLine()) != null){
					tipo = linea;
					id = Integer.parseInt(brArb.readLine());
					nombre = brArb.readLine();
					email = brArb.readLine();
					tlf = brArb.readLine();
					persona = new Personas(id, nombre, email, tlf);
					arbitro = new Arbitro(persona, tipo);
					mArbitro.put(id, arbitro);
				}
			}
			brArb.close();
			//Cargo los Partidos
			BufferedReader brPar = new BufferedReader(new FileReader("Partidos.txt"));
			if(mPartido.isEmpty()){
				while((linea = brPar.readLine()) != null){
					idpartido = Integer.parseInt(linea);
					//Estadio
					idestadio = Integer.parseInt(brPar.readLine());
					direccion = brPar.readLine();
					ciudad = brPar.readLine();
					capacidad = Integer.parseInt(brPar.readLine());
					estadio = new Estadio(idestadio, direccion, ciudad, capacidad);
					//Fecha
					anio = Integer.parseInt(brPar.readLine());
					mes = Integer.parseInt(brPar.readLine());
					dia = Integer.parseInt(brPar.readLine());
					fecha = new Fecha(anio, mes, dia);
					//Equipo1
					idequipo = Integer.parseInt(brPar.readLine());
					aux = Integer.parseInt(brPar.readLine());
					if(aux == 0){
						estadio1 = null;
					}
					else{
						idestadio = Integer.parseInt(brPar.readLine());
						direccion = brPar.readLine();
						ciudad = brPar.readLine();
						capacidad = Integer.parseInt(brPar.readLine());
						estadio1 = new Estadio(idestadio, direccion, ciudad, capacidad);
					}
					puntos = Integer.parseInt(brPar.readLine());
					equipo1 = new Equipo(idequipo, puntos);
					equipo1.AltaEstadio(estadio1);
					aux = Integer.parseInt(brPar.readLine());
					for(i = 0; i < aux; i++){
						id = Integer.parseInt(brPar.readLine());
						nombre = brPar.readLine();
						email = brPar.readLine();
						tlf = brPar.readLine();
						salario = Integer.parseInt(brPar.readLine());
						posicion = brPar.readLine();
						titular = Boolean.parseBoolean(brPar.readLine());
						numero = Integer.parseInt(brPar.readLine());
						persona1 = new Personas(id, nombre, email, tlf);
						jugador1 = new Jugador(persona1, salario, posicion, titular, numero);
						ajugador1.add(jugador1);
						equipo1.AltaJugador(jugador1);
					}
					//Equipo2
					idequipo = Integer.parseInt(brPar.readLine());
					aux = Integer.parseInt(brPar.readLine());
					if(aux == 0){
						estadio2 = null;
					}
					else{
						idestadio = Integer.parseInt(brPar.readLine());
						direccion = brPar.readLine();
						ciudad = brPar.readLine();
						capacidad = Integer.parseInt(brPar.readLine());
						estadio2 = new Estadio(idestadio, direccion, ciudad, capacidad);
					}
					puntos = Integer.parseInt(brPar.readLine());
					equipo2 = new Equipo(idequipo, puntos);
					equipo2.AltaEstadio(estadio2);
					aux = Integer.parseInt(brPar.readLine());
					for(i = 0; i < aux; i++){
						id = Integer.parseInt(brPar.readLine());
						nombre = brPar.readLine();
						email = brPar.readLine();
						tlf = brPar.readLine();
						salario = Integer.parseInt(brPar.readLine());
						posicion = brPar.readLine();
						titular = Boolean.parseBoolean(brPar.readLine());
						numero = Integer.parseInt(brPar.readLine());
						persona2 = new Personas(id, nombre, email, tlf);
						jugador2 = new Jugador(persona2, salario, posicion, titular, numero);
						ajugador2.add(jugador2);
						equipo2.AltaJugador(jugador2);
					}
					//Ida
					ida = Boolean.parseBoolean(brPar.readLine());
					//Arbitros
					aux = Integer.parseInt(brPar.readLine());
					for(i = 0; i < aux; i++){
						id = Integer.parseInt(brPar.readLine());
						nombre = brPar.readLine();
						email = brPar.readLine();
						tlf = brPar.readLine();
						tipo = brPar.readLine();
						persona1 = new Personas(id, nombre, email, tlf);
						arbitro = new Arbitro(persona1, tipo);
						aarbitro.add(arbitro);
					}
					//Jugador1
					aux = Integer.parseInt(brPar.readLine());
					for(i = 0; i < aux; i++){
						id = Integer.parseInt(brPar.readLine());
						nombre = brPar.readLine();
						email = brPar.readLine();
						tlf = brPar.readLine();
						salario = Integer.parseInt(brPar.readLine());
						posicion = brPar.readLine();
						titular = Boolean.parseBoolean(brPar.readLine());
						numero = Integer.parseInt(brPar.readLine());
						persona1 = new Personas(id, nombre, email, tlf);
						jugador1 = new Jugador(persona1, salario, posicion, titular, numero);
						ajugador1.add(jugador1);
					}
					//Jugador2
					aux = Integer.parseInt(brPar.readLine());
					for(i = 0; i < aux; i++){
						id = Integer.parseInt(brPar.readLine());
						nombre = brPar.readLine();
						email = brPar.readLine();
						tlf = brPar.readLine();
						salario = Integer.parseInt(brPar.readLine());
						posicion = brPar.readLine();
						titular = Boolean.parseBoolean(brPar.readLine());
						numero = Integer.parseInt(brPar.readLine());
						persona2 = new Personas(id, nombre, email, tlf);
						jugador2 = new Jugador(persona2, salario, posicion, titular, numero);
						ajugador1.add(jugador2);
					}
					golesEq1 = Integer.parseInt(brPar.readLine());
					golesEq2 = Integer.parseInt(brPar.readLine());
					//public Partido(int idpartido, Estadio estadio, Fecha fecha, Equipo equipo1, Equipo equipo2, Boolean ida, 
		            //ArrayList<Arbitro> arbitro, ArrayList<Jugador> jugador1, ArrayList<Jugador> jugador2, int golesEq1, int golesEq2){
					partido = new Partido(idpartido, estadio, fecha, equipo1, equipo2, ida, aarbitro, ajugador1, ajugador2, golesEq1, golesEq2);
					mPartido.add(partido);
				}
			}
			brPar.close();
		}
		catch(IOException e){
			System.out.println("Error de E/S");
		}
	}
	public void CalcularCampeonTemporada() throws SQLException{
		ResultSet results, resultshelp;
		int i, j, aux, aux2;
		results = ConsultaEquipo();
		i = 0;
		while(results.next()){
			i++;
		}
		int puntos[] = new int[i]; 
		int equipos[] = new int[i];
		//Para calcular el campeon hay cojer todos los equipos, ver los puntos que tiene cada uno y a�adir
		//en funci�n de si han ganado o empatado partidos.
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Cojer cada equipo y sus puntos
			i = 0;
			results = ConsultaEquipo();
			while(results.next()){
				equipos[i] = results.getInt("id");
				puntos[i] = results.getInt("puntos");
				i++;
			}
			results = ConsultaPartido();
			if(!results.next()){
				System.out.println("El campe�n se ha calculado con los puntos indicados al crear el equipo");
				System.out.println("No hay partidos en la base de datos");
			}
			else{
				//Calcular la ganancia de puntos de cada equipo
				results = ConsultaPartido();
				while(results.next()){
					if(results.getInt("goleseq1") > results.getInt("goleseq2")){
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo1") == resultshelp.getInt("id")){
								 puntos[i] += 3;
							 }
							 i++;
						 }
					}
					if(results.getInt("goleseq1") < results.getInt("goleseq2")){
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo2") == resultshelp.getInt("id")){
								 puntos[i] += 3;
							 }
							 i++;
						 }
					}
					if(results.getInt("goleseq1") == results.getInt("goleseq2")){
						resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo1") == resultshelp.getInt("id")){
								 puntos[i] += 1;
							 }
							 i++;
						 }
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo2") == resultshelp.getInt("id")){
								 puntos[i] += 1;
							 }
							 i++;
						 }
					}
				}
			}
			//Calcular el campe�n
			for(i = 0; i < puntos.length - 1; i++){
				for(j = 0; j < puntos.length - i - 1; j++){
					if(puntos[j+1] > puntos[j]){
						aux = equipos[j+1];
						aux2 = puntos[j+1];
						equipos[j+1] = equipos[j];
						puntos[j+1] = puntos[j];
						equipos[j] = aux;
						puntos[j] = aux2;
					}
				}
			}
			//Pinto los resultados
			j = 1;
			for(i = 0; i < equipos.length; i++){
				if(j == 1){
					System.out.println("El equipo con id " + equipos[i] + " queda como campe�n");
				}
				if((i + 1) != equipos.length){
					if(puntos[i] > puntos[i+1]){
						j++;
					}
				}
				i++;
			};
		}
	}
	public void CalcularPosicionesEquipos() throws SQLException{
		//Declaraciones
		int i, j, aux, aux2;
		ResultSet results, resultshelp;
		results = ConsultaEquipo();
		i = 0;
		while(results.next()){
			i++;
		}
		int puntos[] = new int[i]; 
		int equipos[] = new int[i];
		//Para calcular el campeon hay cojer todos los equipos, ver los puntos que tiene cada uno y a�adir
		//en funci�n de si han ganado o empatado partidos.
		results = ConsultaEquipo();
		if(!results.next()){
			System.out.println("No hay equipos en la base de datos");
		}
		else{
			//Cojer cada equipo y sus puntos
			i = 0;
			results = ConsultaEquipo();
			while(results.next()){
				equipos[i] = results.getInt("id");
				puntos[i] = results.getInt("puntos");
				i++;
			}
			results = ConsultaPartido();
			if(!results.next()){
				System.out.println("El campe�n se ha calculado con los puntos indicados al crear el equipo");
				System.out.println("No hay partidos en la base de datos");
			}
			else{
				//Calcular la ganancia de puntos de cada equipo
				results = ConsultaPartido();
				while(results.next()){
					if(results.getInt("goleseq1") > results.getInt("goleseq2")){
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo1") == resultshelp.getInt("id")){
								 puntos[i] += 3;
							 }
							 i++;
						 }
					}
					if(results.getInt("goleseq1") < results.getInt("goleseq2")){
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo2") == resultshelp.getInt("id")){
								 puntos[i] += 3;
							 }
							 i++;
						 }
					}
					if(results.getInt("goleseq1") == results.getInt("goleseq2")){
						resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo1") == resultshelp.getInt("id")){
								 puntos[i] += 1;
							 }
							 i++;
						 }
						 resultshelp = ConsultaEquipo();
						 i = 0;
						 while(resultshelp.next()){
							 if(results.getInt("equipo2") == resultshelp.getInt("id")){
								 puntos[i] += 1;
							 }
							 i++;
						 }
					}
				}	
			}		
		}
		//Calcular el campe�n
		for(i = 0; i < puntos.length - 1; i++){
			for(j = 0; j < puntos.length - i - 1; j++){
				if(puntos[j+1] > puntos[j]){
					aux = equipos[j+1];
					aux2 = puntos[j+1];
					equipos[j+1] = equipos[j];
					puntos[j+1] = puntos[j];
					equipos[j] = aux;
					puntos[j] = aux2;
				}
			}
		}
		//Pinto los resultados
		j = 1;
		for(i = 0; i < puntos.length; i++){
			System.out.println("El equipo con id " + equipos[i] + " queda en la posici�n: " + j + "�");
			if((i + 1) != equipos.length){
				if(puntos[i] > puntos[i+1]){
					j++;
				}
			}
		}
	}
	public void CargarMySQL() throws SQLException{
		String sql = "SELECT * FROM PERSONAS";
		ResultSet results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
		while(results.next()){
			System.out.println(results.getString("dni"));
		}
		sql = "SELECT * FROM JUGADOR";
		results = AppFutbolMenu.Conexion().ejecutarConsulta(sql);
		while(results.next()){
			System.out.println(results.getString("salario"));
		}
	}
	public void GuardarMySQL(){
		String sql = "";
		String nombre, email, tlf, posicion, direccion, ciudad, tipo, fecha;
		int dni, salario, numero, equipo, tit, id, capacidad, estadio, puntos, i, goles, j;
		boolean titular, ida;
		Iterator<Integer> it = null, ith = null;
		Integer key = null;
		//Almaceno los estadios en ESTADIO
		if(mEstadio.isEmpty() == false){
			it = mEstadio.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				id = Integer.valueOf(mEstadio.get(key).GetEstadioId());
				direccion = String.valueOf(mEstadio.get(key).GetEstadioDireccion());
				ciudad = String.valueOf(mEstadio.get(key).GetEstadioCiudad());
				capacidad = Integer.valueOf(mEstadio.get(key).GetEstadioCapacidad());
				sql = "INSERT INTO ESTADIO (id, direcci�n, ciudad, capacidad) VALUES (" 
						+ id + ", '" + direccion + "', '" + ciudad + "', " + capacidad + ");";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
		//Almaceno los equipos en EQUIPO
		if(mEquipo.isEmpty() == false){
			it = mEquipo.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				id = Integer.valueOf(mEquipo.get(key).GetEquipoId());
				puntos = Integer.valueOf(mEquipo.get(key).GetEquipoPuntos());
				if(mEquipo.get(key).GetEquipoEstadio() == null){
					sql = "INSERT INTO EQUIPO (id, estadio, puntos) VALUES ("
							+ id + ", " + null + ", " + puntos + ");";
				}
				else{
					estadio = Integer.valueOf(mEquipo.get(key).GetEquipoEstadio().GetEstadioId());
					sql = "INSERT INTO EQUIPO (id, estadio, puntos) VALUES ("
							+ id + ", " + estadio + ", " + puntos + ");";
				}
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
		//Almaceno los jugadores en JUGADOR y PERSONAS
		if(mJugador.isEmpty() == false){
			it = mJugador.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				dni = Integer.valueOf(mJugador.get(key).GetPersonaId());
				nombre = String.valueOf(mJugador.get(key).GetPersonaNombre());
				email = String.valueOf(mJugador.get(key).GetPersonaEmail());
				tlf = String.valueOf(mJugador.get(key).GetPersonaTlf());
				salario = Integer.valueOf(mJugador.get(key).GetJugadorSalario());
				posicion = String.valueOf(mJugador.get(key).GetJugadorPosicion());
				titular = Boolean.valueOf(mJugador.get(key).GetJugadorTitular());
				if(titular){
					tit = 1;
				}
				else{
					tit = 0;
				}
				numero = Integer.valueOf(mJugador.get(key).GetJugadorNumero());
				ith = mEquipo.keySet().iterator();
				equipo = -1;
				while(ith.hasNext()){
					key=ith.next();
					if(mEquipo.get(key).GetEquipoJugador(dni) != null){
						equipo = mEquipo.get(key).GetEquipoId();
					}
				}
				sql = "INSERT INTO PERSONAS (DNI, nombre, email, tlf) VALUES (" 
						+ dni + ", '" + nombre + "', '" + email + "', '" + tlf + "');";
				AppFutbolMenu.Conexion().ejecutar(sql);
				sql = "INSERT INTO JUGADOR (DNI, salario, posici�n, titular, numero, equipo) "
						+ "VALUES (" + dni + ", " + salario + ", '" + posicion + "', " + 
						tit + ", " + numero + ", " + equipo + ");";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
		//Almaceno los arbitros en ARBITRO y PERSONAS
		if(mArbitro.isEmpty() == false){
			it = mArbitro.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				dni = Integer.valueOf(mArbitro.get(key).GetPersonaId());
				nombre = String.valueOf(mArbitro.get(key).GetPersonaNombre());
				email = String.valueOf(mArbitro.get(key).GetPersonaEmail());
				tlf = String.valueOf(mArbitro.get(key).GetPersonaTlf());
				tipo = String.valueOf(mArbitro.get(key).GetArbitroTipo());
				sql = "INSERT INTO PERSONAS (DNI, nombre, email, tlf) VALUES (" 
						+ dni + ", '" + nombre + "', '" + email + "', '" + tlf + "');";
				AppFutbolMenu.Conexion().ejecutar(sql);
				sql = "INSERT INTO ARBITRO (DNI, tipo) VALUES (" + dni + ", '"+ tipo + "');";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
		//Almaceno los partidos en PARTIDO
		if(mPartido.isEmpty() == false){
			for(i = 0; i < mPartido.size(); i++){
				id = mPartido.get(i).GetPartidoId();
				sql = "INSERT INTO PARTIDO (id, estadio, fecha, equipo1, equipo2, ida, goleseq1, goleseq2) VALUES (" + id;
				if(mPartido.get(i).GetParidoEstadio() == null){
					sql = sql + ", " + null;
				}
				else{
					estadio = mPartido.get(i).GetParidoEstadio().GetEstadioId();
					sql = sql + ", " + estadio;
				}
				fecha = mPartido.get(i).GetPartidoFecha().GetFechaasString();
				sql = sql + ", '" + fecha;
				if(mPartido.get(i).GetPartidoEquipo1() == null){
					sql = sql + "', " + null;
				}
				else{
					equipo = mPartido.get(i).GetPartidoEquipo1().GetEquipoId();
					sql = sql + "', " + equipo;
				}
				if(mPartido.get(i).GetPartidoEquipo2() == null){
					sql = sql + ", " + null;
				}
				else{
					equipo = mPartido.get(i).GetPartidoEquipo2().GetEquipoId();
					sql = sql + ", " + equipo;
				}
				ida = mPartido.get(i).GetPartidoIda();
				if(ida){
					sql = sql + ", " + 1;
				}
				else{
					sql = sql + ", " + 0;
				}
				goles = mPartido.get(i).GetPartidoGolesEq1();
				sql = sql + ", " + goles;
				goles = mPartido.get(i).GetPartidoGolesEq2();
				sql = sql + ", " + goles + ");";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
		//Almaceno los jugadores que juegan un partido en JUGADORES_PARTIDO
		if(mPartido.isEmpty() == false){
			for(i = 0; i < mPartido.size(); i++){
				id = mPartido.get(i).GetPartidoId();
				for(j = 0; j < mPartido.get(i).GetPartidoEquipo1().GetEquipoJugadores().size(); j++){
					dni = mPartido.get(i).GetPartidoEquipo1().GetEquipoJugadores().get(j).GetPersonaId();
					sql = "INSERT INTO JUGADORES_PARTIDO (DNI, partido) VALUES (" + dni + ", " + id + ");";
					AppFutbolMenu.Conexion().ejecutar(sql);
				}
				for(j = 0; j < mPartido.get(i).GetPartidoEquipo2().GetEquipoJugadores().size(); j++){
					dni = mPartido.get(i).GetPartidoEquipo2().GetEquipoJugadores().get(j).GetPersonaId();
					sql = "INSERT INTO JUGADORES_PARTIDO (DNI, partido) VALUES (" + dni + ", " + id + ");";
					AppFutbolMenu.Conexion().ejecutar(sql);
				}
			}
		}
		//Almaceno los arbitros que arbitran un partido en ARBITROS_PARTIDO
		if(mPartido.isEmpty() == false){
			for(i = 0; i < mPartido.size(); i++){
				id = mPartido.get(i).GetPartidoId();
				for(j = 0; j < mPartido.get(i).GetPartidoArbitro().size(); j++){
					dni = mPartido.get(i).GetPartidoArbitro().get(j).GetPersonaId();
					sql = "INSERT INTO ARBITROS_PARTIDO (DNI, partido) VALUES (" + dni + ", " + id + ");";
					AppFutbolMenu.Conexion().ejecutar(sql);
				}
			}
		}
	}
	
	//M�todos
	public int EquipoId(){
		int id = 1;
		//boolean error = false;
		try{
			System.out.println("Introduce la id del equipo");
			id = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			id = -1;
		//	error = true;
		}
		//if(error){
		//	id = EquipoId();
		//}
		return id;
	}
	public int EquipoPuntos(){
        int puntos;
        try{
        	System.out.println("Introduce los puntos del equipo");
        	puntos = in.nextInt();
        }
        catch(Exception e){
        	System.out.println("Error");
			puntos = -1;
        }
        return puntos;
    }
	public int EstadioId(){
		int id;
		try{
			System.out.println("Introduce la id del estadio:");
			id = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			id = EstadioId();
		}
		return id;
	}
	public String EstadioDireccion(){
		String direccion;
		try{
			System.out.println("Introduce la direccion del estadio:");
			direccion = in.next();
		}
		catch(Exception e){
			System.out.println("Error");
			direccion = EstadioDireccion();
		}
		return direccion;
	}
	public String EstadioCiudad(){
		String ciudad;
		try{
			System.out.println("Introduce la ciudad del estadio:");
			ciudad = in.next();
		}
		catch(Exception e){
			System.out.println("Error");
			ciudad = EstadioCiudad();
		}
		return ciudad;
	}
	public int EstadioCapacidad(){

		int capacidad;
		try{
			System.out.println("Introduce la capacidad del estadio:");
			capacidad = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			capacidad = EstadioCapacidad();
		}
		return capacidad;
	}
	public int PersonaId(){
        int id;
        try{
        	System.out.println("Introduce la id de la persona");
        	id = in.nextInt();
        }
        catch(Exception e){
        	System.out.println("Error");
        	id = -1;
        }
        return id;
    }
	public String PersonaNombre(){
        String nombre;
        try{
        	System.out.println("Introduce el nombre de la persona");
        	nombre = in.next();
        }
        catch(Exception e){
        	System.out.println("Error");
        	nombre = "-1";
        }
        return nombre;
    }
	public String PersonaEmail(){
        String email;
        try{
        	System.out.println("Introduce el email de la persona");
        	email = in.next();
        }
        catch(Exception e){
        	System.out.println("Error");
        	email = "-1";
        }
        return email;
    }
	public String PersonaTlf(){

        String tlf;
        try{
        	System.out.println("Introduce el tlf de la persona");
        	tlf = in.next();
        }
        catch(Exception e){
        	System.out.println("Error");
        	tlf = "-1";
        }
        return tlf;
    }
	public int JugadorSalario(){
        int salario;
        try{
        	System.out.println("Introduce el salario del jugador");
        	salario = in.nextInt();
        }
        catch(Exception e){
        	System.out.println("Error");
        	salario = -1;
        }
        return salario;
    }
	public String JugadorPosicion(){
        int eleccion;
        String posicion;
        try{
        	do{
        		System.out.println("Introduce la posicion del jugador");
        		System.out.println("1-Delantero, 2-Centrocampista, 3-Defensa, 4-Portero");
        		eleccion = in.nextInt();
        	}while(eleccion != 1 && eleccion != 2 && eleccion != 3 && eleccion != 4);
        	posicion = JugadorEleccion(eleccion);
        }
        catch(Exception e){
        	System.out.println("Error");
        	posicion = "-1";
        }
        return posicion;
    }
	public Boolean JugadorTitular(){
        Boolean titular,bucle;
        String aux;
        bucle = true;
        titular = false;
        try{
        	do{
        		System.out.println("Indica si el jugador es titular S/N");
        		aux = in.next();
        		if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
        			titular = true;
        			bucle = false;
        		}
        		else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
        			titular = false;
        			bucle = false;
        		}
        		else{
        			System.out.println("Error");
        		}
        	}while(bucle);
        }
        catch(Exception e){
           	System.out.println("Error");
           	titular = false;
        }
        return titular;
    }
	public int JugadorNumero(){
        int numero;
        try{
        	System.out.println("Introduce el numero del jugador");
        	numero = in.nextInt();
        }
        catch(Exception e){
        	System.out.println("Error");
        	numero = -1;
        }
        return numero;
    }
	public String JugadorEleccion(int num){
		String eleccion = "";
		if(num == 1){
			eleccion = "Delantero";
		}
		if(num == 2){
			eleccion = "Centrocampista";
		}
		if(num == 3){
			eleccion = "Defensa";
		}
		if(num == 4){
			eleccion = "Portero";
		}
		return eleccion;
	}
	public String ArbitroTipo(){
		int eleccion;
        String tipo;
        try{
        	do{
        		System.out.println("Introduce el tipo de arbitro");
        		System.out.println("1-Principal, 2-Asistente, 3-Cuarto, 4-Area");
        		eleccion = in.nextInt();
        	}while(eleccion != 1 && eleccion != 2 && eleccion != 3 && eleccion != 4);
        	tipo = ArbitroEleccion(eleccion);
        }
        catch(Exception e){
        	System.out.println("Error");
        	tipo = "-1";
        }
        return tipo;
	}
	public String ArbitroEleccion(int num){
		String eleccion = "";
		if(num == 1){
			eleccion = "Principal";
		}
		if(num == 2){
			eleccion = "Asistente";
		}
		if(num == 3){
			eleccion = "Cuarto";
		}
		if(num == 4){
			eleccion = "Area";
		}
		return eleccion;
	}
	public int PartidoId(){
		int id = 1;
		try{
			System.out.println("Introduce la id del partido");
			id = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			id = -1;
		}
		return id;
	}
	public Boolean PartidoIda(){
		Boolean ida,bucle;
        String aux;
        bucle = true;
        ida = false;
        try{
        	do{
        		System.out.println("Indica si el partido es de ida S/N");
        		aux = in.next();
        		if(aux.compareTo("S") == 0 || aux.compareTo("s") == 0){
        			ida = true;
        			bucle = false;
        		}
        		else if(aux.compareTo("N") == 0 || aux.compareTo("n") == 0){
        			ida = false;
        			bucle = false;
        		}
        		else{
        			System.out.println("Error");
        		}
        	}while(bucle);
        }
        catch(Exception e){
           	System.out.println("Error");
           	ida = false;
        }
        return ida;
	}
	public int PartidoGoles(){
		int goles = 1;
		try{
			System.out.println("Introduce los goles del equipo");
			goles = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			goles = -1;
		}
		return goles;
	}
	public int FechaAnio(){
		int anio;
		try{
			System.out.println("Introduce el a�o del partido");
			anio = in.nextInt();
		}
		catch(Exception e){
			System.out.println("Error");
			anio = -1;
		}
		return anio;
	}
	public int FechaMes(){
		int mes;
		try{
			do{
				System.out.println("Introduce el mes del partido");
				mes = in.nextInt();
			}while(mes <= 0 || mes >= 13);
		}
		catch(Exception e){
			System.out.println("Error");
			mes = -1;
		}
		return mes-1;
	}
	public int FechaDia(){
		int dia;
		try{
			do{
				System.out.println("Introduce el d�a del partido");
				dia = in.nextInt();
			}while(dia <= 0 || dia >= 32);
		}
		catch(Exception e){
			System.out.println("Error");
			dia = -1;
		}
		return dia;
	}
	public int FechaHora(){
		int hora;
		try{
			do{
				System.out.println("Introduce la hora del partido");
				hora = in.nextInt();
			}while(hora < 0 || hora >=24 );
		}
		catch(Exception e){
			System.out.println("Error");
			hora = -1;
		}
		return hora;
	}
	public int FechaMinuto(){
		int minuto;
		try{
			do{
				System.out.println("Introduce el minuto del partido");
				minuto = in.nextInt();
			}while(minuto < 0 || minuto >=60 );
		}
		catch(Exception e){
			System.out.println("Error");
			minuto = -1;
		}
		return minuto;
	}
	public void AddArbitro(int dni, String tipo){
		String sql;
		sql = "INSERT INTO ARBITRO (DNI, tipo) VALUES (" + dni + ", '"+ tipo + "');";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddArbitros_partido(int dni, int id){
		String sql;
		sql = "INSERT INTO ARBITROS_PARTIDO (DNI, partido) VALUES (" + dni + ", " + id + ");";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddEquipo(int id, int estadio, int puntos) throws SQLException{
		String sql;
		boolean repetido = false;
		ResultSet results;
		results = ConsultaEquipo();
		while(results.next()){
			if(results.getInt("id") == id){
				repetido = true;
			}
		}
		if(repetido){
			sql = "UPDATE EQUIPO SET estadio='" + estadio + "', puntos='" + puntos + "' WHERE id= " + id + ";";
		}
		else{
			sql = "INSERT INTO EQUIPO (id, estadio, puntos) VALUES ("
					+ id + ", " + estadio + ", " + puntos + ");";
		}
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddEstadio(int id, String direccion, String ciudad, int capacidad){
		String sql;
		sql = "INSERT INTO ESTADIO (id, direcci�n, ciudad, capacidad) VALUES (" 
			+ id + ", '" + direccion + "', '" + ciudad + "', " + capacidad + ");";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddJugador(int dni, int salario, String posicion, boolean titular, int numero, String equipo) throws SQLException{
		ResultSet results;
		String sql = "", fin = "";
		boolean repetido = false, insert = true;
		int tit;
		if(titular){
			tit = 1;
		}
		else{
			tit = 0;
		}
		results = ConsultaJugador();
		while(results.next()){
			if(results.getInt("DNI") == dni){
				repetido = true;
			}
		}
		if(repetido){
			sql = "UPDATE JUGADOR SET salario='" + salario + "', posici�n='" + posicion 
					+ "', titular='" + tit + "', numero='" + numero + "', equipo='";
			insert = false;
		}
		else{
			sql = "INSERT INTO JUGADOR (DNI, salario, posici�n, titular, numero, equipo) "
					+ "VALUES (" + dni + ", " + salario + ", '" + posicion + "', " + 
					tit + ", " + numero + ", ";
		}
		if(equipo == null){
			sql = sql + equipo;
		}
		else{
			sql = sql + Integer.valueOf(equipo);
		}
		if(insert){
			fin = ");";
		}
		else{
			fin = "' WHERE DNI= " + dni + ";";
		}
		sql = sql + fin;
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddJugadores_partido(int dni, int id){
		String sql;
		sql = "INSERT INTO JUGADORES_PARTIDO (DNI, partido) VALUES (" + dni + ", " + id + ");";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddPartido(int id, int idestadio, String fecha, int equipo1, int equipo2, boolean ida, int goleseq1, int goleseq2) throws java.text.ParseException{
		String sql;
		int IDA;
		if(ida){
			IDA = 1;
		}
		else{
			IDA = 0;
		}
		sql = "INSERT INTO PARTIDO (id, estadio, fecha, equipo1, equipo2, ida, goleseq1, goleseq2) VALUES (" 
		+ id + ", " + idestadio + ", '" + fecha.toString() + "', " + equipo1 + ", " + equipo2 + ", " + IDA + ", " + goleseq1 + ", " + goleseq2 + ");";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void AddPersonas(int dni, String nombre, String email, String tlf){
		String sql;
		sql = "INSERT INTO PERSONAS (DNI, nombre, email, tlf) VALUES (" 
				+ dni + ", '" + nombre + "', '" + email + "', '" + tlf + "');";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public ResultSet ConsultaArbitro(){
		String sql = "SELECT * FROM ARBITRO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaArbitros_partido(){
		String sql = "SELECT * FROM ARBITROS_PARTIDO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaEquipo(){
		String sql = "SELECT * FROM EQUIPO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaEstadio(){
		String sql = "SELECT * FROM ESTADIO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaJugador(){
		String sql = "SELECT * FROM JUGADOR";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaJugadores_partido(){
		String sql = "SELECT * FROM JUGADORES_PARTIDO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaPartido(){
		String sql = "SELECT * FROM PARTIDO";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public ResultSet ConsultaPersonas(){
		String sql = "SELECT * FROM PERSONAS";
		return AppFutbolMenu.Conexion().ejecutarConsulta(sql);
	}
	public void RemoveArbitro(int dni){
		String sql = "DELETE FROM ARBITRO WHERE ARBITRO.DNI=" + dni + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveArbitros_partido(int dni){
		String sql = "DELETE FROM ARBITROS_PARTIDO WHERE ARBITROS_PARTIDO.DNI=" + dni + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveArbitros_partido2(int id){
		String sql = "DELETE FROM ARBITROS_PARTIDO WHERE ARBITROS_PARTIDO.partido=" + id + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveEquipo(int id){
		String sql = "DELETE FROM EQUIPO WHERE EQUIPO.ID=" + id + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveEstadio(int id){
		String sql = "DELETE FROM ESTADIO WHERE ESTADIO.ID=" + id + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveJugador(int dni){
		String sql = "DELETE FROM JUGADOR WHERE JUGADOR.DNI=" + dni + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveJugadores_partido(int dni){
		String sql = "DELETE FROM JUGADORES_PARTIDO WHERE JUGADORES_PARTIDO.DNI=" + dni + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemoveJugadores_partido2(int id){
		String sql = "DELETE FROM JUGADORES_PARTIDO WHERE JUGADORES_PARTIDO.partido=" + id + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemovePartido(int id){
		String sql = "DELETE FROM PARTIDO WHERE PARTIDO.ID=" + id + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
	public void RemovePersonas(int dni){
		String sql = "DELETE FROM PERSONAS WHERE PERSONAS.DNI=" + dni + ";";
		AppFutbolMenu.Conexion().ejecutar(sql);
	}
}