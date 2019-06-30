package ejb;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.Usuario;

@Stateless
public class UsuarioFacadeImpl extends AbstractFacadeImpl<Usuario> implements UsuarioFacade{

	@PersistenceContext(unitName="Persistencia")
	private EntityManager em;
	
	public UsuarioFacadeImpl() {
		super(Usuario.class);
	}

	@Override
	public List<Usuario> findAll() {
		TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		return q.getResultList();
	}
	
	@Override
	public Usuario usuarioPorID(int idUsuario) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario";
			Query query = em.createQuery(consulta);
			query.setParameter("idUsuario", idUsuario);
			
			usuario = (Usuario) query.getSingleResult();
		}catch(Exception e) {
			
		}
		return usuario;
	}
	
	@Override
	public Usuario usuarioPorNombre(String nombre) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "SELECT u FROM Usuario u WHERE u.usuario = :nombre";
			Query query = em.createQuery(consulta);
			query.setParameter("nombre", nombre);
			
			usuario = (Usuario) query.getSingleResult();
		}catch(Exception e) {
			
		}
		return usuario;
	}
	
	@Override
	public String contrasena(String nombre) {
		String contrasena = null;
		String consulta;
		try {
			consulta = "SELECT u.contrasena FROM Usuario u WHERE u.usuario = :nombre";
			Query query = em.createQuery(consulta);
			query.setParameter("nombre", nombre);
			
			contrasena = query.getSingleResult().toString();
		}catch(Exception e) {
			
		}
		return contrasena;
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