package zeppelinum;

import java.util.List;

import javax.ejb.Remote;
import persistencia.dto.EstadisticaOpinionDTO;

@Remote
public interface ZeppelinUMRemoto { 
    public Integer getNumVisitas(Integer idUsuario);
    public void pedidoIniciado(String pedido);
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);
	public int findNumPedidoByUser(int id);
	public int getNumAllPedidos();
	public int findNumPedidoByUserDifferentRestaurant(Integer id);
	public int findPedidosRestaurants(List<Integer> l);
	public int findNumUsersRestaurants(List<Integer> findRestaurantIdByResponsable);
	public int findPedidoByUserDifferentRestaurant(Integer usuario);
	public int countDistinctClientsByRestaurante(List<Integer> restaurante);

}