package persistencia.jpa.dao;

import persistencia.jpa.bean.Restaurante;

public class RestauranteDAO extends ExtensionDAO<Restaurante> {

    public RestauranteDAO(Class<Restaurante> persistedClass) {
        super(persistedClass);
    }

    private static RestauranteDAO restauranteDAO;

    public static RestauranteDAO getRestauranteDAO() {
        if (restauranteDAO == null)
            restauranteDAO = new RestauranteDAO(Restaurante.class);
        return restauranteDAO;
    }
}