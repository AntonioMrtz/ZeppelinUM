package pruebas;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import persistencia.dto.PedidoDTO;
import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.bean.Incidencia;
import persistencia.jpa.bean.Plato;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.CategoriaRestauranteDAO;
import persistencia.jpa.dao.IncidenciaDAO;
import persistencia.jpa.dao.PlatoDAO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import persistencia.mongo.bean.EstadoPedido;
import persistencia.mongo.bean.ItemPedido;
import persistencia.mongo.bean.TipoEstado;
import zeppelinum.ServicioGestionPedido;
import zeppelinum.ServicioGestionPlataforma;


class OwnTest {

	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	private ServicioGestionPedido servicio_pedido= ServicioGestionPedido.getServicioGestionPedido();

	@Test
	void checkCreateCategory() {
		Integer id = servicio.crearCategoria("categoriaPrueba");
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(id);
		assertNotNull(id);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
	}
	
	@org.junit.jupiter.api.Test
    void hacerpedido() {
    ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();   
    servicio.crearPedido("a");
}

	@org.junit.jupiter.api.Test
	void checkAddCategory() {

		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		// get the restaurante in order to delete it.
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);

		Integer cr_id = servicio.crearCategoria("categoriaPrueba");
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cr_id);
		
//		System.out.println(cr_id);
//		System.out.println(restaurante_id);
		
		boolean added = servicio.addCategoria(restaurante_id, cr_id);
		
		boolean deletedR = RestauranteDAO.getRestauranteDAO().delete(restaurante);
		boolean deletedC = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
		assertTrue(added && deletedR && deletedC);
		// deleting the restaurante in order to get the old state of the db back


	}

	@Test
	void checkRegisterRestaurant() {

		LinkedList<Integer> categorias = new LinkedList<>();

		/* persist categories */
		Integer cat1_id = servicio.crearCategoria("fg7834fgw3794fg6");
		Integer cat2_id = servicio.crearCategoria("fbuq38g4bf34");
		categorias.add(cat1_id);
		categorias.add(cat2_id);

		Integer restaurante_id = servicio.registrarRestaurante("f7834gb76f3498f723g4", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, categorias);

//		System.out.println("this is the id: " + restaurante_id);
		
		CategoriaRestaurante c1 = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat1_id);
		
		CategoriaRestaurante c2 = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat2_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);

		assertEquals(2, r.getCategorias().size());
		
		// deleting the added entities
		RestauranteDAO.getRestauranteDAO().delete(r);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c1);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c2);

	}

	@org.junit.jupiter.api.Test
	void checkChangePlateAvailableness() {
		Integer cat1_id = servicio.crearCategoria("cat2");
		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(cat1_id);
		Integer restaurante_id = servicio.registrarRestaurante("89236578239t5978321648", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		Integer plato = servicio.nuevoPlato("plato1", "", 10, restaurante_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat1_id);
		Plato p = PlatoDAO.getPlatoDAO().findById(plato);
		assertTrue(servicio.cambiarDisponibilidadPlato(plato, false));
		

		PlatoDAO.getPlatoDAO().delete(p);
		RestauranteDAO.getRestauranteDAO().delete(r);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
	}

	@org.junit.jupiter.api.Test
	void checkCreateIncidencia() {

		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(1);
		Integer restaurante_id = servicio.registrarRestaurante("rastaurante", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("Veratti", "Palotes", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		
		assertNotNull(i);
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		UsuarioDAO.getUsuarioDAO().delete(u);
		RestauranteDAO.getRestauranteDAO().delete(r);
	}

	@org.junit.jupiter.api.Test
	void checkIncidenciaLinked() {

		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(1);
		Integer restaurante_id = servicio.registrarRestaurante("b83q4bf7349zfb394u", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("fb83274gbf9347fv634e", "Palotes2", fechaNacimiento,
				"periquita@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		
		
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		assertEquals(i.getRestaurante().getId(), r.getId());
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		UsuarioDAO.getUsuarioDAO().delete(u);
		RestauranteDAO.getRestauranteDAO().delete(r);

	}
	
	@org.junit.jupiter.api.Test
	void checkSearchUserByResponsable() {
		
		
		Integer usuario = servicio.registrarUsuario("84395689123746923817648", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.ADMIN);
		Integer categoria = servicio.crearCategoria("21367345123786edt237tz");
		ArrayList<Integer> l= new ArrayList<>();
		l.add(categoria);
		Integer restaurante = servicio.registrarRestaurante("fb3qz489f76zg3eu4f", usuario, " ", " ", 82, "Murcia",38.009109654488476, -1.1339542029796663, l);
		Integer restaurante2 = servicio.registrarRestaurante("fhb78q034gfb793qz4gd", usuario, " ", " ", 82, "Murcia",38.009109654488476, -1.1339542029796663, l);
		

		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario);
		CategoriaRestaurante c = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
		Restaurante r2 = RestauranteDAO.getRestauranteDAO().findById(restaurante2);
		
		
		
		
		assertEquals(2,servicio.searchRestaurantByResponsable(usuario).size());
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		RestauranteDAO.getRestauranteDAO().delete(r2);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c);               
		UsuarioDAO.getUsuarioDAO().delete(u);
		
		
		
	}
	
	@org.junit.jupiter.api.Test
	void checkNonValidatedRestaurantUsers() {
		
		Integer usuario = servicio.registrarUsuario("245745262431", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		Integer usuario_validated = servicio.registrarUsuario("819236482017364", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		
		servicio.validarUsuario(usuario_validated);
		
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario);
		Usuario u2 = UsuarioDAO.getUsuarioDAO().findById(usuario_validated);
		
		assertTrue(servicio.findNonValidatedRestauranteUsers().size()==1);
		
		
		UsuarioDAO.getUsuarioDAO().delete(u);
		UsuarioDAO.getUsuarioDAO().delete(u2);

		
	}
	
	@org.junit.jupiter.api.Test
	void checkFindIncidenciaByUser() {
		
		Integer usuario = servicio.registrarUsuario("us1", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		Integer usuario2 = servicio.registrarUsuario("us_validated", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		Integer categoria = servicio.crearCategoria("cat1");
		ArrayList<Integer> l= new ArrayList<>();
		l.add(categoria);
		Integer restaurante = servicio.registrarRestaurante("rest1", usuario, " ", " ", 82, "Murcia",38.009109654488476, -1.1339542029796663, l);
		
		Integer incidencia = servicio.crearIncidencia(LocalDate.now(), "i1", LocalDate.now(), "a", usuario, restaurante);
		Integer incidencia2 = servicio.crearIncidencia(LocalDate.now(), "i2", LocalDate.now(), "a", usuario, restaurante);
		Integer incidencia3 = servicio.crearIncidencia(LocalDate.now(), "i3", LocalDate.now(), "a", usuario2, restaurante);
		
		
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario);
		Usuario u2 = UsuarioDAO.getUsuarioDAO().findById(usuario2);
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
		
		
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia);
		Incidencia i2 = IncidenciaDAO.getIncidenciaDAO().findById(incidencia2);
		Incidencia i3= IncidenciaDAO.getIncidenciaDAO().findById(incidencia3);
		
		CategoriaRestaurante c = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);

		
		assertTrue(servicio.findIncidenciaByUser(usuario).size()==2);
		
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		IncidenciaDAO.getIncidenciaDAO().delete(i2);
		IncidenciaDAO.getIncidenciaDAO().delete(i3);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c);
		UsuarioDAO.getUsuarioDAO().delete(u);
		UsuarioDAO.getUsuarioDAO().delete(u2);
		

		
	}
	
	
	@org.junit.jupiter.api.Test
	void checkFindIncidenciaNotClosed() {
		
		Integer usuario = servicio.registrarUsuario("3278563487156", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		Integer usuario2 = servicio.registrarUsuario("18273578634985793", "apell1", LocalDate.now(), "a@", "clave", TipoUsuario.RESTAURANTE);
		Integer categoria = servicio.crearCategoria("cat1");
		ArrayList<Integer> l= new ArrayList<>();
		l.add(categoria);
		Integer restaurante = servicio.registrarRestaurante("49380652901384", usuario, " ", " ", 82, "Murcia",38.009109654488476, -1.1339542029796663, l);
		
		Integer incidencia = servicio.crearIncidencia(LocalDate.now(), "i1", null, "a", usuario, restaurante);
		Integer incidencia2 = servicio.crearIncidencia(LocalDate.now(), "i2", null, "a", usuario, restaurante);
		Integer incidencia3 = servicio.crearIncidencia(LocalDate.now(), "i3", LocalDate.now(), "a", usuario2, restaurante);
		
		
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario);
		Usuario u2 = UsuarioDAO.getUsuarioDAO().findById(usuario2);
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
		
		
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia);
		Incidencia i2 = IncidenciaDAO.getIncidenciaDAO().findById(incidencia2);
		Incidencia i3= IncidenciaDAO.getIncidenciaDAO().findById(incidencia3);
		
		CategoriaRestaurante c = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);

		
		assertTrue(servicio.findIncidenciaNotClosed().size()==2);
		
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		IncidenciaDAO.getIncidenciaDAO().delete(i2);
		IncidenciaDAO.getIncidenciaDAO().delete(i3);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c);
		UsuarioDAO.getUsuarioDAO().delete(u);
		UsuarioDAO.getUsuarioDAO().delete(u2);
		

		
	}
	
	@Test
	public void checkAddRepartidor() {
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		
		
		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
		
		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
		
		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());

		
		ObjectId id = servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,null,cliente_id,new ArrayList<EstadoPedido>(),new ArrayList<ItemPedido>());

		
		servicio_pedido.addRepartidorPedido(repartidor_id, id);
		
		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByUser(cliente_id);
		
		UsuarioDTO repartidor_dto = UsuarioDAO.getUsuarioDAO().findByName("repartidor","repartidor").get(0);
		
		assertEquals(repartidor_dto.getNombre(),"repartidor");
		assertEquals(repartidor_dto.getApellidos(),"repartidor");
		
		
		
		
		Usuario cliente = UsuarioDAO.getUsuarioDAO().findById(cliente_id);
		Usuario repartidor = UsuarioDAO.getUsuarioDAO().findById(repartidor_id);
		Restaurante r= RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		UsuarioDAO.getUsuarioDAO().delete(repartidor);
		UsuarioDAO.getUsuarioDAO().delete(cliente);
		
		
		servicio_pedido.deleteAllPedidos();
		
	}
	
	@Test
	public void checkFindPedidoByUser() {
		

		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		
		
		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
		
		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
		
		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());

		
		ObjectId id= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,new ArrayList<EstadoPedido>(),new ArrayList<ItemPedido>());
		ObjectId id2= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,new ArrayList<EstadoPedido>(),new ArrayList<ItemPedido>());

				
		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByUser(cliente_id);
		
		
		assertEquals(2,pedidos.size());
		
		
		
		
		
		Usuario cliente = UsuarioDAO.getUsuarioDAO().findById(cliente_id);
		Usuario repartidor = UsuarioDAO.getUsuarioDAO().findById(repartidor_id);
		Restaurante r= RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		UsuarioDAO.getUsuarioDAO().delete(repartidor);
		UsuarioDAO.getUsuarioDAO().delete(cliente);
		
		
		servicio_pedido.deleteAllPedidos();
		
		
	}
	
	@Test
	public void checkFindPedidoByRestaurant() {
		

		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		
		
		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
		
		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
		
		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());

		ObjectId id= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,new ArrayList<EstadoPedido>(),new ArrayList<ItemPedido>());
		ObjectId id2= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,new ArrayList<EstadoPedido>(),new ArrayList<ItemPedido>());

				
		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByRestaurante(restaurante_id);
		
		
		assertEquals(2,pedidos.size());

		
		
		Usuario cliente = UsuarioDAO.getUsuarioDAO().findById(cliente_id);
		Usuario repartidor = UsuarioDAO.getUsuarioDAO().findById(repartidor_id);
		Restaurante r= RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		UsuarioDAO.getUsuarioDAO().delete(repartidor);
		UsuarioDAO.getUsuarioDAO().delete(cliente);
		
		
		servicio_pedido.deleteAllPedidos();
		
		
	}
	
	@Test
	public void checkUpdatePedido() {
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		
		
		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
		
		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
		
		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());

		ArrayList<EstadoPedido> l = new ArrayList<>();
		l.add(new EstadoPedido(LocalDateTime.now(),TipoEstado.INCIADO));
		
		ObjectId id= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,l,new ArrayList<ItemPedido>());
		
		servicio_pedido.updateEstadosPedido(id,new EstadoPedido(LocalDateTime.now(),TipoEstado.ACEPTADO));
				
		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByRestaurante(restaurante_id);
		

		
		
		Usuario cliente = UsuarioDAO.getUsuarioDAO().findById(cliente_id);
		Usuario repartidor = UsuarioDAO.getUsuarioDAO().findById(repartidor_id);
		Restaurante r= RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		RestauranteDAO.getRestauranteDAO().delete(r);
		UsuarioDAO.getUsuarioDAO().delete(repartidor);
		UsuarioDAO.getUsuarioDAO().delete(cliente);
		
		
		servicio_pedido.deleteAllPedidos();
		assertEquals(2,pedidos.get(0).getEstados().size());
		
		
	}
	
	@Test
	public void checkItemPedido() {
		
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		
		
		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
		
		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
		
		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());

		Integer plato_id = servicio.nuevoPlato("a", "a", 1.0, restaurante_id);
		
		ArrayList<EstadoPedido> l = new ArrayList<>();
		l.add(new EstadoPedido(LocalDateTime.now(),TipoEstado.INCIADO));
		
		ArrayList<ItemPedido> l2 = new ArrayList<>();
		l2.add(new ItemPedido("pizza", 2, 2.0, plato_id, restaurante_id));
		l2.add(new ItemPedido("mango", 1, 2.0, plato_id, restaurante_id));
		
		ObjectId id= servicio_pedido.crearPedido(LocalTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,l,l2);
		
				
		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByRestaurante(restaurante_id);
		
		
		Plato plato = PlatoDAO.getPlatoDAO().findById(plato_id);
		Usuario cliente = UsuarioDAO.getUsuarioDAO().findById(cliente_id);
		Usuario repartidor = UsuarioDAO.getUsuarioDAO().findById(repartidor_id);
		Restaurante r= RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		
		
		assertEquals(2, pedidos.get(0).getItems().size());

				
		
		PlatoDAO.getPlatoDAO().delete(plato);
		RestauranteDAO.getRestauranteDAO().delete(r);
		UsuarioDAO.getUsuarioDAO().delete(repartidor);
		UsuarioDAO.getUsuarioDAO().delete(cliente);
		servicio_pedido.deleteAllPedidos();
		
		
	}	
	
	
