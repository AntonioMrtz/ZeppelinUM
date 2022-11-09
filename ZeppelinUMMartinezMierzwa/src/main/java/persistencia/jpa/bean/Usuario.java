package persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

//@NamedQuery(name = "Usuario.findByEmailClave", query = " SELECT u FROM Usuario u WHERE u.email = :email and u.clave = :clave and u.validado=true"),


@NamedQueries({
	@NamedQuery(name = "Usuario.findByEmailClave", query = " SELECT u FROM Usuario u WHERE u.email = :email and u.clave = :clave"),
	@NamedQuery(name = "Usuario.findByEmail", query = " SELECT u FROM Usuario u WHERE u.email = :email "),
	@NamedQuery(name = "Usuario.findByName", query = " SELECT u FROM Usuario u WHERE u.nombre = :nombre and u.apellidos = :apellidos "),
	@NamedQuery(name = "Usuario.findNonValidatedRestauranteUsers", query = " SELECT u FROM  Usuario u WHERE u.tipo = :restaurante and u.validado = false")})

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "clave")
    private String clave;
    @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name="tipo")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @Column(name="validado")
    private boolean validado;
    
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Incidencia> incidencias; 

    
    
    public Usuario() {
        super();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
        
	public void addIncidencias(List<Incidencia> incidencias) {
		this.incidencias.addAll(incidencias);
	}
	
}