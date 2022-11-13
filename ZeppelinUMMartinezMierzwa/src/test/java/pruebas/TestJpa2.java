package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.PlatoDAO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import zeppelinum.ServicioGestionPlataforma;

class TestJpa2 {
	
	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	
	
	@org.junit.jupiter.api.Test
	public void loginTest() {
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.login("periquita@palotes.es", "12345") != null);
		assertFalse(servicio.login("mdclg3@um.es", "loquesea") != null);
		assertFalse(servicio.login("periquita@palotes.es", "123456") != null);

		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		UsuarioDAO.getUsuarioDAO().delete(u);
	}

	@org.junit.jupiter.api.Test
	public void checkUsuarioTest() {
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.isUsuarioRegistrado("periquita@palotes.es"));
		assertFalse(servicio.isUsuarioRegistrado("mdclg3@um.es"));
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		UsuarioDAO.getUsuarioDAO().delete(u);
	}

	
	@org.junit.jupiter.api.Test
	void crearPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer restaurante_id = servicio.registrarRestaurante("4537536845", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		Integer plato_id = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, restaurante_id);
		assertNotNull(plato_id);
		Restaurante u = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		RestauranteDAO.getRestauranteDAO().delete(u);
	}
	
	//FIXME doesnt work with multiples executions ( made by teacher )
	@org.junit.jupiter.api.Test
	public void platosByRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer restaurante_id = servicio.registrarRestaurante("12524745376342", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		Integer plato_id = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, restaurante_id);
		int last_size=servicio.getMenuByRestaurante(restaurante_id).size();
		servicio.cambiarDisponibilidadPlato(plato_id, false);
		assertTrue(servicio.getMenuByRestaurante(1).size() == last_size-1);
		Restaurante u = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		RestauranteDAO.getRestauranteDAO().delete(u);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarRestaurantes() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer restaurante_id = servicio.registrarRestaurante("La periquita", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		assertTrue(servicio.getRestaurantesByFiltros("La periquita", true, true, true).size() == 1);
		assertTrue(servicio.getRestaurantesByFiltros("venta", true, true, true).size() == 0);
		Restaurante u = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		RestauranteDAO.getRestauranteDAO().delete(u);
	
	}

}