package persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import persistencia.mongo.bean.Opinion;

public class OpinionDAO extends ExtensionMongoDAO<Opinion> {

    private static OpinionDAO opinionDAO;

    public static OpinionDAO getOpinionDAO() {
        if (opinionDAO == null) {
            opinionDAO = new OpinionDAO();
        }
        return opinionDAO;
    }

    @Override
    public void createCollection() {
        collection = db.getCollection("opinion", Opinion.class).withCodecRegistry(defaultCodecRegistry);
    }
    
    public List<Opinion> findByRestaurante(Integer restaurante) {

        Bson query = Filters.eq("restaurante", restaurante);
        FindIterable<Opinion> resultados = collection.find(query);          
        MongoCursor<Opinion> it = resultados.iterator();
        List<Opinion> opiniones = new ArrayList<Opinion>();
        while (it.hasNext()) {
            opiniones.add(it.next());
        }
        return opiniones;
    }
    
    public List<Opinion> findByUsuario(Integer usuario) {

        Bson query = Filters.eq("usuario", usuario);
        FindIterable<Opinion> resultados = collection.find(query);          
        MongoCursor<Opinion> it = resultados.iterator();
        List<Opinion> opiniones = new ArrayList<Opinion>();
        while (it.hasNext()) {
            opiniones.add(it.next());
        }
        return opiniones;
    }
    
    public void deleteAllOpiniones() {
    	
    	collection.drop();
    	
    }
    
}