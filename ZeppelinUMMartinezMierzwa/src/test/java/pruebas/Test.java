package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.dao.RestauranteDAO;
import zeppelinum.ServicioGestionPlataforma;

class Test {
	
	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	@org.junit.jupiter.api.Test
	void crearUsuario() {

		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		assertTrue(usuario != null);
	}

	
	
	@org.junit.jupiter.api.Test
	void validarUsuario() {
		
		boolean exito = servicio.validarUsuario(1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {

		Integer rest = servicio.registrarRestaurante("La periquita", 1, new LinkedList<>());
		assertTrue(rest != null);
		boolean exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertTrue(exito);

	}
	
	@org.junit.jupiter.api.Test
	void checkCreateCategory() {
		
			assertNotNull(servicio.crearCategoria("categoriaPrueba" ));
		
		
		
	}
	
	
	@org.junit.jupiter.api.Test
	void checkAddCategory() {
		
		assertTrue(servicio.addCategoria(1,1));
		
		
		
	}
	
	
	@org.junit.jupiter.api.Test
	void checkRegisterRestaurant() {
		
		LinkedList<Integer> categorias= new LinkedList<>();
		categorias.add(1);
		categorias.add(2);
		Integer restaurante_id=servicio.registrarRestaurante("La Periquita",1,categorias);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		assertNotEquals(0, r.getCategorias().size());
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}