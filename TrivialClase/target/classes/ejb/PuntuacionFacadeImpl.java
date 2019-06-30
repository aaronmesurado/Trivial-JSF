package ejb;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.Puntuacion;

@Stateless
public class PuntuacionFacadeImpl extends AbstractFacadeImpl<Puntuacion> implements PuntuacionFacade{

	@PersistenceContext(unitName="Persistencia")
	private EntityManager em;
	
	public PuntuacionFacadeImpl() {
		super(Puntuacion.class);
	}

	@Override
	public List<Puntuacion> findAll() {
		TypedQuery<Puntuacion> q = em.createQuery("SELECT p FROM Puntuacion p", Puntuacion.class);
		return q.getResultList();
	}
	
	@Override
	public List<Puntuacion> puntuacionCategoria(int idCat) {
		TypedQuery<Puntuacion> q = em.createQuery("SELECT p FROM Puntuacion p WHERE "
				+ "p.categoria.idCategoria = " + idCat, Puntuacion.class);
		return q.getResultList();
	}
	
	@Override
	public List<Puntuacion> puntuacionUsuario(int idUs) {
		TypedQuery<Puntuacion> q = em.createQuery("SELECT p FROM Puntuacion p WHERE "
				+ "p.usuario.idUsuario = " + idUs, Puntuacion.class);
		return q.getResultList();
	}
	
	@Override
	public Puntuacion puntosUsuarioCategoria(int idUs, int idCat) {
		Puntuacion pnts = null;
		String consulta;
		try {
			consulta = "SELECT p FROM Puntuacion p WHERE p.usuario.idUsuario = :idUsuario "
					+ "AND p.categoria.idCategoria = :idCategoria";
			Query query = em.createQuery(consulta);
			query.setParameter("idUsuario", idUs);
			query.setParameter("idCategoria", idCat);
			
			pnts = (Puntuacion) query.getSingleResult();
		}catch(Exception e) {
			
		}
		
		return pnts;
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
