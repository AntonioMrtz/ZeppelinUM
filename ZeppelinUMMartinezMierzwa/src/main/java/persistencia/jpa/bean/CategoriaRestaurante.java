package persistencia.jpa.bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="categoriaRestaurante")
public class CategoriaRestaurante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="categoria")
	private String categoria;
	
	public CategoriaRestaurante() {
		super();
	}
	
	public CategoriaRestaurante(String categoria) {
		
		this.categoria=categoria;
	}
	
    @ManyToMany
    @JoinTable(name="categoria_id",
    
    			joinColumns= {@JoinColumn(name="categoria_id")}
    		)
    private List<Restaurante> restaurantes;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
	
    
}
