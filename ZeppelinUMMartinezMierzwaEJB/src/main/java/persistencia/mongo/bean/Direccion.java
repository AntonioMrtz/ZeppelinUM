package persistencia.mongo.bean;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.mongodb.client.model.geojson.Point;

public class Direccion implements Serializable{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@BsonId
    private ObjectId id;
    private Integer restaurante;
    private Point coordenadas;
    private String calle;
    private String codigoPostal; 
    private String ciudad;
    private Integer numero;
    
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
	}
	public Point getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(Point coordenadas) {
		this.coordenadas = coordenadas;
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