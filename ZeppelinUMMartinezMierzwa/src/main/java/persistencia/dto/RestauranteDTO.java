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

	private Double longitud;
	private Double latitud;
	private String calle;
	private String codigoPostal;
	private String ciudad;
	private Integer numero;

	public RestauranteDTO(Integer id, String nombre, Double valoracionGlobal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.valoracionGlobal = valoracionGlobal;
	}

	public RestauranteDTO(Integer id, String nombre, Double valoracionGlobal, Double longitud, Double latitud,
			String calle, String codigoPostal, String ciudad, Integer numero) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.valoracionGlobal = valoracionGlobal;
		this.longitud = longitud;
		this.latitud = latitud;
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.ciudad = ciudad;
		this.numero = numero;
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

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	
	
}
