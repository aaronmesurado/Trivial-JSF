package controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ejb.PreguntaFacade;
import ejb.CategoriaFacade;
import modelo.Categoria;
import modelo.Pregunta;

@Named
@ViewScoped
public class ControladorPreguntaAdmin implements Serializable{
	/* Controlador que servirá para que los usuarios puedan subir sus
		preguntas a la base de datos y se puedan jugar posteriormente
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PreguntaFacade preguntaEJB;
	@EJB
	private CategoriaFacade categoriaEJB;
	
	//@Inject
	private Pregunta pregunta;
	private List<Categoria> listaCategorias;
	private Categoria categoria;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorPreguntaAdmin");
		// Inicialización de las variables
		pregunta = new Pregunta();
		categoria = new Categoria();
		categoria.setIdCategoria(0); // Inicio de la categoría a 0 para que se seleccione la opcion por defecto
		listaCategorias = categoriaEJB.findAll();
	}

	// Inicio GET y SET
	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	//Fin GET y SET

	/* Función que se encarga de hacer todas las comprobaciones
		necesarias antes de grabar la pregunta en la base de datos
	*/
	public String comprobar() {
		quitarEspacios();
		// Comprobación de la categoría cuando hacemos click en el boton
		if(categoria.getIdCategoria() == 0) {
			estableceMsg("Seleccione una Categoría");
			return null;
		}
		else {
			int id = categoria.getIdCategoria();
			categoria = categoriaEJB.buscaCategoria(id);	
			pregunta.setCategoria(categoria);
		}
		
		String correcta = pregunta.getCorrecta();
		String incorrecta1 = pregunta.getIncorrecta1();
		String incorrecta2 = pregunta.getIncorrecta2();
		String incorrecta3 = pregunta.getIncorrecta3();
		String incorrecta4 = pregunta.getIncorrecta4();
		
		// Comprobación para que la respuesta correcta sea distinta a las incorrectas
		if(correcta.equalsIgnoreCase(incorrecta1) || correcta.equalsIgnoreCase(incorrecta2) ||
				correcta.equalsIgnoreCase(incorrecta3) || correcta.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("La respuesta correcta tiene que ser diferente a las incorrectas");
			return null;
		}
		// Comprobación para que la respuesta correcta no coincida con la pregunta
		if(correcta.equalsIgnoreCase(pregunta.getPregunta())) {
			estableceMsg("La respuesta correcta no puede coincidir con la pregunta");
			return null;
		}
		// Comprobación para que todas las respuestas incorrectas sean distintas
		if(incorrecta1.equalsIgnoreCase(incorrecta2) || incorrecta1.equalsIgnoreCase(incorrecta3) ||
				incorrecta1.equalsIgnoreCase(incorrecta4) || incorrecta2.equalsIgnoreCase(incorrecta3) ||
				incorrecta2.equalsIgnoreCase(incorrecta4) || incorrecta3.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("Hay respuestas incorrectas que son iguales");
			return null;
		}
		
		String titulo = pregunta.getPregunta();
		// Comprobación para que el título de la pregunta sea distinto a las respuestas incorrectas
		if(titulo.equalsIgnoreCase(incorrecta1) || titulo.equalsIgnoreCase(incorrecta2) ||
				titulo.equalsIgnoreCase(incorrecta3) || titulo.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("El título de la pregunta coincide con alguna de las respuestas incorrectas");
			return null;
		}
		
		// Graba la pregunta en la base de datos y dirige al usuario a la página principal
		preguntaEJB.create(pregunta); // Inserta
		return "preguntas";

	}
	
	/* Función que establece el mensaje que verá el usuario cuando
		se cumplan alguna de las condiciones anteriores
	*/
	public void estableceMsg(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
	
	/* Función que se encarga de quitar los espacios
		del principio y del final, si los hubiese
	*/
	public void quitarEspacios() {
		pregunta.setCorrecta(pregunta.getCorrecta().trim());
		pregunta.setIncorrecta1(pregunta.getIncorrecta1().trim());
		pregunta.setIncorrecta2(pregunta.getIncorrecta2().trim());
		pregunta.setIncorrecta3(pregunta.getIncorrecta3().trim());
		pregunta.setIncorrecta4(pregunta.getIncorrecta4().trim());
		pregunta.setPregunta(pregunta.getPregunta().trim());
	}
}
