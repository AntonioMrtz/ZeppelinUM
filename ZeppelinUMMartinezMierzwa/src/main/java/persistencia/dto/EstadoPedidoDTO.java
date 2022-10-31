package persistencia.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import persistencia.mongo.bean.TipoEstado;


public class EstadoPedidoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime fechaEstado;
	private TipoEstado estado;
	
	
	public EstadoPedidoDTO(LocalDateTime fechaEstado,TipoEstado estado) {
		
		this.estado=estado;
		this.fechaEstado=fechaEstado;
		
	}
	
	
	public LocalDateTime getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(LocalDateTime fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public TipoEstado getEstado() {
		return estado;
	}
	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
	
	
	
	

}
