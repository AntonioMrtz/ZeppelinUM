package persistencia.dto;

import java.io.Serializable;


public class CategoriaRestauranteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer id;
	private String categoria;
	
	public CategoriaRestauranteDTO(Integer id,String categoria) {
		super();
		this.id=id;
		this.categoria=categoria;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	


}
