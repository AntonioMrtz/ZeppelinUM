package persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import persistencia.dto.CategoriaRestauranteDTO;
import persistencia.jpa.bean.CategoriaRestaurante;


public class CategoriaRestauranteDAO extends ExtensionDAO<CategoriaRestaurante>{

	public CategoriaRestauranteDAO(Class<CategoriaRestaurante> persistedClass) {
		super(persistedClass);
			}

    private static CategoriaRestauranteDAO categoriaRestauranteDAO;

    public static CategoriaRestauranteDAO getCategoriaRestauranteDAO() {
        if (categoriaRestauranteDAO == null)
        	categoriaRestauranteDAO = new CategoriaRestauranteDAO(CategoriaRestaurante.class);
        return categoriaRestauranteDAO;
    }
    
	public List<CategoriaRestauranteDTO> transformarToDTO(List<CategoriaRestaurante> categorias) {
		List<CategoriaRestauranteDTO> rs = new ArrayList<CategoriaRestauranteDTO>();
		for (CategoriaRestaurante c : categorias) {
			rs.add(new CategoriaRestauranteDTO(c.getId(),c.getCategoria()));
		}
		return rs;
	}
    
	public List<CategoriaRestauranteDTO> findByName(String categoria) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("CategoriaRestaurante.findByName");
			query.setParameter("categoria", categoria);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}


}

