package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.dao.PlatoDAO;
import zeppelinum.ServicioGestionPlataforma;

class Test {
	
	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	
	
	@org.junit.jupiter.api.Test
	public void loginTest() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.login("periquita@palotes.es", "12345") != null);
		assertFalse(servicio.login("mdclg3@um.es", "loquesea") != null);
		assertFalse(servicio.login("periquita@palotes.es", "123456") != null);
	}

	@org.junit.jupiter.api.Test
	public void checkUsuarioTest() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.isUsuarioRegistrado("periquita@palotes.es"));
		assertFalse(servicio.isUsuarioRegistrado("mdclg3@um.es"));
	}

	
	@org.junit.jupiter.api.Test
	void crearPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer exito = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, 1);
		assertNotNull(exito);
	}
	
	@org.junit.jupiter.api.Test
	public void platosByRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		//servicio.cambiarDisponibilidadPlato(1, true);
		assertTrue(servicio.getMenuByRestaurante(1).size() == 1);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarRestaurantes() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.getRestaurantesByFiltros("periqui", true, true, true).size() == 1);
		assertTrue(servicio.getRestaurantesByFiltros("venta", true, true, true).size() == 0);
	}

}