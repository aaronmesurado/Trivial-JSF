package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import modelo.Jugador;
import modelo.Usuario;

@Named
@ViewScoped
public class ControladorSelecJugadores implements Serializable{
	/* Controlador que se encarga de saber el número de
		jugadores que van a jugar la partida multijugador
	*/
	private static final long serialVersionUID = 1L;
	
	private Jugador jug1;
	private Jugador jug2;
	private Jugador jug3;
	private Jugador jug4;
	
	private String j1;
	private String j2;
	private String j3;
	private String j4;
	

	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorSelecJugadores");
		// Obtiene al usuario logueado, si lo hay
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		if(us != null) {
			j1 = us.getUsuario(); // Establece el nombre si alguien hizo loguin
		}
		
		// Creación de los jugadores
		jug1 = new Jugador();
		jug2 = new Jugador();
		jug3 = new Jugador();
		jug4 = new Jugador();
	}
	
	// Inicio GET y SET
	public String getJ1() {
		return j1;
	}

	public void setJ1(String j1) {
		this.j1 = j1;
	}

	public String getJ2() {
		return j2;
	}

	public void setJ2(String j2) {
		this.j2 = j2;
	}

	public String getJ3() {
		return j3;
	}

	public void setJ3(String j3) {
		this.j3 = j3;
	}

	public String getJ4() {
		return j4;
	}

	public void setJ4(String j4) {
		this.j4 = j4;
	}

	public Jugador getJug1() {
		return jug1;
	}

	public void setJug1(Jugador jug1) {
		this.jug1 = jug1;
	}

	public Jugador getJug2() {
		return jug2;
	}

	public void setJug2(Jugador jug2) {
		this.jug2 = jug2;
	}

	public Jugador getJug3() {
		return jug3;
	}

	public void setJug3(Jugador jug3) {
		this.jug3 = jug3;
	}

	public Jugador getJug4() {
		return jug4;
	}

	public void setJug4(Jugador jug4) {
		this.jug4 = jug4;
	}
	// Fin GET y SET

	/* Función que obtien los nombre de los jugadores y prepara los datos por defecto */
	public void jugar(){
		ArrayList<Jugador> jugadores = new ArrayList<>();
		// Estable el nombre de los jugadores
		jug1.setNombre(j1); // Se estable el nombre por si nadie hizo loguin
		jugadores.add(jug1);
		jug2.setNombre(j2);
		jugadores.add(jug2);
		// Comprobación de si hay más jugadores
		if(!j3.isEmpty()) {
			jug3.setNombre(j3);
			jugadores.add(jug3);
		}
		if(!j4.isEmpty()) {
			jug4.setNombre(j4);
			jugadores.add(jug4);
		}
		// Establece el primer jugador activo de forma aleatoria
		int aleat = numAleatorio(0, jugadores.size());
		jugadores.get(aleat).setTurno(true);
		// Establece los valores por defecto
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jugadores", jugadores);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jugActivo", aleat);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ciencias", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deportes", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("espectaculo", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("historia", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("geografia", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("literatura", null);
		FacesContext contex = FacesContext.getCurrentInstance();
		try {
			contex.getExternalContext().redirect( "./jugar.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Función que se encarga de generar un número aletorio
		entre los parámetros que se le pasa y lo devuelve
	*/
	private int numAleatorio(int min, int max) {
		int aleat = (int)  Math.floor(Math.random()*max + min);
		return aleat;
	}
	
}