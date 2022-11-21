package persistencia.dto;

import java.io.Serializable;


public class PlatoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String descripcion;
	protected String titulo;
	protected Double precio;
	protected boolean disponibilidad;

	public PlatoDTO(Integer id, String descripcion, String titulo, Double precio, boolean disponibilidad) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.titulo = titulo;
		this.precio = precio;
		this.disponibilidad = disponibilidad;
	}

	public Integer getId() {
		return id;
	}

	public boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setAvailable(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}




}
