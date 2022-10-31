package persistencia.dto;

import java.io.Serializable;

public class ItemPedidoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer cantidad;
	private Double precio;
//	private String plato;
	
	public ItemPedidoDTO(Integer cantidad,Double precio) {
		
		this.cantidad=cantidad;
		this.precio=precio;
	
		
	}
	
	
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
//	public String getPlato() {
//		return plato;
//	}
//	public void setPlato(String plato) {
//		this.plato = plato;
//	}
//	
	


}
