package persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EstadoPedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private LocalDateTime fechaEstado;
	private TipoEstado estado;
	
	
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
