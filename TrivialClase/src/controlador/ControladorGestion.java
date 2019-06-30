package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ejb.PreguntaFacade;
import ejb.UsuarioFacade;
import modelo.Pregunta;
import modelo.Usuario;

@Named
@ViewScoped
public class ControladorGestion implements Serializable{
	/* Controlador que servirá para que el usuario 'admin'
		pueda ver las preguntas que hay en la base de datos
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PreguntaFacade preguntaEJB;
	@EJB
	private UsuarioFacade usuarioEJB;
	
	//@Injec
	private List<Pregunta> listaPreguntas;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorGestion");
		
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		if(us == null) {
			FacesContext contex = FacesContext.getCurrentInstance();
	        try {
				contex.getExternalContext().redirect( "../index.daw" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Obtiene todas las preguntas de la base de datos
		try {
			listaPreguntas = preguntaEJB.findAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Inicio GET y SET
	public PreguntaFacade getPreguntaEJB() {
		return preguntaEJB;
	}

	public void setPreguntaEJB(PreguntaFacade preguntaEJB) {
		this.preguntaEJB = preguntaEJB;
	}

	public List<Pregunta> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<Pregunta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}
	// Fin GET y SET

	/* Función que se encarga de devolver el usuario administrador a la página para mostrarlo */
	public String mostrarUsuarioLogeado() {
		
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		return us.getUsuario();
	}
	
	/* Función que se encarga de eliminar la sesión activa y enviár al usuario a la página de inicio */
	public void cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext contex = FacesContext.getCurrentInstance();
        try {
			contex.getExternalContext().redirect( "../index.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
