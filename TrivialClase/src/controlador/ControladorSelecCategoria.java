package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ejb.CategoriaFacade;
import ejb.PuntuacionFacade;
import ejb.UsuarioFacade;
import modelo.Categoria;
import modelo.Puntuacion;
import modelo.Usuario;

@Named
@ViewScoped
public class ControladorSelecCategoria implements Serializable{
	/* Controlador que se encarga de mostrar al usuario registrado las categorías existentes
		en la base de datos, establecer datos por defecto antes de iniciar partida ...
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioFacade usuarioEJB;
	@EJB
	private CategoriaFacade categoriaEJB;
	@EJB
	private PuntuacionFacade puntuacionEJB;
	
	private Usuario usuario;
	private List<Categoria> listaCategorias;
	private Categoria categoria;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorSelecCategoria");
		usuario = new Usuario();
		
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
		
		try {
			listaCategorias = categoriaEJB.findAll(); // Obtiene todas las categorías de la base de datos
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Inicio GET y SET
	public CategoriaFacade getCategoriaEJB() {
		return categoriaEJB;
	}

	public void setCategoriaEJB(CategoriaFacade categoriaEJB) {
		this.categoriaEJB = categoriaEJB;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public UsuarioFacade getUsuarioEJB() {
		return usuarioEJB;
	}

	public void setUsuarioEJB(UsuarioFacade usuarioEJB) {
		this.usuarioEJB = usuarioEJB;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public PuntuacionFacade getPuntuacionEJB() {
		return puntuacionEJB;
	}

	public void setPuntuacionEJB(PuntuacionFacade puntuacionEJB) {
		this.puntuacionEJB = puntuacionEJB;
	}
	// Fin GET y SET

	/* Función que se encarga de devolver el usuario logeado a la página para mostrarlo */
	public String mostrarUsuarioLogeado() {
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		return us.getUsuario();
	}
	
	/* Función que estable valores por defecto antes de pasar a la página que carga las preguntas */
	public void catSeleccionada(int idCat) {	
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("preguntas", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("puntos", 0);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vidas", 3);
		FacesContext contex = FacesContext.getCurrentInstance();
        try {
			contex.getExternalContext().redirect( "./jugar.daw?id=" + idCat );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/* Función que redirige al usuario a la página de multijugador */
	public void multijugador() {
		FacesContext contex = FacesContext.getCurrentInstance();
        try {
			contex.getExternalContext().redirect( "../multijugador/seleccionJugadores.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Función que se encarga de activar los botones de las categorias */
	public boolean activar(Categoria cat) {
		if(cat.getIdCategoria() == 1){
			return true;
		}
		else {
			int id = cat.getIdCategoria();
			int puntMin = cat.getPuntuacionMinima();
			// Obtener y contar los puntos totales que tiene el usuario para saber si se tiene que activar el botón
			try {
				Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
				int total = 0;
				for (int i = 1; i < id; i++) {
					Puntuacion puntuacion = puntuacionEJB.puntosUsuarioCategoria(us.getIdUsuario(), i);
					if (puntuacion != null) {
						total += puntuacion.getPuntos();
					}
				}
				// Comprobación de si se debe activar el boton
				if(total >= puntMin) {
					return true;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	/* Función que se encarga de saber los puntos que tiene el usuario */
	public String puntosAcumulados() {
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		int idUsuario = us.getIdUsuario();
		int sigCat = 0;
		int total = 0;
		
		for(int i = 1; i <= 6; i++) {
			Puntuacion puntos = puntuacionEJB.puntosUsuarioCategoria(idUsuario, i);
			if(puntos != null) {
				total += puntos.getPuntos();
			}
			Categoria cat = categoriaEJB.buscaCategoria(i);
			if (total < cat.getPuntuacionMinima()) {
				sigCat = cat.getPuntuacionMinima();
				break;
			}
		}
		
		if(sigCat != 0) {
			estableceMsg("Puntos necesarios para la siguiente categoría: " + sigCat);
		}
		
		return ""+total;
	}
	
	/* Función que establece el mensaje que verá el usuario	*/
	public void estableceMsg(String msg) {
		FacesMessage facesMsg;
		facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
		
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
}
