package persistencia.mongo.bean;

public enum TipoEstado {
    INCIADO("INCIADO"),
    ACEPTADO("ACEPTADO"),
    PREPARADO("PREPARADO"),
    RECOGIDO("RECOGIDO"),
    ENTREGADO("ENTREGADO"),
    CANCELADO("CANCELADO"),
    ERROR("ERROR");
	
    private String state;
    
    private TipoEstado() {
    	
    }
    
    TipoEstado(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
    	this.state = state;
    }
}



