package persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "incidencia")
public class Incidencia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "fecha_creacion", columnDefinition = "DATE")
	private LocalDate fechaCreacion;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "fecha_cierre", columnDefinition = "DATE")
	private LocalDate fechaCierre;
	@Column(name = "comentario_cierre")
	private String comentarioCierre;

	@ManyToOne
	@JoinColumn(name="restaurante")
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;

	
	public Incidencia() {
		
		super();
		
	}
	
	//TODO constructor with parameters

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

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	
}
