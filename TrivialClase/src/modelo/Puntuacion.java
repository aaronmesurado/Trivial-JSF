package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the puntuacion database table.
 * 
 */
@Entity
@Table(name="puntuacion")
@NamedQuery(name="Puntuacion.findAll", query="SELECT p FROM Puntuacion p")
public class Puntuacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PuntuacionPK id;

	private int puntos;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Puntuacion() {
	}

	public PuntuacionPK getId() {
		return this.id;
	}

	public void setId(PuntuacionPK id) {
		this.id = id;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Puntuacion [id=" + id + ", puntos=" + puntos + ", categoria=" + categoria + ", usuario=" + usuario
				+ "]";
	}

}