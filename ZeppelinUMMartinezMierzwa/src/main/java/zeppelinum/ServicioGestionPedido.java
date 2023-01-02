package zeppelinum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import persistencia.dto.EstadoPedidoDTO;
import persistencia.dto.ItemPedidoDTO;
import persistencia.dto.OpinionDTO;
import persistencia.dto.PedidoDTO;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.EntityManagerHelper;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import persistencia.mongo.bean.EstadoPedido;
import persistencia.mongo.bean.ItemPedido;
import persistencia.mongo.bean.Opinion;
import persistencia.mongo.bean.Pedido;
import persistencia.mongo.dao.DireccionDAO;
import persistencia.mongo.dao.OpinionDAO;
import persistencia.mongo.dao.PedidoDAO;
import web.InitialContextUtil;

public class ServicioGestionPedido {
    
    private static ServicioGestionPedido servicio;

    private static ZeppelinUMRemoto zeppelinumRemoto;


    public static ServicioGestionPedido getServicioGestionPedido() {
        if (servicio == null) {
            try {
                zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup("ejb:AADD2022/ZeppelinUMMartinezMierzwaEJB/ZeppelinUMRemoto!zeppelinum.ZeppelinUMRemoto");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            servicio = new ServicioGestionPedido();
        }
        return servicio;
    }
    
    public void crearPedido(String id) {
        //se crea un pedido, este método deberá tener los atributos necesarios
        //una vez creado, nos quedamos con el id que le ha generado mongodb y con eso activamos el tiemr
        zeppelinumRemoto.pedidoIniciado(id);
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
	
	
	
	public ObjectId crearPedido(LocalDateTime fechaHora,String comentarios , double importe,String direccion,Integer restaurante,Integer repartidor,Integer cliente,List<EstadoPedido> est,List<ItemPedido> items) {

		PedidoDAO pedidoDAO = PedidoDAO.getPedidoDAO();
		
		Pedido p = new Pedido(fechaHora,comentarios,importe,direccion,restaurante,repartidor,cliente,est,items);

		
	    ObjectId id = pedidoDAO.save(p);
	    
	    if (id != null) {
	        EntityManager em = EntityManagerHelper.getEntityManager();
	        try {
	            em.getTransaction().begin();

	            
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        } finally {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            em.close();
	        }
	        return id;
	    } else
	        return null;
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
	
	
	
	
	public  List<PedidoDTO> findPedidoByRestaurante(Integer restaurante) {
		
		List<Pedido> pedidos = PedidoDAO.getPedidoDAO().findPedidoByRestaurant(restaurante);

		List<PedidoDTO> pedidosDTO = new ArrayList<>();

		for (Pedido p : pedidos) {
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(p.getCliente());
			Usuario rep = UsuarioDAO.getUsuarioDAO().findById(p.getRepartidor());
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(p.getRestaurante());

			PedidoDTO pedidoDTO = new PedidoDTO();
			pedidoDTO.setCliente(u.getNombre());
			pedidoDTO.setImporte(p.getImporte());
			pedidoDTO.setRestaurante(r.getNombre());
			pedidoDTO.setRepartidor(rep.getNombre());
			pedidoDTO.setComentarios(p.getComentarios());
			pedidoDTO.setDireccion(p.getDireccion());
			
			ArrayList<EstadoPedidoDTO> l = new ArrayList<>();
			ArrayList<ItemPedidoDTO> l2 = new ArrayList<>();
			
			if(p.getEstados()!=null) {
				
				for(EstadoPedido e : p.getEstados()) {
					l.add(new EstadoPedidoDTO(e.getFechaEstado(),e.getEstado()));
					
				}
				pedidoDTO.setEstados(l);
			}
			
			if(p.getItems()!=null) {
				
				for(ItemPedido e : p.getItems()) {
					l2.add(new ItemPedidoDTO(p.getRestaurante(),e.getPrecio()));
					
				}
				pedidoDTO.setItems(l2);
			}
			
			
			

			pedidosDTO.add(pedidoDTO);
		}
		return pedidosDTO;
		
	}
	
	public List<PedidoDTO> findPedidoByUser(Integer usuario) {	
		
		
		List<Pedido> pedidos = PedidoDAO.getPedidoDAO().findPedidoByUser(usuario);
		List<PedidoDTO> pedidosDTO = new ArrayList<>();

		for (Pedido p : pedidos) {
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(p.getCliente());
			Usuario rep = UsuarioDAO.getUsuarioDAO().findById(p.getRepartidor());
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(p.getRestaurante());

			PedidoDTO pedidoDTO = new PedidoDTO();
			pedidoDTO.setCliente(u.getNombre());
			pedidoDTO.setImporte(p.getImporte());
			pedidoDTO.setRestaurante(r.getNombre());
			pedidoDTO.setRepartidor(rep.getNombre());
			pedidoDTO.setComentarios(p.getComentarios());
			pedidoDTO.setDireccion(p.getDireccion());
			
			ArrayList<EstadoPedidoDTO> l = new ArrayList<>();
			ArrayList<ItemPedidoDTO> l2 = new ArrayList<>();

			
			if(p.getEstados()!=null) {
				
				for(EstadoPedido e : p.getEstados()) {
					l.add(new EstadoPedidoDTO(e.getFechaEstado(),e.getEstado()));
					
				}
				pedidoDTO.setEstados(l);
			}
			

			if (p.getItems() != null) {

				for (ItemPedido e : p.getItems()) {
					l2.add(new ItemPedidoDTO(p.getRestaurante(), e.getPrecio()));

				}
				pedidoDTO.setItems(l2);
			}
			
			
			

			pedidosDTO.add(pedidoDTO);
		}
		return pedidosDTO;

	}
	
	
	public List<PedidoDTO> getAllPedidos() {
		
		
		List<Pedido> pedidos = PedidoDAO.getPedidoDAO().findAllPedidos();
		List<PedidoDTO> pedidosDTO = new ArrayList<>();

		for (Pedido p : pedidos) {
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(p.getCliente());
			Usuario rep = UsuarioDAO.getUsuarioDAO().findById(p.getRepartidor());
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(p.getRestaurante());

			PedidoDTO pedidoDTO = new PedidoDTO();
			pedidoDTO.setCliente(u.getNombre());
			pedidoDTO.setImporte(p.getImporte());
			pedidoDTO.setRestaurante(r.getNombre());
			pedidoDTO.setRepartidor(rep.getNombre());
			pedidoDTO.setComentarios(p.getComentarios());
			pedidoDTO.setDireccion(p.getDireccion());
			
			ArrayList<EstadoPedidoDTO> l = new ArrayList<>();
			ArrayList<ItemPedidoDTO> l2 = new ArrayList<>();

			
			if(p.getEstados()!=null) {
				
				for(EstadoPedido e : p.getEstados()) {
					l.add(new EstadoPedidoDTO(e.getFechaEstado(),e.getEstado()));
					
				}
				pedidoDTO.setEstados(l);
			}
			

			if (p.getItems() != null) {

				for (ItemPedido e : p.getItems()) {
					l2.add(new ItemPedidoDTO(p.getRestaurante(), e.getPrecio()));

				}
				pedidoDTO.setItems(l2);
			}
			
			
			

			pedidosDTO.add(pedidoDTO);
		}
		return pedidosDTO;

	}
	
	public int findUsersRestaurants(List<Integer> l) {
		
		return PedidoDAO.getPedidoDAO().findUsersRestaurants(l);
	}
	
	
	public int findPedidoByUserDifferentRestaurant(Integer usuario) {	
		
		return zeppelinumRemoto.findPedidoByUserDifferentRestaurant(usuario);
		//return PedidoDAO.getPedidoDAO().numPedidosDifferentRestaurant(usuario);
		

	}
	
	public void addRepartidorPedido(Integer repartidor,ObjectId pedido) {
		
		PedidoDAO.getPedidoDAO().addRepartidor(repartidor,pedido);
	}
	
	
	public void updateEstadosPedido(Object id, EstadoPedido e) {
		
		PedidoDAO.getPedidoDAO().updatePedido(id,e);
		
	}
	
	public int findPedidosRestaurants(List<Integer> l) {
		
		//return PedidoDAO.getPedidoDAO().findPedidosRestaurants(l);
		return zeppelinumRemoto.findPedidosRestaurants(l);
		
	}
	
	
	
	public void deleteAllPedidos() {
		
		PedidoDAO.getPedidoDAO().deleteAllPedidos();
	}
	

	public void deleteAllOpiniones() {
		
		OpinionDAO.getOpinionDAO().deleteAllOpiniones();
		
	}
	
	
	public void deleteAllDirecciones() {
		
		DireccionDAO.getDireccionDAO().deleteAllDirecciones();
	}

	
	
	public int findNumPedidoByUser(int id) {
		return zeppelinumRemoto.findNumPedidoByUser(id);
	}
	
	public int getNumAllPedidos() {

		return zeppelinumRemoto.getNumAllPedidos();
	}

	public int findNumPedidoByUserDifferentRestaurant(Integer id) {
		
		return zeppelinumRemoto.findNumPedidoByUserDifferentRestaurant(id);
	}

	public int findNumUsersRestaurants(List<Integer> findRestaurantIdByResponsable) {
		return zeppelinumRemoto.findNumUsersRestaurants(findRestaurantIdByResponsable);
	}



}