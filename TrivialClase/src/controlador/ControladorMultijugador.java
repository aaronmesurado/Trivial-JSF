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

import ejb.PreguntaFacade;
import ejb.PuntuacionFacade;
import modelo.Jugador;
import modelo.Pregunta;


@Named
@ViewScoped
public class ControladorMultijugador implements Serializable{
	/* Controlador que se encarga de saber quien es el jugador que tiene el turno activo,
		las categorías que están acertadas, cargar la pregunta que se tiene que mostrar...
	*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PreguntaFacade preguntaEJB;
	@EJB
	private PuntuacionFacade puntuacionEJB;
	
	private List<Pregunta> ciencias;
	private List<Pregunta> deportes;
	private List<Pregunta> espectaculo;
	private List<Pregunta> historia;
	private List<Pregunta> geografia;
	private List<Pregunta> literatura;
	private List<Jugador> jugadores;
	private int activo;
	private int categoria;
	private Pregunta pregunta;
	private ArrayList<String> incorrectas = new ArrayList<>();
	private String incorrecta1;
	private String incorrecta2;
	private ArrayList<String> respuestas = new ArrayList<>();
	private ArrayList<Integer> anadido = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		System.out.println("Inicio ControladorMultijugador");
		// Obtiene los jugadores que se han creado
		jugadores = (List<Jugador>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("jugadores");
		
		// Comprobación de si la lista está vacía para redirección
		if(jugadores == null || jugadores.isEmpty()) {
			FacesContext contex = FacesContext.getCurrentInstance();
			try {
				contex.getExternalContext().redirect( "./seleccionJugadores.daw" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Obtiene el jugador que está activo
		activo = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("jugActivo");
		
		// Obtiene las listas de las preguntas si están cargadas
		ciencias = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ciencias");
		deportes = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("deportes");
		espectaculo = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("espectaculo");
		historia = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("historia");
		geografia= (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("geografia");
		literatura = (List<Pregunta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("literatura");
		
		cargarListas();
		// Obtiene la pregunta que se ha almacenado en una variable de sesion
		pregunta = (Pregunta) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pregunta");
		// Comprobación de se la pregunta existe o no
		if(pregunta != null) {
			anadirIncorrectas();
			buscarIncorrectas();
			crearArrayRespuestas();
		}
	}
	
	/* Función que se encarga de seleccionar la pregunta según la
		categoría que se ha seleccionado de forma aleatoria
	*/
	private void obtenerPregunta() {
		int pos;
		if(categoria == 1) {
			pos = numAleatorio(0, ciencias.size());
			pregunta = ciencias.get(pos);
			ciencias.remove(pos);
		}
		else if(categoria == 2) {
			pos = numAleatorio(0, deportes.size());
			pregunta = deportes.get(pos);
			deportes.remove(pos);
		}
		else if(categoria == 3) {
			pos = numAleatorio(0, espectaculo.size());
			pregunta = espectaculo.get(pos);
			espectaculo.remove(pos);
		}
		else if(categoria == 4) {
			pos = numAleatorio(0, historia.size());
			pregunta = historia.get(pos);
			historia.remove(pos);
		}
		else if(categoria == 5) {
			pos = numAleatorio(0, geografia.size());
			pregunta = geografia.get(pos);
			geografia.remove(pos);
		}
		else if(categoria == 6) {
			pos = numAleatorio(0, literatura.size());
			pregunta = literatura.get(pos);
			literatura.remove(pos);
		}
		// Almacena la pregunta en una variable de sesión
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pregunta", pregunta);
		
		FacesContext contex = FacesContext.getCurrentInstance();
		try {
			contex.getExternalContext().redirect( "./jugar.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	/* Función que se encarga de obtener las preguntas de la base de datos, según la categoría */
	private void cargarListas() {
		for(int i = 1; i <= 6; i++) {
			if(i == 1 && (ciencias == null || ciencias.isEmpty())) { // Comprobación de si la lista existe o no
				ciencias = preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ciencias", ciencias);
			}
			else if(i == 2 && (deportes == null || deportes.isEmpty())) { // Comprobación de si la lista existe o no
				deportes = preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deportes", deportes);
			}
			else if (i == 3 && (espectaculo == null || espectaculo.isEmpty())){ // Comprobación de si la lista existe o no
				espectaculo = preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("espectaculo", espectaculo);
			}
			else if(i == 4 && (historia == null || historia.isEmpty())) { // Comprobación de si la lista existe o no
				historia= preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("historia", historia);
			}
			else if(i == 5 && (geografia == null || geografia.isEmpty())) { // Comprobación de si la lista existe o no
				geografia = preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("geografia", geografia);
			}
			else if (i == 6 && (literatura == null || literatura.isEmpty())){ // Comprobación de si la lista existe o no
				literatura = preguntaEJB.preguntasCategoria(i);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("literatura", literatura);
			}
		}
	}
	
	/* Función que obtiene la categoría de la pregunta de forma
		aleatoria siempre que no haya una pregunta establecida
	*/
	public void tirar() {
		
		if(pregunta == null) {
			categoria = numAleatorio(1, 6);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoria", categoria);
			
			obtenerPregunta();
		}
	}
	
	/* Función que se encarga de renderiar la imagen que se le muestra al usuario */
	public boolean cargaImagen(int idCategoria) {

		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoria") == null) {
			categoria = 0;
		}
		else {
			categoria = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoria");
		}
		
		if(categoria == idCategoria) {
			return true;
		}
		return false;
	}
	
	/* Función que comprueba si la respues que ha seleccionado el usuario es la
		correcta (la marca como acertada) o no (la marca como fallada y pasa turno)
	*/
	public void seleccionaRespuesta(int param) {
		categoria = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoria"); // Obtiene la categoria
				
		String resp = respuestas.get(param);
		// Comprobación de si la respuesta seleccionada es la correcta o no
		if(resp.equals(pregunta.getCorrecta())) {
			jugadores.get(activo).cambiaValor(categoria, true); // Cambia el valor de la categoria para el jugador
			comprobarGanador();
		}
		else {
			jugadores.get(activo).cambiaValor(categoria, false); // Cambia el valor de la categoria para el jugador
			pasarTurno(activo);
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jugadores", jugadores);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pregunta", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoria", null);
		FacesContext contex = FacesContext.getCurrentInstance();
		try {
			contex.getExternalContext().redirect( "./jugar.daw" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* Función que se encarga de cambiar el turno si el jugador activo falla la pregunta */
	private void pasarTurno(int act) {
		jugadores.get(act).setTurno(false); // Quita el turno al jugador
		
		// Comprobación de si el siguiente es mayor que el número de jugadores
		if(act + 1 == jugadores.size()) {
			act = 0;
		}
		else {
			act++;
		}
		
		jugadores.get(act).setTurno(true);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jugActivo", act);
	}
	
	/* Función que se encarga de saber si el jugador que acaba de responder
		correctamente gana la partida o no
	*/
	private void comprobarGanador() {
		boolean[] acertados = jugadores.get(activo).getAcertados();
		int contador = 0;
		for (int i = 0; i < acertados.length; i++) {
			if(acertados[i] == true) {
				contador++;
			}
		}
		
		if(contador == 6) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ganador", jugadores.get(activo));
			FacesContext contex = FacesContext.getCurrentInstance();
			try {
				contex.getExternalContext().redirect( "./ganador.daw" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/* Función que se encarga de generar un número aletorio
		entre los parámetros que se le pasa y lo devuelve
	*/
	private int numAleatorio(int min, int max) {
		int aleat = (int)  Math.floor(Math.random()*max + min);
		return aleat;
	}
	
	// Inicio GET y SET
	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public ArrayList<String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(ArrayList<String> respuestas) {
		this.respuestas = respuestas;
	}
	// Fin GET y SET
		
}