package controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import criptografia.*;
import ejb.UsuarioFacade;
import modelo.Usuario;

@Named
@SessionScoped
public class ControladorLogin implements Serializable{
	/* Controlador que se encarga de buscar al usuario que intenta
		hacer login en la aplicación y establecerle una sesión
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioFacade usuarioEJB;
	
	private Usuario usuario;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorLogin");
		usuario = new Usuario();
	}
	
	// Inicio GET y SET
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
	// Fin GET y SET

	/* Función que busca al usuario que intenta hacer login en la base de datos */
	public void comprobar() {
		try {
			Usuario usuarioBD = usuarioEJB.usuarioPorNombre(usuario.getUsuario()); // Busca al usuario por nombre en la base de datos
			if(usuarioBD != null) {
				// Encripta la contraseña
				String encriptado = EncriptDescript.encrypt(usuario.getContrasena());
				// Comprobación de se la contraseña que se acaba de encriptar es igual que la del usuario que se ha obtenido de la base de datos
				if(usuarioBD.getContrasena().equals(encriptado)){
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioBD); // Creación de la variable de sesion
					FacesContext contex = FacesContext.getCurrentInstance();
					// Comprobación de si el usuario es o no administrador para redireccionar
					if(usuarioBD.getUsuario().equals("admin") || usuarioBD.getUsuario().equals("administrador")) {
						contex.getExternalContext().redirect( "gestion/preguntas.daw" );
					}else {
						contex.getExternalContext().redirect( "solitario/seleccionCategoria.daw" );
					}
				}
				else {
					estableceMsg("Usuario y/o contraseña incorrecto"); // Mensaje que se le muestra al usuario
				}
			}
			else {
				estableceMsg("Usuario y/o contraseña incorrecto"); // Mensaje que se le muestra al usuario
			}
			
		}catch(Exception e) {
			estableceMsg("Fallo al conectar con la base de datos"); // Mensaje que se le muestra al usuario
		}
	}
	
	/* Función que establece el mensaje que verá el usuario	*/
	public void estableceMsg(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
}
