package web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;

import persistencia.dto.RestauranteDTO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.mongo.bean.Pedido;

@Named
@SessionScoped
public class RestaurantListView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RestauranteDTO> restaurants;

	private RestauranteDTO selectedRestaurante;

	private List<FilterMeta> filterBy;

	private boolean globalFilterOnly;

	@Inject
	private UserSessionWeb usuarioSesion;

	RestaurantListView() {
		init();
	}

	public void init() {
		restaurants = RestauranteDAO.getRestauranteDAO().getAllRestaurantes();

	}

	public void toggleGlobalFilter() {
		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	public void goToDetailScreen(String id) {
		Pedido.OrderBuilder pedido = null;
		try {
			System.out.println(id);
			pedido = (Pedido.OrderBuilder) usuarioSesion.getPedido();
			System.out.println("pedido is: " + pedido);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// If pedido is null or we start a pedido from a new restaurante, create a new pedido
		if (pedido == null || pedido.getRestaurante() != Integer.parseInt(id)) {
			Integer userId = usuarioSesion.getUsuario().getId();
			Pedido.OrderBuilder pob = new Pedido.OrderBuilder().setCliente(userId).setRestaurante(Integer.parseInt(id));
			usuarioSesion.setPedido(pob);
		}
		System.out.println("did it after pedido");
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		System.out.println("navigating to restaurant number : " + id);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(ec.getRequestContextPath() + "/restaurante/formPlatos.xhtml?id=" + id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<RestauranteDTO> getRestaurants() {
		return restaurants;
	}

	public RestauranteDTO getSelectedProduct() {
		return selectedRestaurante;
	}

	public void setSelectedProduct(RestauranteDTO selectedRestaurante) {
		this.selectedRestaurante = selectedRestaurante;
	}

	public void clearMultiViewState() {
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		PrimeFaces.current().multiViewState().clearAll(viewId, true, this::showMessage);
	}

	private void showMessage(String clientId) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}
}