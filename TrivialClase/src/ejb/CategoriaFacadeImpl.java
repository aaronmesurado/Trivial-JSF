package ejb;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.Categoria;

@Stateless
public class CategoriaFacadeImpl extends AbstractFacadeImpl<Categoria> implements CategoriaFacade{

	@PersistenceContext(unitName="Persistencia")
	private EntityManager em;
	
	public CategoriaFacadeImpl() {
		super(Categoria.class);
	}

	@Override
	public List<Categoria> findAll() {
		TypedQuery<Categoria> q = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
		return q.getResultList();
	}
	
	
	@Override
	public int puntuacionMinima(int id) {
		int puntuacion = -1;
		String consulta;
		try {
			consulta = "SELECT c.puntuacionMinima FROM Categoria c WHERE "
					+ "c.idCategoria = :id";
			Query query = em.createQuery(consulta);
			query.setParameter("id", id);
			
			puntuacion = (Integer) query.getSingleResult();
		}catch(Exception e) {
			
		}
		
		return puntuacion;
	}
	
	@Override
	public Categoria buscaCategoria(int id) {
		Categoria cat = null;
		String consulta;
		try {
			consulta = "SELECT c FROM Categoria c WHERE "
					+ "c.idCategoria = :id";
			Query query = em.createQuery(consulta);
			query.setParameter("id", id);
			
			cat = (Categoria) query.getSingleResult();
		}catch(Exception e) {
			
		}
		
		return cat;
	}

	@Override
	protected EntityManager getEm() {
		return em;
	}
	
	@PreDestroy
	public void destroy() {
		getEm().close();
	}

}
