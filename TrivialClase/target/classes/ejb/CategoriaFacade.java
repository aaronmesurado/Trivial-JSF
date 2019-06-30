package ejb;

import java.util.List;

import modelo.Categoria;

public interface CategoriaFacade extends AbstractFacade<Categoria>{
	public List<Categoria> findAll();
	public int puntuacionMinima(int id);
	public Categoria buscaCategoria(int id);
}
