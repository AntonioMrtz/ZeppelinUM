package persistencia.mongo.dao;


import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import persistencia.mongo.bean.Pedido;

public class PedidoDAO extends ExtensionMongoDAO<Pedido> {

	private static PedidoDAO pedidoDAO;

	public static PedidoDAO getPedidoDAO() {
		if (pedidoDAO == null) {
			pedidoDAO = new PedidoDAO();
		}
		return pedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("pedido", Pedido.class).withCodecRegistry(defaultCodecRegistry);
	}
	
	
	public List<Pedido> findPedidoByUser(Integer user) {
		
		Bson query_user = Filters.eq("cliente", user);
		FindIterable<Pedido> resultados = collection.find(query_user);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;	
		
	}
	
	
	
	public List<Pedido> findPedidoByRestaurant(Integer restaurant) {
		
		Bson query_restaurant=Filters.eq("restaurante",restaurant);
		FindIterable<Pedido> resultados = collection.find(query_restaurant);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;	
		
		
	}
	
	public void addRepartidor(Integer repartidor,Integer pedido) {
		
		
		
	}
	
}
