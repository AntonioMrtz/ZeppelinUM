package persistencia.jpa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.dto.RestauranteDTO;
import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.Usuario;
import persistencia.mongo.bean.Direccion;
import persistencia.mongo.dao.DireccionDAO;

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

	public List<RestauranteDTO> transformarToDTO(List<Restaurante> restaurantes) {
		List<RestauranteDTO> rs = new ArrayList<RestauranteDTO>();
		for (Restaurante r : restaurantes) {
			Direccion direccion = DireccionDAO.getDireccionDAO().findByRestaurante(r.getId());
			RestauranteDTO restaurant = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal());
			restaurant.setCiudad(direccion.getCiudad());
			restaurant.setLongitud(direccion.getCoordenadas().getPosition().getValues().get(0));
			restaurant.setLatitud(direccion.getCoordenadas().getPosition().getValues().get(1));
			rs.add(restaurant);

		}
		System.out.println("done");
		return rs;
	}

    public Number countRestaurantesByFiltros(String keyword, LocalDate fechaAlta, boolean sinPenalizacion) {
        try {
            String queryString = " SELECT count(distinct r) FROM Restaurante r " 
                    + " INNER JOIN Plato p WHERE p.restaurante = r AND p.disponibilidad = true"  ;// Ponemos una condición que siempre es cierta para poder enlazar las condiciones más fácilmente    
                                                                                                        
            if (keyword != null) {
                queryString += " AND r.nombre like :keyword ";
            }
            if(fechaAlta != null) {
                queryString +=" AND r.fechaAlta >= :fechaAlta ";
            }
            if(sinPenalizacion) {
                queryString +=" AND r.numPenalizaciones = 0 ";
            }
            System.out.println(queryString);
            Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
            if (keyword != null) {
                query.setParameter("keyword", "%"+keyword+"%");
            }
            if (fechaAlta != null) {
                query.setParameter("fechaAlta", fechaAlta);
            }
            
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            return (Number)query.getSingleResult();
        } catch (RuntimeException re) {
            throw re;
        }
    }
	
	public List<RestauranteDTO> findRestauranteByFiltrosLazy(String keyword, LocalDate fechaAlta, 
            boolean mejorValorados, boolean sinPenalizacion, int start, int max) {
        try {
            String queryString = " SELECT r FROM Restaurante r " 
                    + " NATURAL JOIN Plato p WHERE r.id IS NOT NULL";// Ponemos una condición que siempre es cierta para poder enlazar las condiciones más fácilmente    
                                                                                                        
            if (keyword != null) {
                queryString += " AND r.nombre like :keyword ";
            }
            if(fechaAlta != null) {
                queryString +=" AND r.fechaAlta >= :fechaAlta ";
            }
            if(sinPenalizacion) {
                queryString +=" AND r.numPenalizaciones = 0 ";
            }
            // queryString +=" GROUP BY r.id ";
            
            if(mejorValorados) {
                queryString +=" ORDER BY r.valoracionGlobal desc ";
            }

            Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
            if (keyword != null) {
                query.setParameter("keyword", "%"+keyword+"%");
            }
            if (fechaAlta != null) {
                query.setParameter("fechaAlta", fechaAlta);
            }
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            query.setFirstResult(start);
            query.setMaxResults(max);
            return transformarToDTO(query.getResultList());
        } catch (RuntimeException re) {
            throw re;
        }
    }
	
	public List<RestauranteDTO> findRestauranteByFiltros(String keyword, LocalDate fechaAlta, boolean mejorValorados,
			boolean sinPenalizacion) {
		try {
			String queryString = " SELECT r FROM Restaurante r " + " INNER JOIN r.platos p on p.disponibilidad = true "
			// Con el inner join me aseguro de que no aparezcan restaurantes con 0 platos
			// disponibles
					+ " WHERE r.id is not null ";// Ponemos una condición que siempre es cierta para poder enlazar las
													// condiciones más fácilmente
			if (keyword != null) {
				queryString += " AND r.nombre like :keyword ";
			}
			if (fechaAlta != null) {
				queryString += " AND r.fechaAlta >= :fechaAlta ";
			}
			if (sinPenalizacion) {
				queryString += " AND r.numPenalizaciones = 0 ";
			}
			queryString += " GROUP BY r.id ";
			if (mejorValorados) {
				queryString += " ORDER BY r.valoracionGlobal desc ";
			}
			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			if (keyword != null) {
				query.setParameter("keyword", "%" + keyword + "%");
			}
			if (fechaAlta != null) {
				query.setParameter("fechaAlta", fechaAlta);
			}
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<RestauranteDTO> findByName(String nombre) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Restaurante.findByName");
			query.setParameter("nombre", nombre);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<RestauranteDTO> findByResponsable(Integer responsable) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Restaurante.findByResponsable");
			query.setParameter("responsable", responsable);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<RestauranteDTO> getAllRestaurantes() {
		try {
			List<Restaurante> resList = getAll();
			return transformarToDTO(resList);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}