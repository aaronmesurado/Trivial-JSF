package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
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
import ejb.PuntuacionFacade;
import modelo.Categoria;
import modelo.Pregunta;
import modelo.Puntuacion;
import modelo.Usuario;


@Named
@ViewScoped
public class ControladorJugar implements Serializable{
	/* Controlador que sirve para obtener las preguntas de la base de datos
		según la categoría que haya seleccionado el usuario
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PreguntaFacade preguntaEJB;
	@EJB
	private PuntuacionFacade puntuacionEJB;
	@EJB
	private CategoriaFacade categoriaEJB;
		
	private List<Pregunta> preguntas;
	private String nombreUsuario;
	private Pregunta pregunta;
	private ArrayList<String> incorrectas = new ArrayList<>();
	private String incorrecta1;
	private String incorrecta2;
	private int puntos;
	private int vidas;
	private ArrayList<String> respuestas = new ArrayList<>();
	private ArrayList<Integer> anadido = new ArrayList<>();
	private int id;
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorJugar");
		
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
		
		cargarCabecera();
		
		id = buscarParametro();
		// Obtiene de la variable de sesión las preguntas que se hayan guardado
		preguntas = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("preguntas");
		
		preguntasRespuestas(preguntas);
	}
	
	// Inicio GET y SET
	public ArrayList<String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(ArrayList<String> respuestas) {
		this.respuestas = respuestas;
	}
	
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	public String getIncorrecta1() {
		return incorrecta1;
	}

	public void setIncorrecta1(String incorrecta1) {
		this.incorrecta1 = incorrecta1;
	}

	public String getIncorrecta2() {
		return incorrecta2;
	}

	public void setIncorrecta2(String incorrecta2) {
		this.incorrecta2 = incorrecta2;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public PreguntaFacade getPreguntaEJB() {
		return preguntaEJB;
	}

	public void setPreguntaEJB(PreguntaFacade preguntaEJB) {
		this.preguntaEJB = preguntaEJB;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	// Fin GET y SET
	
	/* Función que se encarga de gestionar las preguntas y sus respuestas */
	private void preguntasRespuestas(List<Pregunta> preg) {
		try {
			// Comprobación de que la lista exista
			if(preg == null) {
				preg = preguntaEJB.preguntasCategoria(id); // Obtiene las preguntas de la categoría que se seleccionó
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("preguntas", preg); // Almacena las preguntas en una variable de sesion
			}
			// Comprobación de que la lista está vacía
			if(preg.isEmpty()) {
				FacesContext contex = FacesContext.getCurrentInstance();
				almacenarDatos();
				contex.getExternalContext().redirect( "./seleccionCategoria.daw" );
			}
			else {
				// Buscar pregunta a mostrar
				int pos = numAleatorio(0, preg.size());
				pregunta = preg.get(pos);
				// Elimino la pregunta del array para que no se repita
				preg.remove(pos);
				// Aviso al usuario de que la pregunta que se muestra es la última
				if(preg.size() == 0) {
					estableceMsg("Esta es la última pregunta de la categoría");
				}
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("preguntas", preg);
				
				anadirIncorrectas();
				buscarIncorrectas();

				crearArrayRespuestas();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Función que crea el array final de respuestas que se le mostrará al usuario */
	private void crearArrayRespuestas() {
		int aleat = numAleatorio(1, 9);
		anadido.add(aleat);
		// do...while para que se repita mientras que las respuestas añadidas no sean 3
		do {
			if(aleat >= 1 && aleat <= 3) {
				if(!respuestas.contains(incorrecta1)) {
					respuestas.add(incorrecta1);
				}
			}else if(aleat >= 4 && aleat <= 6) {
				if(!respuestas.contains(pregunta.getCorrecta())) {
					respuestas.add(pregunta.getCorrecta());
				}
			}else {
				if(!respuestas.contains(incorrecta2)) {
					respuestas.add(incorrecta2);
				}
			}
			// do...while para que el no se repita la respuesta
			do {
				aleat = numAleatorio(1, 9);
			}while(anadido.contains(aleat));
			anadido.add(aleat);
		}while(respuestas.size() != 3);
	}
	
	/* Función que se encarga de obtener los valores necesarios para la cabecera de la página */
	private void cargarCabecera() {
		usuarioLogueado();
		puntos = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("puntos");
		vidas = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vidas");
	}
	
	/* Función que se encarga de devolver el usuario logeado a la página para mostrarlo */
	private void usuarioLogueado() {
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		nombreUsuario = us.getUsuario();
	}
	/* Función que busca el parámetro que se le pasa por la URL */
	private int buscarParametro() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		String variable1 = params.get("id").toString();
		int id = Integer.parseInt(variable1);
		return id;
	}
	
	/* Función que se encargar de insertar las respuestas incorrectas
		de la pregunta seleccionada a un array
	*/
	private void anadirIncorrectas() {
		incorrectas.add(pregunta.getIncorrecta1());
		incorrectas.add(pregunta.getIncorrecta2());
		incorrectas.add(pregunta.getIncorrecta3());
		incorrectas.add(pregunta.getIncorrecta4());
	}
	
	/* Función que selecciona 2 respuestas incorrectas de forma aleatoria
		del array de respuestas incorrectas que se llenó anteriormente
	*/
	private void buscarIncorrectas() {
		int pos2 = numAleatorio(0, incorrectas.size());
		int aux;
		incorrecta1 = incorrectas.get(pos2);
		do {
			aux = numAleatorio(0, incorrectas.size());
		}while(aux == pos2);
		incorrecta2 = incorrectas.get(aux);
	}
	
	/* Función que se encarga de generar un número aletorio
		entre los parámetros que se le pasa y lo devuelve
	*/
	private int numAleatorio(int min, int max) {
		int aleat = (int)  Math.floor(Math.random()*max + min);
		return aleat;
	}
	
	/* Función que se encarga de saber si la respuesta que el usuario
		selecciona es la correcta (suma puntos) o no (resta vida)
	*/
	public void seleccionaRespuesta(int param) {
		FacesContext contex = FacesContext.getCurrentInstance();
		String resp = respuestas.get(param);
		// Comprobación de se la respuesta seleccionada es la correcta
		if(resp.equals(pregunta.getCorrecta())) {
			puntos += 10;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("puntos", puntos);
		}
		else {
			// La respuesta seleccionada no es la correcta
			vidas -= 1;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vidas", vidas);
		}
        try {
        	// Comprobación de si el usuario aún tiene vidas para seguir jugando
        	if(vidas == -1) {
        		almacenarDatos();
        		contex.getExternalContext().redirect( "./seleccionCategoria.daw" );
        	}
        	else {
        		contex.getExternalContext().redirect( "./jugar.daw?id="+id );
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Función que se encarga de almacenar la puntuación obtenida en la base de datos */
	public void almacenarDatos() {
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		Categoria cat = categoriaEJB.buscaCategoria(id); 
		
		Puntuacion puntuacion = new Puntuacion();
		
		puntuacion.setCategoria(cat);
		puntuacion.setUsuario(us);
		puntuacion.setPuntos(puntos);
		
		// Comprobación de si existe en la base de datos un usuario con puntuación en esa categoría
		Puntuacion pt = puntuacionEJB.puntosUsuarioCategoria(us.getIdUsuario(), cat.getIdCategoria());
		
		if(pt == null) {
			puntuacionEJB.create(puntuacion); // Inserta
		}else {
			puntuacionEJB.update(puntuacion); // Actualiza
		}
		
	}
	
	/* Función que establece el mensaje que verá el usuario	*/
	public void estableceMsg(String msg) {
		FacesMessage facesMsg;
		facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
		
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
	
}