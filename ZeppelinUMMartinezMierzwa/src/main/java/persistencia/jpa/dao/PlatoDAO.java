package persistencia.jpa.dao;

import persistencia.jpa.bean.Plato;

public class PlatoDAO extends ExtensionDAO<Plato> {

    public PlatoDAO(Class<Plato> persistedClass) {
        super(persistedClass);
    }

    private static PlatoDAO platoDAO;

    public static PlatoDAO getPlatoDAO() {
        if (platoDAO == null)
            platoDAO = new PlatoDAO(Plato.class);
        return platoDAO;
    }
}
