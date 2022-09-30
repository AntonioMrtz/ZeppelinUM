package persistencia.jpa.dao;


import java.util.List;
import javax.persistence.EntityManager;

public interface DAO<T> {
    public T findById(Integer id);
    
    public List<T> findByIds(List<Integer> ids);

    public List<T> getAll();

    public void save(T t, EntityManager em);
}