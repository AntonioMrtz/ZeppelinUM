package zeppelinum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import persistencia.dto.OpinionDTO;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.EntityManagerHelper;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import persistencia.mongo.bean.Opinion;
import persistencia.mongo.dao.OpinionDAO;

public class ServicioGestionPedido {
    
    private static ServicioGestionPedido servicio;

    public static ServicioGestionPedido getServicioGestionPedido() {
        if (servicio == null) {
            servicio = new ServicioGestionPedido();
        }
        return servicio;
    }   
    
    
	

	public boolean opinar(Integer usuario, Integer restaurante, String comentario, Double valoracion) {
	    OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();

	    Opinion o = new Opinion();
	    o.setOpinion(comentario);
	    o.setRestaurante(restaurante);
	    o.setUsuario(usuario);
	    o.setValor(valoracion);

	    ObjectId id = opinionDAO.save(o);
	    if (id != null) {
	        // si se ha creado tengo que modificar la nota del restaurante
	        EntityManager em = EntityManagerHelper.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
	            Integer numeroValoraciones = r.getNumValoraciones();                
	            Integer nuevoNumValoraciones = numeroValoraciones + 1;
	            r.setNumValoraciones(nuevoNumValoraciones);
	            if(r.getNumValoraciones() == 0) {                   
	                r.setValoracionGlobal(valoracion);
	            }
	            else {
	                Double nota = r.getValoracionGlobal();
	                double nuevaGlobal = ((nota * numeroValoraciones) + valoracion) / nuevoNumValoraciones;
	                r.setValoracionGlobal(nuevaGlobal);
	            }               
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            em.close();
	        }
	        return true;
	    } else
	        return false;
	}

	public List<OpinionDTO> findByUsuario(Integer usuario) {
	    OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
	    List<Opinion> opiniones = opinionDAO.findByUsuario(usuario);
	    List<OpinionDTO> opinionesDTO = new ArrayList<>();

	    for (Opinion o : opiniones) {
	        Restaurante r = RestauranteDAO.getRestauranteDAO().findById(o.getRestaurante());
	        OpinionDTO opinionDTO = new OpinionDTO();
	        opinionDTO.setNombreRestaurante(r.getNombre());
	        opinionDTO.setValoracion(o.getValor());
	        opinionDTO.setComentario(o.getOpinion());
	        opinionesDTO.add(opinionDTO);
	    }
	    return opinionesDTO;
	}

	public List<OpinionDTO> findByRestaurante(Integer restaurante) {
	    OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
	    List<Opinion> opiniones = opinionDAO.findByRestaurante(restaurante);
	    List<OpinionDTO> opinionesDTO = new ArrayList<>();

	    for (Opinion o : opiniones) {
	        Usuario u = UsuarioDAO.getUsuarioDAO().findById(o.getUsuario());
	        OpinionDTO opinionDTO = new OpinionDTO();
	        opinionDTO.setNombreUsuario(u.getNombre());
	        opinionDTO.setValoracion(o.getValor());
	        opinionDTO.setComentario(o.getOpinion());
	        opinionesDTO.add(opinionDTO);
	    }
	    return opinionesDTO;
	}

}