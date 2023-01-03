package persistencia.mongo.bean;

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

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

@Singleton(name="OpinionDAO")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class OpinionDAO {
    private MongoClient mongoClient;
    private MongoDatabase db;
    protected MongoCollection<Document> coleccion;
    
    @PostConstruct
    public void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        db = mongoClient.getDatabase("Zeppelinummartinezmierzwa");      
        coleccion = db.getCollection("opinion");        
    }
    
    @PreDestroy
    public void destroy() {
        mongoClient.close();
    }
    @Lock(LockType.READ)
    public List<Document> calcularEstadisticas(Integer idUsuario) {
        Bson match = Aggregates.match(Filters.eq("usuario",idUsuario));
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
}