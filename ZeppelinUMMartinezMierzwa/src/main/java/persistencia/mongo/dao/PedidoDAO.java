package persistencia.mongo.dao;


import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import persistencia.mongo.bean.EstadoPedido;
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
	
	public void addRepartidor(Integer repartidor,ObjectId pedido) {
		
		
		Bson query_pedido=Filters.eq("_id",pedido);
		Bson query_repartido=Updates.set("repartidor",repartidor);
		
		
		collection.updateOne(query_pedido, query_repartido);

		
	}
	
	public void updatePedido(Object id,EstadoPedido e) {
		
		Bson query=Filters.eq("_id",id);
		Bson query_estado=Updates.push("estados",e);
		
		collection.updateOne(query, query_estado);
		
	}
	
	
	public void deleteAllPedidos() {
		
		collection.drop();
	}
	
}
