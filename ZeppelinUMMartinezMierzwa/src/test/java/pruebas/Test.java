package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import persistencia.jpa.bean.Incidencia;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.IncidenciaDAO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import zeppelinum.ServicioGestionPlataforma;

class Test {
	
	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	@org.junit.jupiter.api.Test
	void crearUsuario() {

		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		
		//servicio.
		assertTrue(usuario != null);
	}

	
	
	@org.junit.jupiter.api.Test
	void validarUsuario() {
		
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		
		boolean exito = servicio.validarUsuario(usuario);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {

		Integer rest = servicio.registrarRestaurante("La periquita", 1, new LinkedList<>());
		assertTrue(rest != null);
		Integer exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertNotNull(exito);

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

		
		/* persist categories*/
		
		categorias.add( servicio.crearCategoria("cat1"));
		categorias.add( servicio.crearCategoria("cat2"));		


		Integer restaurante_id=servicio.registrarRestaurante("La Periquita",1,categorias);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
				
		assertEquals(2, r.getCategorias().size());
	
		
	}
	
	@org.junit.jupiter.api.Test
	void checkChangePlateAvailableness(){
		
		Integer categoria_id=servicio.crearCategoria("cat1");
		LinkedList<Integer> cats=new LinkedList<>();
		cats.add(categoria_id);
		Integer restaurante_id=servicio.registrarRestaurante("La Periquita",1,cats);
		Integer plato=servicio.nuevoPlato("plato1","", 10, restaurante_id);
		
		
		assertTrue(servicio.cambiarDisponibilidadPlato(plato, false));
		
	}
	
	@org.junit.jupiter.api.Test
	void check(){
		
		// Create restaurant
		// Create User
		// Create Incidencia
		// save incidencia
		// incidencia.restaurant
		Integer categoria_id=servicio.crearCategoria("cat1");
		LinkedList<Integer> cats=new LinkedList<>();
		cats.add(categoria_id);
		Integer restaurante_id = servicio.registrarRestaurante("La Periquita",1,cats);
		Integer plato = servicio.nuevoPlato("plato1","", 10, restaurante_id);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);		
		Integer incidencia = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "", usuario_id, restaurante_id);
		Incidencia incidencia = IncidenciaDAO.getIncidenciaDAO().findById(incidencia);
		assertNotNull(incidencia);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}