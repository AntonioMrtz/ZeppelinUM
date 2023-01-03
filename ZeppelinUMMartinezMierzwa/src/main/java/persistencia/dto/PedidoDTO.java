package persistencia.dto;

import java.io.Serializable;
import java.util.List;



public class PedidoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// add id?
	private String comentarios;
	private double importe;
	private String direccion;
	private String restaurante;
	private String repartidor;
	private String cliente;
	private List<EstadoPedidoDTO> estados;
	private List<ItemPedidoDTO> items;
	
	
	
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}
	public String getRepartidor() {
		return repartidor;
	}
	public void setRepartidor(String repartidor) {
		this.repartidor = repartidor;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String client) {
		this.cliente = client;
	}
	public List<EstadoPedidoDTO> getEstados() {
		return estados;
	}
	public void setEstados(List<EstadoPedidoDTO> estados) {
		this.estados = estados;
	}
	public List<ItemPedidoDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemPedidoDTO> items) {
		this.items = items;
	}

	
	public static class PedidoDTOBuilder {
		private int restaurante;
		private int cliente;
	}
	
	
	
}
