package web.admin;

import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import persistencia.jpa.bean.TipoUsuario;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RegistroRider implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
    private String apellidos;
    private String correo;
    private String clave;
    private String clave2;
    private LocalDate fechaNacimiento;
    private String tipo="RIDER";
    @Inject
    protected FacesContext facesContext;

    public void registro() {
        /**
         * TODO Comprobar que los campos no llegan vacíos Comprobar que clave y clave2
         * son iguales
         */
        if (ServicioGestionPlataforma.getServicioGestionPlataforma().isUsuarioRegistrado(correo)) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un usuario con el email " + correo));
            return;
        }
        Integer idUser = ServicioGestionPlataforma.getServicioGestionPlataforma().registrarUsuario(nombre, apellidos,fechaNacimiento, correo, clave, TipoUsuario.valueOf(tipo));
        if (idUser != null) {
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario registrado correctamente"));
        } else {
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usurio no ha podido ser registrado"));
        }
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}


    
    
}