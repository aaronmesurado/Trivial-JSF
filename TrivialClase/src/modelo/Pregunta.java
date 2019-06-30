package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the preguntas database table.
 * 
 */
@Entity
@Table(name="preguntas")
@NamedQuery(name="Pregunta.findAll", query="SELECT p FROM Pregunta p")
public class Pregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_pregunta")
	private int idPregunta;

	private String correcta;

	private String incorrecta1;

	private String incorrecta2;

	private String incorrecta3;

	private String incorrecta4;

	private String pregunta;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	public Pregunta() {
	}

	public int getIdPregunta() {
		return this.idPregunta;
	}

	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}

	public String getCorrecta() {
		return this.correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public String getIncorrecta1() {
		return this.incorrecta1;
	}

	public void setIncorrecta1(String incorrecta1) {
		this.incorrecta1 = incorrecta1;
	}

	public String getIncorrecta2() {
		return this.incorrecta2;
	}

	public void setIncorrecta2(String incorrecta2) {
		this.incorrecta2 = incorrecta2;
	}

	public String getIncorrecta3() {
		return this.incorrecta3;
	}

	public void setIncorrecta3(String incorrecta3) {
		this.incorrecta3 = incorrecta3;
	}

	public String getIncorrecta4() {
		return this.incorrecta4;
	}

	public void setIncorrecta4(String incorrecta4) {
		this.incorrecta4 = incorrecta4;
	}

	public String getPregunta() {
		return this.pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Pregunta [idPregunta=" + idPregunta + ", correcta=" + correcta + ", incorrecta1=" + incorrecta1
				+ ", incorrecta2=" + incorrecta2 + ", incorrecta3=" + incorrecta3 + ", incorrecta4=" + incorrecta4
				+ ", pregunta=" + pregunta + ", categoria=" + categoria + "]";
	}

}