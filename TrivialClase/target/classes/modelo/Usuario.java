package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario")
	private int idUsuario;

	private String contrasena;

	private String usuario;

	//bi-directional many-to-one association to Puntuacion
	@OneToMany(mappedBy="usuario")
	private List<Puntuacion> puntuacions;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Puntuacion> getPuntuacions() {
		return this.puntuacions;
	}

	public void setPuntuacions(List<Puntuacion> puntuacions) {
		this.puntuacions = puntuacions;
	}

	public Puntuacion addPuntuacion(Puntuacion puntuacion) {
		getPuntuacions().add(puntuacion);
		puntuacion.setUsuario(this);

		return puntuacion;
	}

	public Puntuacion removePuntuacion(Puntuacion puntuacion) {
		getPuntuacions().remove(puntuacion);
		puntuacion.setUsuario(null);

		return puntuacion;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", contrasena=" + contrasena + ", usuario=" + usuario + "]";
	}

}