package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ejb.CategoriaFacade;
import ejb.PreguntaFacade;
import modelo.Categoria;
import modelo.Pregunta;
import modelo.Usuario;

@Named
@ViewScoped
public class ControladorModificar implements Serializable{
	/* Controlador que se encarga de mostrar y recoger los datos de
		la pregunta que el administrador quiera/necesite modificar
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PreguntaFacade preguntaEJB;
	@EJB
	private CategoriaFacade categoriaEJB;
	
	private int id;
	private Pregunta pregunta;
	private List<Categoria> listaCategorias;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorModificar");
		
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
		
		id = buscarParametro(); 
		try {
			pregunta = preguntaEJB.pregunta(id); // Busca en la base de datos la pregunta a modificar
			listaCategorias = categoriaEJB.findAll(); // Busca en la base de datos las categorias existentes
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

	public CategoriaFacade getCategoriaEJB() {
		return categoriaEJB;
	}

	public void setCategoriaEJB(CategoriaFacade categoriaEJB) {
		this.categoriaEJB = categoriaEJB;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	// Fin GET y SET
	
	/* Función que busca el parámetro que se le pasa por la URL */
	private int buscarParametro() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		String variable1 = params.get("id").toString();
		int id = Integer.parseInt(variable1);
		return id;
	}
	
	/* Función que se encarga de actualizar la base de datos con los datos nuevos */
	public void guardarCambios() {
		quitarEspacios();
		boolean correcto = true;
		
		Categoria cat = new Categoria();
		try {
			cat = categoriaEJB.buscaCategoria(pregunta.getCategoria().getIdCategoria()); // Obtiene de la base de datos la categoría seleccionada
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		pregunta.setCategoria(cat);
		
		String correcta = pregunta.getCorrecta();
		String incorrecta1 = pregunta.getIncorrecta1();
		String incorrecta2 = pregunta.getIncorrecta2();
		String incorrecta3 = pregunta.getIncorrecta3();
		String incorrecta4 = pregunta.getIncorrecta4();
		
		if (pregunta.getPregunta().equals("") || correcta.equals("") || incorrecta1.equals("")
				|| incorrecta2.equals("") || incorrecta3.equals("") || incorrecta4.equals("")) {
			estableceMsg("Los campos no pueden estar vacíos");
			correcto = false;
		}
		
		// Comprobación para que la respuesta correcta sea distinta a las incorrectas
		if(correcta.equalsIgnoreCase(incorrecta1) || correcta.equalsIgnoreCase(incorrecta2) ||
				correcta.equalsIgnoreCase(incorrecta3) || correcta.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("La respuesta correcta tiene que ser diferente a las incorrectas");
			correcto = false;
		}
		// Comprobación para que la respuesta correcta no coincida con la pregunta
		if(correcta.equalsIgnoreCase(pregunta.getPregunta())) {
			estableceMsg("La respuesta correcta no puede coincidir con la pregunta");
			correcto = false;
		}
		// Comprobación para que todas las respuestas incorrectas sean distintas
		if(incorrecta1.equalsIgnoreCase(incorrecta2) || incorrecta1.equalsIgnoreCase(incorrecta3) ||
				incorrecta1.equalsIgnoreCase(incorrecta4) || incorrecta2.equalsIgnoreCase(incorrecta3) ||
				incorrecta2.equalsIgnoreCase(incorrecta4) || incorrecta3.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("Hay respuestas incorrectas que son iguales");
			correcto = false;
		}
		
		String titulo = pregunta.getPregunta();
		// Comprobación para que el título de la pregunta sea distinto a las respuestas incorrectas
		if(titulo.equalsIgnoreCase(incorrecta1) || titulo.equalsIgnoreCase(incorrecta2) ||
				titulo.equalsIgnoreCase(incorrecta3) || titulo.equalsIgnoreCase(incorrecta4)) {
			estableceMsg("El título de la pregunta coincide con alguna de las respuestas incorrectas");
			correcto = false;
		}
		
		if (correcto) {
			// Graba la pregunta en la base de datos y dirige al usuario a la página principal
			preguntaEJB.update(pregunta); // Actualiza
			
			FacesContext contex = FacesContext.getCurrentInstance();
	        try {
				contex.getExternalContext().redirect( "./preguntas.daw" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			FacesContext contex = FacesContext.getCurrentInstance();
	        try {
				contex.getExternalContext().redirect( "./modificar.daw?id="+id );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

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
	
	public void borrar() {
		preguntaEJB.remove(pregunta);
		FacesContext contex = FacesContext.getCurrentInstance();
        try {
			contex.getExternalContext().redirect( "./preguntas.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
