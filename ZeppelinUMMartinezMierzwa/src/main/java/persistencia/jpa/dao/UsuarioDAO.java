package persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;

public class UsuarioDAO extends ExtensionDAO<Usuario> {

    public UsuarioDAO(Class<Usuario> persistedClass) {
        super(persistedClass);
    }

    private static UsuarioDAO usuarioDAO;

    public static UsuarioDAO getUsuarioDAO() {
        if (usuarioDAO == null)
            usuarioDAO = new UsuarioDAO(Usuario.class);
        return usuarioDAO;
    }

    
    
	public List<UsuarioDTO> findByEmail(String email) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findByEmail");
			query.setParameter("email", email);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UsuarioDTO> getAllUsers() {
		try {
			List<Usuario> usuList = getAll();
			return transformarToDTO(usuList);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<UsuarioDTO> findByEmailClave(String email, String clave) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findByEmailClave");
			query.setParameter("email", email);
			query.setParameter("clave", clave);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UsuarioDTO> findByName(String nombre, String apellidos) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findByName");
			query.setParameter("nombre", nombre);
			query.setParameter("apellidos", apellidos);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<UsuarioDTO> transformarToDTO(List<Usuario> usuarios) {
		List<UsuarioDTO> users = new ArrayList<UsuarioDTO>();
		for (Usuario u : usuarios) {
			users.add(new UsuarioDTO(u.getId(), u.getNombre(), u.getApellidos(), u.getFechaNacimiento(), u.getTipo(), u.isValidado()));
		}
		return users;
	}
	

	public List<UsuarioDTO> findNonValidatedRestauranteUsers() {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findNonValidatedRestauranteUsers");
			query.setParameter("restaurante", TipoUsuario.RESTAURANTE);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}



	
	
	
	
	
	
}