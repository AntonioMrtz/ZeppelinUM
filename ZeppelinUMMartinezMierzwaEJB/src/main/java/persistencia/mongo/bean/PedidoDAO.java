package persistencia.mongo.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import persistencia.dto.EstadisticaOpinionDTO;

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
    @Lock(LockType.READ)
    public List<Document> calcularEstadisticas(Integer id) {
        Bson match = Aggregates.match(Filters.eq("restaurante",id));
        Bson group = Aggregates.group("$valor", Accumulators.sum("total", 1));

        try {
            AggregateIterable<Document> resultado = coleccion.aggregate(Arrays.asList(match, group));
            List<Document> estadisticas = new ArrayList<>();
            MongoCursor<Document> it = resultado.iterator();
            while (it.hasNext()) {
                estadisticas.add(it.next());
            }
            return estadisticas;
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
        return null;
    }

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
    
//    @Lock(LockType.WRITE)
//    @Lock(LockType.READ)
	public void actualizarEstadoCancelado(String id) {
		
		

        try {
//            AggregateIterable<Document> resultado = coleccion.aggregate(Arrays.asList(match));
//            List<Document> pedidos = new ArrayList<>();
//            MongoCursor<Document> it = resultado.iterator();
//            
//            List<Document> estados = null;
//            
//            while (it.hasNext()) {
//            	pedidos.add(it.next());
//            }
//            
//            for(Document r:pedidos) {
//            	
//            	estados = (List<Document>) r.get("estados");
//            }
//           
            Document cancelado=new Document("estado","CANCELADO");
            cancelado.append("fechaEstado",LocalDate.now());
//            estados.add(cancelado);
//            
//            coleccion.updateOne(match, cancelado);
        	
        	
        	Bson filter = Filters.eq("_id",new ObjectId(id));
        	Bson update = Updates.push("estados",cancelado);

        	coleccion.updateOne(filter, update);
            
            
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

	}
}