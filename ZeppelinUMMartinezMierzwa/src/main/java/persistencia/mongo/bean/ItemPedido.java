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
	private String titulo;
	private Integer cantidad;
	private Double precio;
	private Integer plato;
	private Integer idRestaurante;
	
	
	public ItemPedido() {
		
		
	}
	
	public ItemPedido(String titulo, Integer cantidad,Double precio,Integer plato, Integer idRestaurante) {
		
		this.titulo = titulo;
		this.cantidad=cantidad;
		this.precio=precio;
		this.plato=plato;
		this.idRestaurante = idRestaurante;
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
	public Integer getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}
