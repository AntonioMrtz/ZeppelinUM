package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import persistencia.dto.RestauranteDTO;
import persistencia.jpa.bean.TipoUsuario;
import zeppelinum.ServicioGestionPedido;
import zeppelinum.ServicioGestionPlataforma;

class TestMongo1 {

	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	
	
	@org.junit.jupiter.api.Test
    void crearRestaurantePlato() {
		
		Integer c =servicio.crearCategoria("catMongo");
		ArrayList<Integer> l = new ArrayList<>();
		l.add(c);

	    Integer rest = servicio.registrarRestaurante("Puerta de Murcia", 1, "Rio Madera", "30110", null, "Murcia",  38.009109654488476, -1.1339542029796663,l );
	    Integer rest2 = servicio.registrarRestaurante("Pistatxo", 1, "Alfaro", "30001", 12, "Murcia",  37.98654993575417, -1.1305437741450695 ,l);
	    Integer rest3 = servicio.registrarRestaurante("El Barrilero de Jose", 1, "Marqués de Espinardo", "30100", 4, "Murcia",  38.00805160364204, -1.152337749004084, l);
	    assertTrue(rest != null);
	    assertTrue(rest2 != null);
	    assertTrue(rest3 != null);
	    Integer exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras", 20d, rest);
	    assertNotNull(exito);  
	}
	
	@org.junit.jupiter.api.Test
	void buscarRestaurantesOrdenados() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

	    List<RestauranteDTO> restaurantes = servicio.getRestaurantesByCercanía(38.02410905700919, -1.1740641907325182, 20, 0);
	    for(RestauranteDTO r:restaurantes) {
	        System.out.println(r.getNombre());
	    }
	    assertTrue(restaurantes.size()>0);      
	}   
	
	
	@org.junit.jupiter.api.Test
	void crearUsuario2() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	    LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
	    Integer usuario = servicio.registrarUsuario("Mari", "Legaz", fechaNacimiento, "mclg@um.es", "12345", TipoUsuario.CLIENTE);
	    assertTrue(usuario != null);        
	}
	
	@org.junit.jupiter.api.Test
	void crearOpinion() {
	    ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();      
	    servicio.opinar(1, 2, "Todo estupendo y muy rico", 10d);
	    servicio.opinar(1, 1, "La comida llegó un poco fría", 7.5d);
	    servicio.opinar(1, 3, "El menú es un poco escaso, pero todo muy bueno", 8d);
	    servicio.opinar(1, 3, "Nos trajeron un plato cambiado", 5d);
	    //servicio.opinar(1, 4, "Siempre repetimos", 10d);        no hay id==4
	}
	    
	@org.junit.jupiter.api.Test
	    void buscarOpiniones() {
	    ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();      
	    assertTrue(servicio.findByUsuario(1).size() == 4);
	    assertTrue(servicio.findByRestaurante(3).size() == 2);
	}
	

}
