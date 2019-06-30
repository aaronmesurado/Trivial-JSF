package modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the puntuacion database table.
 * 
 */
@Embeddable
public class PuntuacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_usuario", insertable=false, updatable=false)
	private int idUsuario;

	@Column(name="id_categoria", insertable=false, updatable=false)
	private int idCategoria;

	public PuntuacionPK() {
	}
	public int getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdCategoria() {
		return this.idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PuntuacionPK)) {
			return false;
		}
		PuntuacionPK castOther = (PuntuacionPK)other;
		return 
			(this.idUsuario == castOther.idUsuario)
			&& (this.idCategoria == castOther.idCategoria);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario;
		hash = hash * prime + this.idCategoria;
		
		return hash;
	}
}