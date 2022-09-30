package persistencia.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

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
    public List<T> findByIds(List<Integer> id) {
        return null;
        // TODO Auto-generated method stub
    }
    @Override
    public void save(T t, EntityManager em) {
        em.persist(t);
    }

    @Override
    public List<T> getAll() {
        return null;
        // TODO Auto-generated method stub
    }
}