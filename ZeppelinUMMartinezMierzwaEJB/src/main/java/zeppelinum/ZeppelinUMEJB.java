package zeppelinum;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import persistencia.dto.EstadisticaOpinionDTO;
import persistencia.mongo.bean.OpinionDAO;

@Stateless(name="ZeppelinUMRemoto")
public class ZeppelinUMEJB implements ZeppelinUMRemoto{

    @EJB(beanName="OpinionDAO")
    private OpinionDAO opinionDAO;
    
    @EJB(beanName="Contador")
    private ContadorVisitasEJB contadorVisitas;
    
    @EJB(beanName="EstadoAutomatico")
    private IEstadoAutomatico estadoAutomatico;

    @Override
    public Integer getNumVisitas(Integer idUsuario) {
        return contadorVisitas.getVisitas(idUsuario);
    }

    @Override
    public void pedidoIniciado(String pedido) {
        estadoAutomatico.startUpTimer(pedido,30); //el timer saltará a los 30 minutos, 
                                                 //para hacer pruebas, puedes cambiar
                                                 //esto a un número de minutos más bajo     
    }

    @Override
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario) {
        contadorVisitas.nuevaVista(idUsuario);
        List<EstadisticaOpinionDTO> estadisticas = new ArrayList<>();
        
        List<Document> resultados = opinionDAO.calcularEstadisticas(idUsuario);
        for(Document r:resultados) {
            estadisticas.add(new EstadisticaOpinionDTO(r.getDouble("_id"), r.getInteger("total")));
        }       
        return estadisticas;
    }
}