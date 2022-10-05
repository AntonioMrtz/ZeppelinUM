package persistencia.jpa.dao;

import persistencia.jpa.bean.Incidencia;

public class IncidenciaDAO extends ExtensionDAO<Incidencia>{

	public IncidenciaDAO(Class<Incidencia> persistedClass) {
		super(persistedClass);
	}
	
    private static IncidenciaDAO incidenciaDAO;

    public static IncidenciaDAO getIncidenciaDAO() {
        if (incidenciaDAO == null)
        	incidenciaDAO = new IncidenciaDAO(Incidencia.class);
        return incidenciaDAO;
    }
}
