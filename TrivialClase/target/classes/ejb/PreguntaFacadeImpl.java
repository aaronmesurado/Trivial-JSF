package ejb;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.Pregunta;

@Stateless
public class PreguntaFacadeImpl extends AbstractFacadeImpl<Pregunta> implements PreguntaFacade{

	@PersistenceContext(unitName="Persistencia")
	private EntityManager em;
	
	public PreguntaFacadeImpl() {
		super(Pregunta.class);
	}

	@Override
	public List<Pregunta> findAll() {
		TypedQuery<Pregunta> q = em.createQuery("SELECT p FROM Pregunta p", Pregunta.class);
		return q.getResultList();
	}
	
	@Override
	public List<Pregunta> preguntasCategoria(int id) {
		TypedQuery<Pregunta> q = em.createQuery("SELECT p FROM Pregunta p WHERE "
				+ "p.categoria.idCategoria = " + id, Pregunta.class);
		return q.getResultList();
	}
	
	@Override
	public Pregunta pregunta(int id) {
		Pregunta preg = null;
		String consulta;
		try {
			consulta = "SELECT p FROM Pregunta p WHERE p.idPregunta = :id";
			Query query = em.createQuery(consulta);
			query.setParameter("id", id);
			
			preg = (Pregunta) query.getSingleResult();
		}catch(Exception e) {
			
		}
		
		return preg;
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
