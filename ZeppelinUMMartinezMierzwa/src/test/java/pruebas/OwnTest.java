package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import persistencia.jpa.bean.Incidencia;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.dao.IncidenciaDAO;
import persistencia.jpa.dao.RestauranteDAO;
import zeppelinum.ServicioGestionPlataforma;

class OwnTest {

	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

	@org.junit.jupiter.api.Test
	void checkCreateCategory() {

		assertNotNull(servicio.crearCategoria("categoriaPrueba"));

	}

	@org.junit.jupiter.api.Test
	void checkAddCategory() {

		assertTrue(servicio.addCategoria(1, 1));

	}

	@org.junit.jupiter.api.Test
	void checkRegisterRestaurant() {

		LinkedList<Integer> categorias = new LinkedList<>();

		/* persist categories */

		categorias.add(servicio.crearCategoria("cat1"));
		categorias.add(servicio.crearCategoria("cat2"));

		Integer restaurante_id = servicio.registrarRestaurante("La Periquita", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, categorias);

		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);

		assertEquals(2, r.getCategorias().size());

	}

	@org.junit.jupiter.api.Test
	void checkChangePlateAvailableness() {

		Integer categoria_id = servicio.crearCategoria("cat1");
		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(categoria_id);
		Integer restaurante_id = servicio.registrarRestaurante("Restaurante", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		Integer plato = servicio.nuevoPlato("plato1", "", 10, restaurante_id);
		
		//FIXME plato is null

		assertTrue(servicio.cambiarDisponibilidadPlato(plato, false));

	}

	@org.junit.jupiter.api.Test
	void checkCreateIncidencia() {

		Integer categoria_id = servicio.crearCategoria("cat1");
		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(categoria_id);
		Integer restaurante_id = servicio.registrarRestaurante("1 something else", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("1 someone else", "Palotes", fechaNacimiento,
				"periquita@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		Incidencia incidencia = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		assertNotNull(incidencia);

	}

	@org.junit.jupiter.api.Test
	void checkIncidenciaLinked() {

		Integer categoria_id = servicio.crearCategoria("cat1");
		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(categoria_id);
		Integer restaurante_id = servicio.registrarRestaurante("something else", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("someone else", "Palotes", fechaNacimiento,
				"periquita@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		Incidencia incidencia = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		assertEquals(incidencia.getRestaurante().getId(), restaurante.getId());

	}

}
