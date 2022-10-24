package persistencia.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class IncidenciaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private LocalDate fechaCreacion;
	private String descripcion;
	private LocalDate fechaCierre;
	private String comentarioCierre;
//	private String usuario;
//	private String restaurante;
	
	public IncidenciaDTO(Integer id,LocalDate fechaCreacion,String descripcion,LocalDate fechaCierre, String comentarioCierre) {
	
		this.id=id;
		this.fechaCierre=fechaCierre;
		this.descripcion=descripcion;
		this.fechaCreacion=fechaCreacion;
		this.comentarioCierre=comentarioCierre;
//		this.usuario=usuario;
//		this.restaurante=restaurante;	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getComentarioCierre() {
		return comentarioCierre;
	}

	public void setComentarioCierre(String comentarioCierre) {
		this.comentarioCierre = comentarioCierre;
	}

//	public String getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(String usuario) {
//		this.usuario = usuario;
//	}
//
//	public String getRestaurante() {
//		return restaurante;
//	}
//
//	public void setRestaurante(String restaurante) {
//		this.restaurante = restaurante;
//	}



	
	
}
