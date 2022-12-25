package persistencia.dto;

import java.io.Serializable;

public class EstadisticaOpinionDTO implements Serializable{ 
    private static final long serialVersionUID = 1L;
	private Double nota;
    private Integer total;
    
    public EstadisticaOpinionDTO(Double nota, Integer total) {
        super();
        this.nota = nota;
        this.total = total;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }       
}