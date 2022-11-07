package web.usuario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import persistencia.dto.UsuarioDTO;
import persistencia.jpa.bean.TipoUsuario;

@Named
@SessionScoped
public class UserSessionWeb implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
}