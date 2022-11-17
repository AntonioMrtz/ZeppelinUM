package web.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.ViewScoped;

import persistencia.jpa.bean.TipoUsuario;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class InfoUsuariosWeb implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	@Push(channel = "canalNoticias")
	private PushContext canal;
	@Inject
	private FacesContext facesContext;
	protected String message;
	protected List<String> tipos;

	public void submit() {
		System.out.println("sending message: " + message);
		System.out.println("To these tipos:");
		for(String s: tipos) {
			System.out.println(s);
		}
        if(message == null || message.isBlank()) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Indique el mensaje que desea enviar", ""));
return; }
        if(tipos == null || tipos.isEmpty()) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe indicar los tipos de usuarios quedeben recibir el mensaje", ""));
return; }
        List<TipoUsuario> enumtipos = new ArrayList<>();
        for(String t:tipos) {
        	enumtipos.add(TipoUsuario.valueOf(t));
        }
        List<Integer> usuarios =
ServicioGestionPlataforma.getServicioGestionPlataforma().getIdUsuariosByTipo(enumtipos);
        canal.send(message,usuarios);
        message=null;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}
}