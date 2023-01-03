package web.usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.mongo.bean.Pedido;

@Named
@SessionScoped
public class UserSessionWeb implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Pedido.OrderBuilder pedido = null;
	
	protected UsuarioDTO usuario;
         
    public boolean isLogin() {
        return usuario != null;
    }
    public boolean isAdmin() {
        return usuario.getTipo().equals(TipoUsuario.ADMIN);
    }
    public boolean isRestaurante() {
        return usuario.getTipo().equals(TipoUsuario.RESTAURANTE);
    }
    public boolean isRider() {
        return usuario.getTipo().equals(TipoUsuario.RIDER);
    }
   
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    public Pedido.OrderBuilder getPedido() {
    	return pedido;
    }
    

    public void setPedido(Pedido.OrderBuilder pedido) {
    	this.pedido = pedido;
    }
}