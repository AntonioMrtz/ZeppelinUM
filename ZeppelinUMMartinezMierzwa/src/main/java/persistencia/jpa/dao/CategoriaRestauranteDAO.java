package persistencia.jpa.dao;

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
}

