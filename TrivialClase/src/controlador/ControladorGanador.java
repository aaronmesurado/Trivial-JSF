package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import modelo.Jugador;
import modelo.Usuario;


@Named
@ViewScoped
public class ControladorGanador implements Serializable{
	/* Controlador que se encarga de mostrar quien es el ganador de la partida */
	private static final long serialVersionUID = 1L;
	
	private Jugador ganador;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorGanador");
		// Obtiene los jugadores que se han creado
		ganador = (Jugador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ganador");
		
		// Comprobación de si la lista está vacía para redirección
		if(ganador == null) {
			FacesContext contex = FacesContext.getCurrentInstance();
			try {
				contex.getExternalContext().redirect( "./seleccionJugadores.daw" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//Inicio GET y SET
	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}
	// Fin GET y SET
	
	public void volver() {
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		FacesContext contex = FacesContext.getCurrentInstance();
		
		try {
			
			if(us == null) {
				contex.getExternalContext().redirect( "../index.daw" );
			}
			else {
				contex.getExternalContext().redirect( "../solitario/seleccionCategoria.daw" );
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}