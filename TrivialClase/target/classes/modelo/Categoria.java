package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categorias database table.
 * 
 */
@Entity
@Table(name="categorias")
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_categoria")
	private int idCategoria;

	private String categoria;

	@Column(name="puntuacion_minima")
	private int puntuacionMinima;

	//bi-directional many-to-one association to Pregunta
	@OneToMany(mappedBy="categoria")
	private List<Pregunta> preguntas;

	//bi-directional many-to-one association to Puntuacion
	@OneToMany(mappedBy="categoria")
	private List<Puntuacion> puntuacions;

	public Categoria() {
	}

	public int getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getPuntuacionMinima() {
		return this.puntuacionMinima;
	}

	public void setPuntuacionMinima(int puntuacionMinima) {
		this.puntuacionMinima = puntuacionMinima;
	}

	public List<Pregunta> getPreguntas() {
		return this.preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public Pregunta addPregunta(Pregunta pregunta) {
		getPreguntas().add(pregunta);
		pregunta.setCategoria(this);

		return pregunta;
	}

	public Pregunta removePregunta(Pregunta pregunta) {
		getPreguntas().remove(pregunta);
		pregunta.setCategoria(null);

		return pregunta;
	}

	public List<Puntuacion> getPuntuacions() {
		return this.puntuacions;
	}

	public void setPuntuacions(List<Puntuacion> puntuacions) {
		this.puntuacions = puntuacions;
	}

	public Puntuacion addPuntuacion(Puntuacion puntuacion) {
		getPuntuacions().add(puntuacion);
		puntuacion.setCategoria(this);

		return puntuacion;
	}

	public Puntuacion removePuntuacion(Puntuacion puntuacion) {
		getPuntuacions().remove(puntuacion);
		puntuacion.setCategoria(null);

		return puntuacion;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", categoria=" + categoria + ", puntuacionMinima="
				+ puntuacionMinima + "]";
	}
	
	

}