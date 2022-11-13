package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import persistencia.mongo.dao.DireccionDAO;
import zeppelinum.ServicioGestionPlataforma;

class TestJpa1 {

private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	@org.junit.jupiter.api.Test
	void crearUsuario() {

		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("zirewie39485738", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		
		assertTrue(usuario_id != null);
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		UsuarioDAO.getUsuarioDAO().delete(u);
	}

	
	
	@org.junit.jupiter.api.Test
	void validarUsuario() {
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("923752983456", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		
		boolean exito = servicio.validarUsuario(usuario_id);
		assertTrue(exito);
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		UsuarioDAO.getUsuarioDAO().delete(u);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {

		Integer restaurante_id = servicio.registrarRestaurante("11241235235", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		assertTrue(restaurante_id != null);
		Integer plato_id = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, restaurante_id);
		assertNotNull(plato_id);
		Restaurante u = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		RestauranteDAO.getRestauranteDAO().delete(u);


	}
	
//	@AfterEach
//	void dropMongo() {
//		
//		DireccionDAO.getDireccionDAO().deleteAllDirecciones();
//		
//	}
	
}
