package practica1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;	

public class AppFutbol{
	
	static Scanner in = new Scanner (System.in);
	HashMap<Integer, Equipo> mEquipo = new HashMap<Integer, Equipo>(); //el integer será el idequipo
	HashMap<Integer, Jugador> mJugador = new HashMap<Integer, Jugador>(); //Integer será idjugador
	HashMap<Integer, Arbitro> mArbitro = new HashMap<Integer, Arbitro>(); // ..igual
	HashMap<Integer, Estadio> mEstadio = new HashMap<Integer, Estadio>(); //..igual
	ArrayList<Partido> mPartido = new ArrayList<Partido>();
	
	public AppFutbol(){//Aquí se pueden cargar los datos o en un nuevo método
		
	}
	public void AltaEquipo(){
		//Declaraciones
		Object key;
		Iterator<Integer> it;
		int id, puntos;
		Boolean bucle;
		//Pido la id y busco que no esté repetida
		do{
			id = EquipoId();
			bucle = false;
			it = mEquipo.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				if(mEquipo.get(key).GetEquipoId() == id){
					System.out.println("Ya hay un equipo con esa id");
					bucle = true;
				}
			}
		}while(bucle);
		//Como no está repetido creo el nuevo Equipo
		puntos = EquipoPuntos();
		Equipo equipo = new Equipo(id, puntos);
		mEquipo.put(id, equipo);
		//Caso de error
		if(id == -1 || puntos == -1){
			System.out.println("Ha habido un error");
			mEquipo.remove(id);
		}
	}
	public void BajaEquipo(){
		//Declaraciones
		int id, borrar = 1;
		String aux;
		Boolean bucle, mostrar, error;
		Object key;
		Iterator<Integer> it;
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Doy la opción de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("¿Desea que se le muestren los equipos en el sistema? S/N");
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
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).GetEquipoId() == id){
							bucle = false;
							borrar = id;
						}
					}
				}while(bucle);
				mEquipo.remove(borrar);
			}
		}
	}
	public void AltaJugador(){ //Se da de alta en un equipo y si no está en el sistema también
		//Declaraciones
		Jugador jugador = null;
		Object key;
		Iterator<Integer> it;
		String nombre, email, tlf, posicion, aux;
		int id, id2, salario, num;
		Boolean bucle, titular, error, mostrar;
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Pido la id y busco que no esté repetida
			bucle = mostrar = true;
			error = false;
			id2 = -1;
			do{
				id = PersonaId();
				bucle = false;
				it = mJugador.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					if(mJugador.get(key).GetPersonaId() == id){
						System.out.println("Ya hay un jugador con esa id");
						bucle = true;
					}
				}
			}while(bucle);
			//Como no está repetido creo el nuevo Jugador
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
				//Creo el jugador
				Personas persona = new Personas(id, nombre, email, tlf);
				jugador = new Jugador(persona, salario, posicion, titular, num);
				//Doy la opcion de mostrar los equipos en los que meter al jugador
				bucle = true;
				try{
					do{
						System.out.println("¿Desea que se le muestren los equipos en el sistema? S/N");
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
				//Meto al jugador en la base de datos y en un equipo
				mJugador.put(id, jugador);
				//Pido que meta al jugador en un equipo
				bucle = true;
				do{
					System.out.println("Introduzca al jugador en un equipo");
					id2 = EquipoId();
					if(id2 == -1){
						bucle = false;
					}
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).GetEquipoId() == id2){
							mEquipo.get(key).AltaJugador(jugador);
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
	public void BajaJugador(){ // de un equipo, no del sistema
		//Declaraciones
		int i, id, borrar, ide;
		Boolean bucle, mostrar, error;
		String aux;
		Object key, borr;
		Iterator<Integer> it;
		if(mJugador.isEmpty()){
			System.out.println("No hay jugadores en el sistema");
		}
		else{
			//Doy la opción de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			borrar = 0;
			borr = null;
			try{
				do{
					System.out.println("¿Desea que se le muestren los jugadores en el sistema? S/N");
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
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						System.out.print("En el equipo de id: " +mEquipo.get(key).GetEquipoId());
						System.out.println(" están:");
						for(i = 0; i < mEquipo.get(key).ejugador.size(); i++){
							System.out.print("El jugador: " + mEquipo.get(key).ejugador.get(i).GetPersonaNombre());
							System.out.println(" con id: " + mEquipo.get(key).ejugador.get(i).GetPersonaId());
						}
					}
				}
				//Borro el equipo por id
				bucle = true;
				do{
					System.out.println("De qué equipo quieres borrar al jugador");
					ide = EquipoId();
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).GetEquipoId() == ide){
							borr = key;
							bucle = false;
						}
					}
				}while(bucle);
				bucle = true;
				do{
					id = PersonaId();
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						for(i = 0; i < mEquipo.get(borr).ejugador.size(); i++){
							borrar = id;
							bucle = false;
						}
					}
				}while(bucle);
				mEquipo.get(borr).BajaJugador(borrar);
			}
		}
	}
	public void AltaArbitro(){
		//Declaraciones
		Arbitro arbitro;
		Personas persona;
		Object key;
		Iterator<Integer> it;
		int id;
		String nombre, email, tlf, tipo;
		Boolean bucle;
		//Pido la id y busco que no esté repetida
		do{
			id = PersonaId();
			bucle = false;
			it = mArbitro.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				if(mArbitro.get(key).GetPersonaId() == id){
					System.out.println("Ya hay un arbitro con esa id");
					bucle = true;
				}
			}
		}while(bucle);
		//Como no está repetido creo el nuevo Arbitro
		nombre = PersonaNombre();
		email = PersonaEmail();
		tlf = PersonaTlf();
		tipo = ArbitroTipo();
		persona = new Personas(id, nombre, email, tlf);
		arbitro = new Arbitro(persona, tipo);
		mArbitro.put(id, arbitro);
		//Caso de error
		if(id == -1 || nombre.compareTo("-1") == 0 || email.compareTo("-1") == 0
				|| tlf.compareTo("-1") == 0 || tipo.compareTo("-1") == 0){
			System.out.println("Ha habido un error");
			mArbitro.remove(id);
		}
	}
	public void BajaArbitro(){
		//Declaraciones
		Boolean bucle, mostrar, error;
		String aux;
		int id, borrar;
		Iterator<Integer> it;
		Object key;
		borrar = 0;
		if(mArbitro.isEmpty()){
			System.out.println("No hay arbitros en el sistema");
		}
		else{
			//Doy la opción de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			try{
				do{
					System.out.println("¿Desea que se le muestren los arbitros en el sistema? S/N");
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
				//Borro el equipo por id
				bucle = true;
				do{
					id = PersonaId();
					it = mArbitro.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mArbitro.get(key).GetPersonaId() == id){
							bucle = false;
							borrar = id;
						}
					}
				}while(bucle);
				mArbitro.remove(borrar);
			}
		}
	}
	public void AltaEstadio(){ //del sistema
		//Declaraciones
		Estadio estadio = null;
		Object key;
		Iterator<Integer> it;
		int id, capacidad, id2;
		String ciudad, direccion, aux;
		Boolean bucle, error, mostrar;
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Pido la id y busco que la id no esté repetida
			error = false;
			mostrar = true;
			id2 = 0;
			do{
				id = EstadioId();
				bucle = false;
				it = mEstadio.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					if(mEstadio.get(key).GetEstadioId() == id){
						System.out.println("Ya hay un estadio con esa id");
						bucle = true;
					}
				}
			}while(bucle);
			//Como no está repetido creo el nuevo Equipo
			ciudad = EstadioCiudad();
			direccion = EstadioDireccion();
			capacidad = EstadioCapacidad();
			//Muestro los equipos
			if(id == -1 || ciudad.compareTo("-1") == 0 || direccion.compareTo("-1") == 0 
					|| capacidad == -1){
				error = true;
			}
			else{
				//Creo el estadio
				estadio = new Estadio(id, direccion, ciudad, capacidad);
				//Doy la opcion de mostrar los equipos en los que meter al estadio
				bucle = true;
				try{
					do{
						System.out.println("¿Desea que se le muestren los equipos en el sistema? S/N");
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
				mEstadio.put(id, estadio);
				//Pido que meta al jugador en un equipo
				bucle = true;
				do{
					System.out.println("Introduzca al estadio en un equipo");
					id2 = EquipoId();
					if(id2 == -1){
						bucle = false;
					}
					//Lo añado a un equipo
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).GetEquipoId() == id2){
							mEquipo.get(key).AltaEstadio(estadio);
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
	public void AltaPartido(){
		//Declaraciones
		Estadio estadio;
		ArrayList<Arbitro> arbitro = new ArrayList<Arbitro>();
		ArrayList<Jugador> jugador1, jugador2;
		Equipo equipo1, equipo2;
		Boolean bucle, ida, aniadir, repetido;
		String aux;
		int ocurrencias, id, i, idequipo, idestadio, golesEq1, golesEq2, idarbitro, anio, mes, dia, hora, minuto;
		Iterator<Integer> it;
		Object key;
		estadio = null;
		equipo1 = equipo2 = null;
		ocurrencias = id = golesEq1 = golesEq2 = 0;
		aniadir = ida = true;
		jugador1 = jugador2 = null;
		//Tengo equipos
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Tengo estadios
			if(mEstadio.isEmpty()){
				System.out.println("No hay estadios en el sistema");
			}
			else{
				//Tengo arbitros
				if(mArbitro.isEmpty()){
					System.out.println("No hay arbitros en el sistema");
				}
				else{
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).ejugador.isEmpty() == false){
							ocurrencias++;
						}
					}
					//Tengo dos o más equipos con jugadores
					if(ocurrencias >= 2){
						//Compruebo que no se repita la id del partido
						do{
							bucle = false;
							id = PartidoId();
							for(i = 0; i < mPartido.size(); i++){
								if(mPartido.get(i).GetPartidoId() == id){
									System.out.println("Ya hay un partido con esa id");
									bucle = true;
								}
							}
						}while(bucle);
						//Añado los equipos
						it = mEquipo.keySet().iterator();
						while(it.hasNext()){
							key = it.next();
							if(mEquipo.get(key).ejugador.isEmpty() == false){
								System.out.println("El equipo con id: " + mEquipo.get(key).GetEquipoId()
										+ " tiene jugadores");
							}
						}
						do{
							bucle = true;
							System.out.println("Introduzca el primer equipo que juega el partido");
							idequipo = EquipoId();
							it = mEquipo.keySet().iterator();
							while(it.hasNext()){
								key = it.next();
								if(mEquipo.get(key).ejugador.isEmpty() == false && mEquipo.get(key).GetEquipoId() == idequipo){
									equipo1 = mEquipo.get(key);
									bucle = false;
								}
							}
						}while(bucle);
						do{
							bucle = true;
							System.out.println("Introduzca el segundo equipo que juega el partido");
							idequipo = EquipoId();
							it = mEquipo.keySet().iterator();
							while(it.hasNext()){
								key = it.next();
								if(mEquipo.get(key).ejugador.isEmpty() == false && mEquipo.get(key).GetEquipoId() == idequipo 
										&& mEquipo.get(key) != equipo1){
									equipo2 = mEquipo.get(key);
									bucle = false;
								}
							}
						}while(bucle);
						jugador1 = equipo1.ejugador;
						jugador2 = equipo2.ejugador;
						ListarEstadios();
						do{
							bucle = true;
							System.out.println("Introduzca el estadio donde se juega el partido");
							idestadio = EstadioId();
							it = mEstadio.keySet().iterator();
							while(it.hasNext()){
								key = it.next();
								if(mEstadio.get(key).GetEstadioId() == idestadio){
									bucle = false;
									estadio = mEstadio.get(key);
								}
							}
						}while(bucle);
						System.out.println("Equipo 1");
						golesEq1 = PartidoGoles();
						System.out.println("Equipo 2");
						golesEq2 = PartidoGoles();
						ida = PartidoIda();
						//Añado un árbitro
						ListarArbitros();
						do{
							bucle = true;
							System.out.println("Introduzca los árbitros del partido");
							idarbitro = PersonaId();
							it = mArbitro.keySet().iterator();
							while(it.hasNext()){
								key = it.next();
								if(mArbitro.get(key).GetPersonaId() == idarbitro){
									arbitro.add(mArbitro.get(key));
									bucle = false;
								}
							}
						}while(bucle);
						//Doy la opción de añadir más
						do{
							do{
								System.out.println("¿Quieres añadir otro árbitro? S/N");
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
									it = mArbitro.keySet().iterator();
									while(it.hasNext()){
										key = it.next();
										if(mArbitro.get(key).GetPersonaId() == idarbitro){
											repetido = false;
											for(i = 0; i < arbitro.size(); i++){
												if(arbitro.get(i).GetPersonaId() == idarbitro){
													System.out.println("Ese árbitro ya está en el partido");
													repetido = true;
												}
												bucle = false;
											}
											if(repetido == false){
												arbitro.add(mArbitro.get(key));
											}
										}
									}
								}while(bucle);
							}
						}while(aniadir);
					}
					//Introduzco la fecha
					System.out.println("Introduzca la fecha del partido");
					anio = FechaAnio();
					mes = FechaMes();
					dia = FechaDia();
					hora = FechaHora();
					minuto = FechaMinuto();
					Fecha fecha = new Fecha(anio, mes, dia, hora, minuto);
					Partido partido = new Partido(id, estadio, fecha, equipo1, equipo2, ida,
							arbitro, jugador1, jugador2, golesEq1, golesEq2);
					mPartido.add(partido);
				}
			}
		}
	}
	public void BajaPartido(){
		//Declaraciones
		Boolean bucle, mostrar, error;
		String aux;
		int id, i;
		Partido borrar;
		if(mPartido.isEmpty()){
			System.out.println("No hay partidos en el sistema");
		}
		else{
			//Doy la opción de que se le muestren los equipos que hay para que elija uno
			bucle = mostrar = true;
			error = false;
			borrar = null;
			try{
				do{
					System.out.println("¿Desea que se le muestren los partidos en el sistema? S/N");
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
					for(i = 0; i < mPartido.size(); i++){
						System.out.print("El partido con id: " + mPartido.get(i).GetPartidoId() + " que se jugó el: ");
						mPartido.get(i).GetPartidoFecha().GetFecha();
					}
				}
				//Borro el equipo por id
				bucle = true;
				do{
					id = PartidoId();
					for(i = 0; i < mPartido.size(); i++){
						if(mPartido.get(i).GetPartidoId() == id){
							bucle = false;
							borrar = mPartido.get(i);
						}
					}
				}while(bucle);
				mPartido.remove(borrar);
			}
		}
	}
	public void ListarEquipos(){
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			System.out.println("Los equipos son:");
			for (Entry<Integer, Equipo> equipo : mEquipo.entrySet()){
				Equipo valor = equipo.getValue();
				System.out.print("El equipo con id: " + valor.GetEquipoId());
				System.out.println(" y puntuación de: " + valor.GetEquipoPuntos());
			}
		}
	}
	public void ListarEstadios(){
		if(mEstadio.isEmpty()){
			System.out.println("No hay estadios en el sistema");
		}
		else{
			System.out.println("Los estadios son:");
			for (Entry<Integer, Estadio> estadio : mEstadio.entrySet()){
				Estadio valor = estadio.getValue();
				System.out.print("El estadio con id: " + valor.GetEstadioId());
				System.out.print(" que está en la ciudad de: " + valor.GetEstadioCiudad());
				System.out.print(" en la calle: " + valor.GetEstadioDireccion());
				System.out.println(" y con capacidad para: " + valor.GetEstadioCapacidad());
			}
		}
	}
	public void ListarArbitros(){
		if(mArbitro.isEmpty()){
			System.out.println("No hay arbitros en el sistema");
		}
		else{
			System.out.println("Los arbitros son:");
			for (Entry<Integer, Arbitro> arbitro : mArbitro.entrySet()){
				Arbitro valor = arbitro.getValue();
				System.out.print("El arbitro con id: " + valor.GetPersonaId());
				System.out.print(", de nombre: " + valor.GetPersonaNombre());
				System.out.print(", con email: " + valor.GetPersonaEmail());
				System.out.print(", con tlf: " + valor.GetPersonaTlf());
				System.out.println(" es: " + valor.GetArbitroTipo());
			}
		}
	}
	public void ContarPartidos(){
		//Declaraciones
		if(mPartido.isEmpty()){
			System.out.println("No hay partidos en el sistema");
		}
		else{
			System.out.println("Hay " + mPartido.size() + " partidos");
		}
	}
	public void ListarPartidos(){ //devuelve info del partido dada una fecha
		//Declaraciones
		int anio, mes, dia, i;
		if(mPartido.isEmpty()){
			System.out.println("No hay partidos en el sistema");
		}
		else{
			System.out.println("Selecciona el día del que mirar los partidos");
			anio = FechaAnio();
			mes = FechaMes();
			dia = FechaDia();
			for(i = 0; i < mPartido.size(); i++){
				if(mPartido.get(i).GetPartidoFecha().GetFechaAnio() == anio 
						&& mPartido.get(i).GetPartidoFecha().GetFechaMes() == mes
						&& mPartido.get(i).GetPartidoFecha().GetFechaDia() == dia){
					System.out.print("El partido con id: " + mPartido.get(i).GetPartidoId());
					System.out.print(", en el que juegan los equipos con id: " + mPartido.get(i).GetPartidoEquipo1().GetEquipoId());
					System.out.print(" y " + mPartido.get(i).GetPartidoEquipo2().GetEquipoId());
					System.out.print(" se jugó ");
					mPartido.get(i).GetPartidoFecha().GetFecha();
				}
			}
		}
	}
	public void ListarPartidosEquipo(){//Devuelve la info del partido dado un equipo
		//Declaraciones
		int id, i;
		Equipo equipo;
		Boolean bucle;
		Iterator<Integer> it;
		Object key;
		equipo = null;
		if(mPartido.isEmpty()){
			System.out.println("No hay partidos en el sistema");
		}
		else{
			ListarEquipos();
			do{
				bucle = true;
				id = EquipoId();
				it = mEquipo.keySet().iterator();
				while(it.hasNext()){
					key = it.next();
					if(mEquipo.get(key).GetEquipoId() == id){
						equipo = mEquipo.get(key);
						bucle = false;
					}
				}
			}while(bucle);
			for(i = 0; i < mPartido.size(); i++){
				if(mPartido.get(i).GetPartidoEquipo1().GetEquipoId() == equipo.GetEquipoId()
						|| mPartido.get(i).GetPartidoEquipo2().GetEquipoId() == equipo.GetEquipoId()){
					System.out.print("El equipo con id: " + equipo.GetEquipoId());
					System.out.print(", juega el partido con id: " + mPartido.get(i).GetPartidoId());
					if(mPartido.get(i).GetPartidoEquipo1().GetEquipoId() == equipo.GetEquipoId()){
						System.out.println(", marcó " + mPartido.get(i).GetPartidoGolesEq1() + " goles");
					}
					else{
						System.out.println(", marcó " + mPartido.get(i).GetPartidoGolesEq2() + " goles");
					}
				}
			}
		}
	}
	public void ListarJugadores(){//dada una posición en el campo
		//Declaraciones
		String posicion;
		if(mJugador.isEmpty()){
			System.out.println("No hay jugadores en el sistema");
		}
		else{
			System.out.println("Seleccione la posición");
			posicion = JugadorPosicion();
			if(posicion.compareTo("-1") == 0){
				System.out.println("Ha habido un error");
			}
			else{
				System.out.println("Los jugadores en la posición de " + posicion + " son:");
				for (Entry<Integer, Jugador> jugador : mJugador.entrySet()){
					Jugador valor = jugador.getValue();
					if(valor.GetJugadorPosicion().compareTo(posicion) == 0){
						System.out.print("El jugador con id: " + valor.GetPersonaId());
						System.out.print(", de nombre: " + valor.GetPersonaNombre());
						System.out.print(", con email: " + valor.GetPersonaEmail());
						System.out.print(", con tlf: " + valor.GetPersonaTlf());
						System.out.print(", gana: " + valor.GetJugadorSalario());
						System.out.print(", tiene el número: " + valor.GetJugadorNumero());
						if(valor.GetJugadorTitular()){
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
	public void ListarJugadoresEquipo(){//dado un equipo
		//Declaraciones
		int id, i;
		Boolean novacio = false, bucle = true;
		Iterator<Integer> it;
		Object key, elegido = null;
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Compruebo que los equipos tengan jugadores
			it = mEquipo.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				if(mEquipo.get(key).ejugador.isEmpty() == false){
					novacio = true;
				}
			}
			if(novacio){
				do{
					System.out.println("Seleccione el equipo");
					id = EquipoId();
					if(id == -1){
						System.out.println("Ha habido un error");
					}
					it = mEquipo.keySet().iterator();
					while(it.hasNext()){
						key = it.next();
						if(mEquipo.get(key).GetEquipoId() == id){
							bucle = false;
							elegido = key;
						}
					}
				}while(bucle);
				System.out.println("Los jugadores en el equipo de id: " + mEquipo.get(elegido).GetEquipoId() + " son:");
				for(i = 0; i < mEquipo.get(elegido).ejugador.size(); i++){
					System.out.print("El jugador con id: " + mEquipo.get(elegido).ejugador.get(i).GetPersonaId());
					System.out.print(", de nombre: " + mEquipo.get(elegido).ejugador.get(i).GetPersonaNombre());
					System.out.print(", con email: " + mEquipo.get(elegido).ejugador.get(i).GetPersonaEmail());
					System.out.print(", con tlf: " + mEquipo.get(elegido).ejugador.get(i).GetPersonaEmail());
					System.out.print(", gana: " + mEquipo.get(elegido).ejugador.get(i).GetJugadorSalario());
					System.out.print(", tiene el número: " + mEquipo.get(elegido).ejugador.get(i).GetJugadorNumero());
					System.out.print(", juega de: " + mEquipo.get(elegido).ejugador.get(i).GetJugadorPosicion());
					if(mEquipo.get(elegido).ejugador.get(i).GetJugadorTitular()){
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
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoFecha().GetFechaHora()));
					bwPar.newLine();
					bwPar.write(String.valueOf(mPartido.get(i).GetPartidoFecha().GetFechaMinuto()));
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
		int i, id, idestadio, capacidad, idequipo, puntos, salario, numero, idpartido, aux, anio, mes, dia, hora, minuto, golesEq1, golesEq2;
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
			//Cargo los árbitros
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
					hora = Integer.parseInt(brPar.readLine());
					minuto = Integer.parseInt(brPar.readLine());
					fecha = new Fecha(anio, mes, dia, hora, minuto);
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
	public void CalcularCampeonTemporada(){
		//int[] puntos, equipos;
		int puntos[] = new int[mEquipo.size()]; 
		int equipos[] = new int[mEquipo.size()];
		int i, j, aux, aux2;
		//Para calcular el campeon hay cojer todos los equipos, ver los puntos que tiene cada uno y añadir
		//en función de si han ganado o empatado partidos.
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Cojer cada equipo y sus puntos
			i = 0;
			for (Entry<Integer, Equipo> equipo : mEquipo.entrySet()){
				Equipo valor = equipo.getValue();
				equipos[i] = valor.GetEquipoId();
				puntos[i] = valor.GetEquipoPuntos();
				i++;
			}
			if(mPartido.isEmpty()){
				System.out.println("El campeón se ha calculado con los puntos indicados al crear el equipo");
				System.out.println("No hay partidos en el sistema");
			}
			else{
				//Calcular la ganancia de puntos de cada equipo
				for(i = 0; i < mPartido.size(); i++){
					if(mPartido.get(i).GetPartidoGolesEq1() > mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo1().GetEquipoId()){
								puntos[j] += 3;
							}
						}
					}
					if(mPartido.get(i).GetPartidoGolesEq1() < mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo2().GetEquipoId()){
								puntos[j] += 3;
							}
						}
					}
					if(mPartido.get(i).GetPartidoGolesEq1() == mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo1().GetEquipoId()){
								puntos[j] += 1;
							}
						}
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo2().GetEquipoId()){
								puntos[j] += 1;
							}
						}
					}
				}
			}
			//Calcular el campeón
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
					System.out.println("El equipo con id " + equipos[i] + " queda como campeón");
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
	public void CalcularPosicionesEquipos(){
		int puntos[] = new int[mEquipo.size()]; 
		int equipos[] = new int[mEquipo.size()];
		int i, j, aux, aux2;
		//Para calcular el campeon hay cojer todos los equipos, ver los puntos que tiene cada uno y añadir
		//en función de si han ganado o empatado partidos.
		if(mEquipo.isEmpty()){
			System.out.println("No hay equipos en el sistema");
		}
		else{
			//Cojer cada equipo y sus puntos
			i = 0;
			for (Entry<Integer, Equipo> equipo : mEquipo.entrySet()){
				Equipo valor = equipo.getValue();
				equipos[i] = valor.GetEquipoId();
				puntos[i] = valor.GetEquipoPuntos();
				i++;
			}
			if(mPartido.isEmpty()){
				System.out.println("El campeón se ha calculado con los puntos indicados al crear el equipo");
				System.out.println("No hay partidos en el sistema");
			}
			else{
				//Calcular la ganancia de puntos de cada equipo
				for(i = 0; i < mPartido.size(); i++){
					if(mPartido.get(i).GetPartidoGolesEq1() > mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo1().GetEquipoId()){
								puntos[j] += 3;
							}
						}
					}
					if(mPartido.get(i).GetPartidoGolesEq1() < mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo2().GetEquipoId()){
								puntos[j] += 3;
							}
						}
					}
					if(mPartido.get(i).GetPartidoGolesEq1() == mPartido.get(i).GetPartidoGolesEq2()){
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo1().GetEquipoId()){
								puntos[j] += 1;
							}
						}
						for(j = 0; j < equipos.length; j++){
							if(equipos[j] == mPartido.get(i).GetPartidoEquipo2().GetEquipoId()){
								puntos[j] += 1;
							}
						}
					}
				}
			}
			//Calcular el campeón
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
				System.out.println("El equipo con id " + equipos[i] + " queda en la posición: " + j + "º");
				if((i + 1) != equipos.length){
					if(puntos[i] > puntos[i+1]){
						j++;
					}
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
		String nombre, email, tlf, posicion, direccion, ciudad;
		int dni, salario, numero, equipo, tit, id, capacidad;
		boolean titular;
		Iterator<Integer> it = null, ith = null;
		Integer key = null;
		if(mEstadio.isEmpty() == false){
			it = mEstadio.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				id = Integer.valueOf(mEstadio.get(key).GetEstadioId());
				direccion = String.valueOf(mEstadio.get(key).GetEstadioDireccion());
				ciudad = String.valueOf(mEstadio.get(key).GetEstadioCiudad());
				capacidad = Integer.valueOf(mEstadio.get(key).GetEstadioCapacidad());
				sql = "INSERT INTO ESTADIO (id, dirección, ciudad, capacidad) VALUES (" 
						+ id + ", '" + direccion + "', '" + ciudad + "', " + capacidad + ");";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
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
				sql = "INSERT INTO JUGADOR (DNI, salario, posición, titular, numero, equipo) "
						+ "VALUES (" + dni + ", " + salario + ", '" + posicion + "', " + 
						tit + ", " + numero + ", " + equipo + ");";
				AppFutbolMenu.Conexion().ejecutar(sql);
			}
		}
	}
	
	//Métodos
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
			System.out.println("Introduce el año del partido");
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
				System.out.println("Introduce el día del partido");
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
}