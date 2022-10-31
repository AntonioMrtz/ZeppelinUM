package persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Pedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@BsonId
    private ObjectId id;
	private LocalDateTime fechaHora ;
	private String comentarios;
	private double importe;
	private String direccion;
	private Integer restaurante;
	private Integer repartidor;
	private Integer cliente;
	private List<EstadoPedido> estado;
	//private List<ItemPedido> items;
	
	public Pedido() {
		
	}

	public Pedido(LocalDateTime fechaHora,String comentarios , double importe,String direccion,Integer restaurante,Integer repartidor,Integer cliente,List<EstadoPedido> estados) {
		
		
		this.fechaHora=fechaHora;
		this.comentarios=comentarios;
		this.importe=importe;
		this.direccion=direccion;
		this.restaurante=restaurante;
		this.repartidor=repartidor;
		this.cliente=cliente;
		this.estado=estados;
	}
	
	
	public Pedido(LocalDateTime fechaHora,String comentarios , double importe,String direccion,Integer restaurante,Integer repartidor,Integer cliente) {
		
		
		this.fechaHora=fechaHora;
		this.comentarios=comentarios;
		this.importe=importe;
		this.direccion=direccion;
		this.restaurante=restaurante;
		this.repartidor=repartidor;
		this.cliente=cliente;
	}
	
	
//	public Pedido(LocalDateTime fechaHora,String comentarios , double importe,String direccion,Integer restaurante,Integer repartidor,Integer cliente,List<EstadoPedido> estado,List<ItemPedido> items) {
//		
//		
//		this.fechaHora=fechaHora;
//		this.comentarios=comentarios;
//		this.importe=importe;
//		this.direccion=direccion;
//		this.restaurante=restaurante;
//		this.repartidor=repartidor;
//		this.cliente=cliente;
//		this.estado=estado;
//		this.items=items;
//	}
	


	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
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
	public Integer getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
	}
	public Integer getRepartidor() {
		return repartidor;
	}
	public void setRepartidor(Integer repartidor) {
		this.repartidor = repartidor;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public List<EstadoPedido> getEstados() {
		return estado;
	}
	public void setEstados(List<EstadoPedido> estado) {
		this.estado = estado;
	}
//	public List<ItemPedido> getItems() {
//		return items;
//	}
//	public void setItems(List<ItemPedido> items) {
//		this.items = items;
//	}
//	

	

	
	
	
}
