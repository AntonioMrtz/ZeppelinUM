package persistencia.mongo.bean;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class ItemPedido implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@BsonId
//	private ObjectId id;
	private Integer cantidad;
	private Double precio;
	private Integer plato;
	
	
	public ItemPedido() {
		
		
	}
	
	public ItemPedido(Integer cantidad,Double precio,Integer plato) {
		
		this.cantidad=cantidad;
		this.precio=precio;
		this.plato=plato;
	}
	
	
//	public ObjectId getId() {
//		return id;
//	}
//	public void setId(ObjectId id) {
//		this.id = id;
//	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getPlato() {
		return plato;
	}
	public void setPlato(Integer plato) {
		this.plato = plato;
	}
	
	
	
	
}
