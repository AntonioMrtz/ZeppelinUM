package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import persistencia.jpa.bean.TipoUsuario;
import zeppelinum.ServicioGestionPlataforma;

class TestJpa1 {

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
		
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Periquitas", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		
		boolean exito = servicio.validarUsuario(usuario);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {

		Integer rest = servicio.registrarRestaurante("La periquita", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		assertTrue(rest != null);
		Integer exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertNotNull(exito);

	}
}
