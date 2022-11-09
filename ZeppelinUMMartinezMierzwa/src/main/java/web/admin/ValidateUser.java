package web.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import persistencia.dto.UsuarioDTO;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class ValidateUser implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected String email;
    protected String password = "Hello";
    protected String redirect;
    protected List<UsuarioDTO> usuarios;
    protected boolean isValidated;


    @PostConstruct
    public void init() {
        usuarios = ServicioGestionPlataforma.getServicioGestionPlataforma().getAllUsers();
    }



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}



	public boolean isValidated() {
		return isValidated;
	}



	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	







} 