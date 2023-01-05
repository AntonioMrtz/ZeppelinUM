package persistencia.mongo.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

@Singleton(name="PedidoDAO")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class PedidoDAO {
    private MongoClient mongoClient;
    private MongoDatabase db;
    protected MongoCollection<Document> coleccion;
    
    @PostConstruct
    public void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        db = mongoClient.getDatabase("Zeppelinummartinezmierzwa");      
        coleccion = db.getCollection("pedido");       
        
    }
    
    @PreDestroy
    public void destroy() {
        mongoClient.close();
    }
//    @Lock(LockType.READ)
//    public List<Document> calcularEstadisticas(Integer id) {
//        Bson match = Aggregates.match(Filters.eq("restaurante",id));
//        Bson group = Aggregates.group("$valor", Accumulators.sum("total", 1));
//
//        try {
//            AggregateIterable<Document> resultado = coleccion.aggregate(Arrays.asList(match, group));
//            List<Document> estadisticas = new ArrayList<>();
//            MongoCursor<Document> it = resultado.iterator();
//            while (it.hasNext()) {
//                estadisticas.add(it.next());
//            }
//            return estadisticas;
//        } catch (RuntimeException re) {
//            re.printStackTrace();
//        }
//        return null;
//    }

    @Lock(LockType.READ)
	public boolean checkEstadoInicio(String id) {
		
		Bson match = Filters.eq("_id",new ObjectId(id));

        try {
        	
        	List<Document> pedidos = new ArrayList<>();

        	
        	
        	
            FindIterable<Document> resultado = coleccion.find(match);
            MongoCursor<Document> it = resultado.iterator();
            
            while (it.hasNext()) {
            	
            	pedidos.add(it.next());
            }
           
            for(Document r:pedidos) {
            	List<Document> estados = (List<Document>) r.get("estados");
            	return estados.size()==1;
            }
            
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
		
		return false;
		
	}

    
	public void actualizarEstadoCancelado(String id) {
		
		

        try {

            Document cancelado=new Document("estado","CANCELADO");
            cancelado.append("fechaEstado",LocalDate.now());
  	
        	
        	Bson filter = Filters.eq("_id",new ObjectId(id));
        	Bson update = Updates.push("estados",cancelado);

        	coleccion.updateOne(filter, update);
            
            
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

	}

	@Lock(LockType.READ)
	public int findNumPedidoByUser(int id) {
		
		Bson query_user = Filters.eq("cliente", id);
		return (int) coleccion.countDocuments(query_user);


//		int count = 0;
//
//		Bson query_user = Filters.eq("cliente", id);
//		FindIterable<Document> resultados = coleccion.find(query_user);
//		MongoCursor<Document> it = resultados.iterator();
//		while (it.hasNext()) {
//
//			count++;
//		}
//		return count;
	}

	@Lock(LockType.READ)
	public int getNumAllPedidos() {
		
		return (int) coleccion.countDocuments();
		

//		int count = 0;
//
//		FindIterable<Document> res = coleccion.find();
//		MongoCursor<Document> it = res.iterator();
//		while (it.hasNext()) {
//
//			count++;
//		}
//		return count;
	}

	@Lock(LockType.READ)
	public int findNumPedidoByUserDifferentRestaurant(Integer id) {
		
		Bson query_restaurant=Filters.eq("cliente",id);

		return (int) coleccion.countDocuments(query_restaurant);
		
//		int count=0;
//		Bson query_restaurant=Filters.eq("usuario",id);
//		MongoCursor<String> it=coleccion.distinct("restaurante", query_restaurant,String.class).iterator();
//		
//		while (it.hasNext()) {
//			count++;
//		}
//		return count;	
	}

	@Lock(LockType.READ)
	public int findPedidosRestaurants(List<Integer> l) {
		
			
			Bson query=Filters.in("restaurante",l);
			
			return (int) coleccion.countDocuments(query);
			
	}

	public int findNumUsersRestaurants(List<Integer> l) {
		
		Bson query=Filters.in("restaurante",l);
		
		if (coleccion.distinct("usuario",String.class).filter(query) instanceof Collection) {
		    return ((Collection<?>) coleccion.distinct("usuario",String.class).filter(query)).size();
		}
		
		return 0;

//		
//		Bson query=Filters.in("restaurante",l);
//		int count=0;
//		
//		MongoCursor<String> it=collection.distinct("usuario", query,String.class).iterator();
//		
//		while (it.hasNext()) {
//			count++;
//		}
//		return count;	
	}

	public int findPedidoByUserDifferentRestaurant(Integer usuario) {

		
		Bson query_restaurant=Filters.eq("cliente",usuario);
		
		if (coleccion.distinct("cliente",String.class).filter(query_restaurant) instanceof Collection) {
		    return ((Collection<?>) coleccion.distinct("restaurante",String.class).filter(query_restaurant)).size();
		}
		
		return 0;
		
		
//		int count=0;
//		
//		Bson query_restaurant=Filters.eq("usuario",us);
//		MongoCursor<String> it=collection.distinct("restaurante", query_restaurant,String.class).iterator();
//		
//		while (it.hasNext()) {
//			count++;
//		}
//		return count;
//		return 0;
	}
	
	
	
}
