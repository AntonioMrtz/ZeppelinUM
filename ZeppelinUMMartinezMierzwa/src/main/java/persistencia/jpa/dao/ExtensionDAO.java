package persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

public abstract class ExtensionDAO<T> implements DAO<T> {
    protected Class<T> persistedClass;
    private String name;

    public ExtensionDAO(Class<T> persistedClass) {
        this.persistedClass = persistedClass;
        name = persistedClass.getName().substring(persistedClass.getName().lastIndexOf(".") + 1);
    }   
    @Override
    public T findById(Integer id) {
        try {
            T instance = EntityManagerHelper.getEntityManager().find(persistedClass, id);
            if (instance != null) {
                EntityManagerHelper.getEntityManager().refresh(instance);
            }
            return instance;
        } catch (RuntimeException re) {
            throw re;
        }
    }   
    @Override
    public List<T> findByIds(List<Integer> ids) {

    	List<T> categorias = new ArrayList<T>();

    	for(Integer id: ids) {
    		try {
    			T instance = EntityManagerHelper.getEntityManager().find(persistedClass, id);
    			
    			if (instance != null) {
    				EntityManagerHelper.getEntityManager().refresh(instance);
    				categorias.add(instance);
    			}
    		} catch (RuntimeException re) {
    			throw re;
    		}
    		
    	}
    	
    	return categorias;
    }
    
    
    @Override
    public void save(T t, EntityManager em) {
        em.persist(t);
    }
    
	public List<T> getAll() {
		try {
			final String queryString = " SELECT model from " + name + " model ";
			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	//sejeeejej
	public void a() {
		
		
	}

}