//	@Test//QUITAR
//	public void testEJBCancelar() {
//		
//		LocalDate fechaNacimiento = LocalDate.now();
//		
//		
//		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
//				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
//		
//		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
//				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
//		
//		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
//
//		ArrayList<EstadoPedido> l = new ArrayList<>();
//		l.add(new EstadoPedido(LocalDateTime.now(),TipoEstado.INCIADO));
//		
//		ArrayList<ItemPedido> l2 = new ArrayList<>();
//
//		
//		ObjectId id= servicio_pedido.crearPedido(LocalDateTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,l,l2);
//		
//		servicio_pedido.crearPedido(id.toString());
//		
//	}
	
	
//	@Test
//	public void testInsertarPedido() {
//		
//		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
//		
//		
//		Integer repartidor_id = servicio.registrarUsuario("repartidor", "repartidor", fechaNacimiento,
//				"veratti@palotes.es", "12345", TipoUsuario.RIDER);
//		
//		Integer cliente_id = servicio.registrarUsuario("cliente", "cliente", fechaNacimiento,
//				"veratti@palotes.es", "12345", TipoUsuario.CLIENTE);
//		
//		Integer restaurante_id = 1;
//
//		ArrayList<EstadoPedido> l = new ArrayList<>();
//		l.add(new EstadoPedido(LocalDateTime.now(),TipoEstado.INCIADO));
//		
//		ObjectId id= servicio_pedido.crearPedido(LocalDateTime.now(),"a" , 10.0,"calle 1",restaurante_id,repartidor_id,cliente_id,l,null);
//		
//		servicio_pedido.updateEstadosPedido(id,new EstadoPedido(LocalDateTime.now(),TipoEstado.ACEPTADO));
//				
//		List<PedidoDTO> pedidos =servicio_pedido.findPedidoByRestaurante(restaurante_id);
//	}
	
	@AfterEach
	public void deleteDireccionesDB(){
		
		servicio_pedido.deleteAllDirecciones();
		
	}
	
	
	
	
	

}
