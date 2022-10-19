package zeppelinum;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import persistencia.dto.PlatoDTO;
import persistencia.dto.RestauranteDTO;
import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.bean.Incidencia;
import persistencia.jpa.bean.Plato;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.CategoriaRestauranteDAO;
import persistencia.jpa.dao.EntityManagerHelper;
import persistencia.jpa.dao.IncidenciaDAO;
import persistencia.jpa.dao.PlatoDAO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;

public class ServicioGestionPlataforma {

    private static ServicioGestionPlataforma servicio;

    public static ServicioGestionPlataforma getServicioGestionPlataforma() {
        if (servicio == null) {
            servicio = new ServicioGestionPlataforma();
        }
        return servicio;
    }
    
    public Integer registrarUsuario(String nombre, String apellidos, LocalDate fechaNacimiento, String email,String clave, TipoUsuario tipo) {      

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            

            //TODO check if user already in database
            if(UsuarioDAO.getUsuarioDAO().findByName(nombre, apellidos).size()>=1)  return null;
            	
           
            
            Usuario usu = new Usuario();
            usu.setNombre(nombre);
            usu.setApellidos(apellidos);
            usu.setFechaNacimiento(fechaNacimiento);
            usu.setEmail(email);
            usu.setClave(clave);
            usu.setTipo(tipo);
            usu.setValidado(false);

            UsuarioDAO.getUsuarioDAO().save(usu, em);

            em.getTransaction().commit();
            return usu.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
    public boolean validarUsuario(Integer usuario) {
    	
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Usuario usu = UsuarioDAO.getUsuarioDAO().findById(usuario);
            usu.setValidado(true);
            
            em.getTransaction().commit();           
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Integer registrarRestaurante(String nombre, Integer responsable,List<Integer> categorias) {

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Restaurante r = new Restaurante();          
            r.setResponsable(UsuarioDAO.getUsuarioDAO().findById(responsable));
            r.setNombre(nombre);
            r.setFechaAlta(LocalDate.now());
            r.setValoracionGlobal(0d);
            r.setNumPenalizaciones(0);
            r.setNumValoraciones(0);
            
            List <CategoriaRestaurante> categorias_obj =  CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findByIds(categorias);

            r.setCategorias(categorias_obj);
          
            
            // add restaurant to the categories
            

            for(CategoriaRestaurante c : categorias_obj) {
            	
            	c.addRestaurant(r);
            }
            
            RestauranteDAO.getRestauranteDAO().save(r, em); 
            em.getTransaction().commit();
            return r.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    
    
    public Integer crearCategoria(String nombre) {
    	
    	  EntityManager em = EntityManagerHelper.getEntityManager();
          try {
              em.getTransaction().begin();
              
              CategoriaRestaurante c = new CategoriaRestaurante(nombre);
              c.setCategoria(nombre);
              
              CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(c, em);
             
              em.getTransaction().commit(); 
              return c.getId();
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          } finally {
              if (em.getTransaction().isActive()) {
                  em.getTransaction().rollback();
              }
              em.close();
          }
    }
    
    public boolean addCategoria(Integer restaurante,Integer categoria) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
            CategoriaRestaurante cat = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);
            
            LinkedList<CategoriaRestaurante>lista=new LinkedList<>();
            lista.add(cat);
            
            r.addCategorias(lista);
            

            CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(cat, em);
            RestauranteDAO.getRestauranteDAO().save(r, em);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }   
    
    /*
    public boolean nuevoPlato(String titulo, String descripcion, double precio, Integer restaurante) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
            Plato p = new Plato();
            p.setDescripcion(descripcion);
            p.setTitulo(titulo);
            p.setPrecio(precio);
            p.setRestaurante(r);
            p.setDisponibilidad(true);

            PlatoDAO.getPlatoDAO().save(p, em);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }*/
    
    public Integer nuevoPlato(String titulo, String descripcion, double precio, Integer restaurante) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
            Plato p = new Plato();
            p.setDescripcion(descripcion);
            p.setTitulo(titulo);
            p.setPrecio(precio);
            p.setRestaurante(r);
            p.setDisponibilidad(true);
            
            
            LinkedList<Plato> l = new LinkedList<>();
            l.add(p);

            r.addPlato(l);

            
            PlatoDAO.getPlatoDAO().save(p, em);
            RestauranteDAO.getRestauranteDAO().save(r, em);

            em.getTransaction().commit();
            return p.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
    
    public boolean cambiarDisponibilidadPlato(Integer plato,boolean disponibilidad) {
    	
    	EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Plato p= PlatoDAO.getPlatoDAO().findById(plato);
            p.setDisponibilidad(disponibilidad);
            
            PlatoDAO.getPlatoDAO().save(p, em);
            
            em.getTransaction().commit();           
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    	
    }
    
    
    public Integer crearIncidencia (LocalDate fechaCreacion,String descripcion,LocalDate fechaCierre,String comentarioCierre,Integer user_id,Integer restaurant_id) {
    	
    	EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Restaurante r=RestauranteDAO.getRestauranteDAO().findById(restaurant_id);
            Usuario u=UsuarioDAO.getUsuarioDAO().findById(user_id);            
            
            Incidencia i = new Incidencia(fechaCreacion,descripcion,fechaCierre,comentarioCierre,u,r);
            
            LinkedList<Incidencia> l = new LinkedList<>();
            l.add(i);
            
            r.addIncidencias(l);
            u.addIncidencias(l);
            
            IncidenciaDAO.getIncidenciaDAO().save(i, em);
            RestauranteDAO.getRestauranteDAO().save(r, em);
            UsuarioDAO.getUsuarioDAO().save(u, em);
            
            
            em.getTransaction().commit();     
            
            return i.getId();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
	public List<PlatoDTO> getMenuByRestaurante(Integer restaurante) {
		return PlatoDAO.getPlatoDAO().findPlatosDisponiblesByRestaurante(restaurante);
	}

	public List<RestauranteDTO> getRestaurantesByFiltros(String keyword, boolean verNovedades,
			boolean ordernarByValoracion, boolean ceroIncidencias) {
		if (keyword != null && keyword.isBlank()) {
			keyword = null;
		}
		LocalDate fecha = null;
		if (verNovedades) { // filtramos por aquellos dados de alta la última semana
			fecha = LocalDate.now();
			fecha = fecha.minusWeeks(1);
		}
		return RestauranteDAO.getRestauranteDAO().findRestauranteByFiltros(keyword, fecha, ordernarByValoracion,
				ceroIncidencias);
	}
	
	
	public boolean isUsuarioRegistrado(String email) {
		List<UsuarioDTO> u = UsuarioDAO.getUsuarioDAO().findByEmail(email);
		if(u != null && !u.isEmpty()) {
		return true;
		}
		return false;
		}
	
	
		public UsuarioDTO login(String email, String clave) {
			List<UsuarioDTO> usuarios = UsuarioDAO.getUsuarioDAO().findByEmailClave(email, clave);
			if (usuarios.isEmpty()) {
				System.out.println("Usuario no encontrado, email o clave incorrectos");
				return null;
			} else {
				System.out.println("Usuario logueado " + usuarios.get(0).getNombre());
				return usuarios.get(0);
			}
		}
    
}