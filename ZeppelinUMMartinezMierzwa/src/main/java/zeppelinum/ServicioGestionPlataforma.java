package zeppelinum;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.bean.Plato;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.CategoriaRestauranteDAO;
import persistencia.jpa.dao.EntityManagerHelper;
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

    public Integer registrarRestaurante(String nombre, Integer responsable,List<CategoriaRestaurante> categorias) {

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
            r.setCategorias(categorias);

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

    
    
    public boolean crearCategoria(Integer categoria,String nombre) {
    	
    	  EntityManager em = EntityManagerHelper.getEntityManager();
          try {
              em.getTransaction().begin();
              
              CategoriaRestaurante r = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);
              r.setCategoria(nombre);
             
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
    }
    
    
    public boolean cambiarDisponibilidadPlato(Integer plato,boolean disponibilidad) {
    	
    	EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Plato p= PlatoDAO.getPlatoDAO().findById(plato);
            p.setDisponibilidad(disponibilidad);
            
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
    
    
    
}