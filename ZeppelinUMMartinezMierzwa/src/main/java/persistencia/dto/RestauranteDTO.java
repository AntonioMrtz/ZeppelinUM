package persistencia.dto;

import java.io.Serializable;


public class RestauranteDTO implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String nombre;
	protected Double valoracionGlobal;

	public RestauranteDTO(Integer id, String nombre, Double valoracionGlobal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.valoracionGlobal = valoracionGlobal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getValoracionGlobal() {
		return valoracionGlobal;
	}

	public void setValoracionGlobal(Double valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}
	
	

	
	
}


