package controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
import javax.inject.Named;

import ejb.UsuarioFacade;
import modelo.Usuario;

import criptografia.*;

@Named
@ViewScoped
public class ControladorRegistro implements Serializable{
	/* Controlador que se encarga de saber si el usuario que intenta registrarse
		existe ya en la base de datos o si la contraseña esá bien escrita
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioFacade usuarioEJB;
	
	private Usuario usuario;
	private String repContrasena;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorRegistro");
		usuario = new Usuario();
	}
	
	// Inicio GET y SET
	public String getRepContrasena() {
		return repContrasena;
	}

	public void setRepContrasena(String repContrasena) {
		this.repContrasena = repContrasena;
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
	// Fin GET y SET
	
	/* Función que se encarga de registrar al usuario */
	public String registrar() {
		usuario.setUsuario(usuario.getUsuario().trim()); // Elimina los espacios del principio y del final
		usuario.setContrasena(usuario.getContrasena().trim()); // Elimina los espacios del principio y del final
		repContrasena = repContrasena.trim(); // Elimina los espacios del principio y del final
		
		// Evita que se registren usuarios con el nombre 'admin' o 'administrador'
		String nombre = usuario.getUsuario();
		if(nombre.equals("administrador") || nombre.equals("Administrador")) {
			estableceMsg("El usuario ya existe en la base de datos"); // Mensaje que se le muestra al usuario
			return null;
		}
		
		// Comprobación de los la contraseña está bien escrita
		if(!repContrasena.equals(usuario.getContrasena())) {
			estableceMsg("Las contraseñas no son iguales");
			return null;
		}
		else {	
			try {
				Usuario usuarioBD = usuarioEJB.usuarioPorNombre(usuario.getUsuario()); // Busca el nombre de usuario en la base de datos
				if(usuarioBD == null) {
					// Encripta la contraseña
					String encriptado = EncriptDescript.encrypt(usuario.getContrasena());
					usuario.setContrasena(encriptado); // Establece la contraseña
					usuarioEJB.create(usuario); // Inserta
					return "index";
				}
				estableceMsg("El usuario ya existe en la base de datos"); // Mensaje que se le muestra al usuario
				return null;
				
			}catch(Exception e) {
				estableceMsg("Error con la base de datos"); // Mensaje que se le muestra al usuario
				return null;
			}
		}
		
	}
	
	/* Función que establece el mensaje que verá el usuario	*/
	public void estableceMsg(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
}
