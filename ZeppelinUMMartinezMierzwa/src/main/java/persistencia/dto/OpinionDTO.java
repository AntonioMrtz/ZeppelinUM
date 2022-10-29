package persistencia.dto;

import java.io.Serializable;


public class OpinionDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nombreUsuario;
    private String nombreRestaurante;
    private String comentario;
    private Double valoracion;
    
    
    public OpinionDTO(String comentario,Double valoracion,String nombreUsuario) {
    	
    	this.comentario=comentario;
    	this.valoracion=valoracion;
    	this.nombreUsuario=nombreUsuario;
    	
    }
    
   public OpinionDTO(String comentario,Double valoracion) {
    	
    	this.comentario=comentario;
    	this.valoracion=valoracion;
    	
    }
   
    
    public OpinionDTO() {
    	
    }
    
    
    
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Double getValoracion() {
		return valoracion;
	}
	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}
	
	
    
    
}