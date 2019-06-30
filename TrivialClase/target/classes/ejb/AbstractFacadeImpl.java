package ejb;

import javax.persistence.EntityManager;

public abstract class AbstractFacadeImpl<T> implements AbstractFacade<T> {
	private Class<T> entityClass;
	
	protected abstract EntityManager getEm();
	
	public AbstractFacadeImpl(Class<T> entityClass) {
		this.entityClass=entityClass;
	}

	@Override
	public void create(T entity) {
		getEm().persist(entity);
	}

	@Override
	public void update(T entity) {
		getEm().merge(entity);		
	}

	@Override
	public void remove(T entity) {
		getEm().remove(getEm().merge(entity));		
	}

	@Override
	public T find(Object id) {
		return getEm().find(entityClass, id);
	}	

}
