package persistencia.mongo.bean;

import java.io.Serializable;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Opinion implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@BsonId
    private ObjectId id;
    private Integer usuario;
    private Integer restaurante;
    private String opinion;
    private Double valor;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	public Integer getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}   

    

}