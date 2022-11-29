package zeppelinum;

import java.util.List;

import javax.ejb.Remote;
import persistencia.dto.EstadisticaOpinionDTO;

@Remote
public interface ZeppelinUMRemoto { 
    public Integer getNumVisitas(Integer idUsuario);
    public void pedidoIniciado(String pedido);
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);

}