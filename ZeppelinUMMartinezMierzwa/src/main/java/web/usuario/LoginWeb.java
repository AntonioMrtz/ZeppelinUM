package web.usuario;

import java.io.Serializable;

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
public class LoginWeb implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected String email;
    protected String password;
    protected String redirect;
    @Inject
    protected FacesContext facesContext;
    @Inject
    protected UserSessionWeb userSessionWeb;

    @PostConstruct
    public void init() {
        String p = facesContext.getExternalContext().getRequestServletPath();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String s = request.getQueryString();
        if (s != null) {
            s = "?" + s;
        } else {
            s = "";
        }
        redirect = request.getContextPath()+ p + s;
    }

    public void login() {
        ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

        if (email == null || email.trim().equals("")) {
            facesContext.addMessage(null, 
               new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe indicar su email"));
            return;
        }
        if (password == null || password.trim().equals("")) {
            facesContext.addMessage(null, 
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe indicar su contraseña"));
            return;
        }

        UsuarioDTO usuario = servicio.login(email, password);
        if (usuario == null) {
            facesContext.addMessage(null, 
       new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email o contraseña no validos"));

        } else {
            userSessionWeb.setUsuario(usuario);
            try {
                facesContext.getExternalContext().redirect(redirect);
            } catch (Exception ex) {

            }
        }
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

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}





} 