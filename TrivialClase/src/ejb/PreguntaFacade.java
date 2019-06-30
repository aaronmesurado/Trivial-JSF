package ejb;

import java.util.List;

import modelo.Pregunta;

public interface PreguntaFacade extends AbstractFacade<Pregunta>{
	public List<Pregunta> findAll();
	public List<Pregunta> preguntasCategoria(int id);
	public Pregunta pregunta(int id);
}