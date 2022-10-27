package persistencia.jpa.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import persistencia.dto.IncidenciaDTO;
import persistencia.dto.UsuarioDTO;
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
    
	public List<IncidenciaDTO> transformarToDTO(List<Incidencia> incidencias) {
		List<IncidenciaDTO> rs = new ArrayList<IncidenciaDTO>();
		for (Incidencia i : incidencias) {
			rs.add(new IncidenciaDTO(i.getId(),i.getFechaCreacion(),i.getDescripcion(),i.getFechaCierre(),i.getComentarioCierre()));
		}
		return rs;
	}
    
    
	public List<UsuarioDTO> findIncidenceByUser(String usuario,String apellidos) {
		
		List<UsuarioDTO> user_list =UsuarioDAO.getUsuarioDAO().findByName(usuario, apellidos);
		
		if (user_list==null) {
			
			return null;
		}
		
		Integer user_id=user_list.indexOf(0);
		
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidenccia.findIncidenceByUser");
			query.setParameter("usuario",usuario);
			
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UsuarioDTO> findIncidenciaByUser(Integer usuario) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidenccia.findIncidenceByUser");
			query.setParameter("usuario",usuario);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UsuarioDTO> findIncidenciaNotClosed(Integer usuario) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidenccia.findIncidenciaNotClosed");
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	

}
