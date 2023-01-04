package web.restaurante;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import persistencia.dto.PlatoDTO;
import persistencia.dto.RestauranteDTO;
import persistencia.jpa.bean.Plato;
import persistencia.mongo.bean.ItemPedido;
import persistencia.mongo.bean.Pedido;
import web.usuario.UserSessionWeb;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteMenuList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FacesContext facesContext;
	private Integer idRestaurante;
	private List<PlatoDTO> menu;
	
	private PlatoDTO selectedPlato;
	private Integer cantidad;
	private String titulo;
	private String descripcion;
	private Double precio;
	private ServicioGestionPlataforma servicio;
	private RestauranteDTO restauranteSeleccionado;
	private Boolean[] boolArray;
	@Inject
    protected UserSessionWeb userSessionWeb;
	
	public Boolean[] getBoolArray() {
		return boolArray;
	}

	public void setBoolArray(Boolean[] boolArray) {
		this.boolArray = boolArray;
	}
	
	@PostConstruct
	public void load() {
		
		Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
		idRestaurante = Integer.parseInt(params.get("id"));
		System.out.println("thats the restaurant id: " + idRestaurante);
		menu = userSessionWeb.isRestaurante() ? servicio.getMenuByRestauranteAll(idRestaurante) : servicio.getMenuByRestaurante(idRestaurante);
		boolArray = new Boolean[menu.size()];
		for(int i = 0; i < menu.size(); i++) {
			boolArray[i] = menu.get(i).getDisponibilidad();
		}
	}
	
	public void selectPlato(PlatoDTO plato) {
		System.out.println(plato.getTitulo());
		 selectedPlato = plato;
		 System.out.println(plato.toString());
	}
	
	public void addToCart() {
		ItemPedido item = new ItemPedido(selectedPlato.getTitulo(), cantidad, selectedPlato.getPrecio(), selectedPlato.getId(), idRestaurante);
		Pedido.OrderBuilder p = userSessionWeb.getPedido();
		p.addItems(item);
		userSessionWeb.setPedido(p);
	}

	public RestauranteMenuList() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	// TODO change the name and update the plates with the values of the boolArray
	public void changeDisponibilidad() {
		for (int i = 0; i < boolArray.length; i++) {
			boolean isDisp = menu.get(i).getDisponibilidad();
			if (boolArray[i] != isDisp) {
				servicio.cambiarDisponibilidadPlato(menu.get(i).getId());
				menu.get(i).setAvailable(!isDisp);
			}
		}
	}

	public void loadMenu() {
		menu = servicio.getMenuByRestaurante(idRestaurante);
		boolArray = new Boolean[menu.size()];
		for (int i = 0; i < menu.size(); i++) {
			boolArray[i] = menu.get(i).getDisponibilidad();
		}
	}

	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
		restauranteSeleccionado = servicio.getRestaurante(idRestaurante);
	}

	public void toggleDisponibilidad(int id) {
		servicio.cambiarDisponibilidadPlato(id);
	}

	public Integer getidRestaurante() {
		return idRestaurante;
	}

	public List<PlatoDTO> getMenu() {
		return menu;
	}

	public void crearPlato() {
		Integer exito = servicio.nuevoPlato(titulo, descripcion, precio, idRestaurante);
		if (exito != null) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato creado", ""));
			loadMenu();
		}
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}

	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}

	public RestauranteDTO getRestauranteSeleccionado() {
		return restauranteSeleccionado;
	}

	public void setRestauranteSeleccionado(RestauranteDTO restauranteSeleccionado) {
		this.restauranteSeleccionado = restauranteSeleccionado;
	}

	public Integer getIdRestaurante() {
		return idRestaurante;
	}

	public void setMenu(List<PlatoDTO> menu) {
		this.menu = menu;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public PlatoDTO getSelectedPlato() {
		return selectedPlato;
	}


}