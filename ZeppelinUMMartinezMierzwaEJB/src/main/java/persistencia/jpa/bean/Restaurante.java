package persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = "Restaurante.findByName", query = " SELECT r FROM Restaurante r WHERE r.nombre= :nombre"),
	//@NamedQuery(name = "Restaurante.findByResponsable", query = " SELECT r FROM Restaurante r WHERE r.responsable= :responsable")
	@NamedQuery(name = "Restaurante.findByResponsable", query =" SELECT r FROM Restaurante r INNER JOIN r.responsable re WHERE re.id = :responsable")
	
})

@Entity
@Table(name = "restaurante")
public class Restaurante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;  
    @Column(name ="fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
    @Column(name ="valoracion_global")
    private Double valoracionGlobal;
    @Column(name ="num_valoraciones")
    private Integer numValoraciones;
    @Column(name ="num_penalizaciones")
    private Integer numPenalizaciones;
    
    
    @JoinColumn(name = "responsable")
    @ManyToOne
    private Usuario responsable;
    
//    @ManyToMany(mappedBy = "restaurantes")
//    private List<CategoriaRestaurante> categorias;
//    
//    @JoinColumn(name="incidencia")
//    @OneToMany(mappedBy="restaurante",cascade = CascadeType.ALL)
//    private List<Incidencia> incidencias;
//    
//    
//    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
//    private List<Plato> platos;
    

    private static final long serialVersionUID = 1L;

    public Restaurante() {
        super();
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

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Double getValoracionGlobal() {
		return valoracionGlobal;
	}

	public void setValoracionGlobal(Double valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}

	public Integer getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(Integer numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public Integer getNumPenalizaciones() {
		return numPenalizaciones;
	}

	public void setNumPenalizaciones(Integer numPenalizaciones) {
		this.numPenalizaciones = numPenalizaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setResponsable(Usuario u) {
		responsable=u;
		
	}

	public Usuario getResponsable() {
		return responsable;
	}

//	public List<CategoriaRestaurante> getCategorias() {
//		return new LinkedList<>(categorias);
//	}
//
//	public List<Incidencia> getIncidencias() {
//		return incidencias;
//	}
//
//	public void setIncidencias(List<Incidencia> incidencias) {
//		this.incidencias = incidencias;
//	}
//	
//	public void addIncidencias(List<Incidencia> incidencias) {
//		this.incidencias.addAll(incidencias);
//	}
//
//	public void setCategorias(List<CategoriaRestaurante> categoria) {
//		this.categorias = categoria;
//	}
//  
//	public void addCategorias(List<CategoriaRestaurante> categorias) {
//		this.categorias.addAll(categorias);
//	}
//
//	public List<Plato> getPlatos() {
//		return platos;
//	}
//
//	public void setPlatos(List<Plato> platos) {
//		this.platos = platos;
//	}
//    
//	public void addPlato(List<Plato> platos) {
//		this.platos.addAll(platos);
//	}
    
}