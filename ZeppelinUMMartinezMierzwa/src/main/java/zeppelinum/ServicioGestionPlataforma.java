package zeppelinum;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import persistencia.jpa.bean.Plato;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
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

    public Integer registrarRestaurante(String nombre, Integer responsable) {

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
}