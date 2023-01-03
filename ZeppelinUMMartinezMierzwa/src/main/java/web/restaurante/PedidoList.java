package web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.view.ViewScoped;

import persistencia.jpa.dao.RestauranteDAO;
import persistencia.mongo.bean.EstadoPedido;
import persistencia.mongo.bean.Pedido;
import persistencia.mongo.bean.TipoEstado;
import persistencia.mongo.dao.PedidoDAO;
import web.usuario.UserSessionWeb;

@Named
@ViewScoped
public class PedidoList implements Serializable {

	private static final long serialVersionUID = 1L;

	public PedidoList() {

	}

	private List<Integer> restaurantId;

	private Map<Integer, List<Pedido>> pedidos;

	private Integer pedidoId;
	@Inject
	private UserSessionWeb usuarioSesion;

	public void getProducts() {
		Map<Integer, List<Pedido>> pedidoMap = new HashMap<Integer, List<Pedido>>();
		for (int id : restaurantId) {
			List<Pedido> pedidoList = PedidoDAO.getPedidoDAO().findPedidoByRestaurant(id);
			pedidoMap.put(id, pedidoList);
		}
		pedidos = pedidoMap;
	}

	
	@PostConstruct
	public void obtenerUsuarioSesion() {
		System.out.println("Doing!!!");
		restaurantId = RestauranteDAO.getRestauranteDAO()
				.findRestaurantIdByResponsable(usuarioSesion.getUsuario().getId());
		getProducts();
	}

	public void goToDetailScreen(String id) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println("navigating to pedido number : " + id);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(ec.getRequestContextPath() + "/restaurante/pedidosList.xhtml?id=" + id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<Integer, List<Pedido>> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Map<Integer, List<Pedido>> pedidos) {
		this.pedidos = pedidos;
	}

	public Integer getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Integer pedidoId) {
		this.pedidoId = pedidoId;
	}

}