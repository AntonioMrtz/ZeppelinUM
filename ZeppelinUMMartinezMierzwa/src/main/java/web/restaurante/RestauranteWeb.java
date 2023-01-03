package web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import persistencia.dto.RestauranteDTO;
import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.dao.CategoriaRestauranteDAO;
import web.usuario.UserSessionWeb;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteWeb implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double latitudSelected;
    private Double longitudSelected;
    private String nombreRestaurante;
    private String calle;
    private String codigoPostal;
    private Integer numero;
    private String ciudad;
    private List<Integer> categorias;
    private MapModel<Integer> simpleModel;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserSessionWeb usuarioSesion;
    private Integer responsableId;
    private ServicioGestionPlataforma servicio;

    public RestauranteWeb() {
    	
    }

    public void initializeMapModel() {
    	servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    	System.out.println("executing");
    	simpleModel = new DefaultMapModel<Integer>();
    	System.out.println("loaded mapModel");
    	List<RestauranteDTO> restaurantList = servicio.searchRestaurantByResponsable(responsableId);
    	System.out.println("here i did it");
    	if(restaurantList == null) {
    		restaurantList = new ArrayList<RestauranteDTO>();
    	}
    	System.out.println("loaded restaurantList from responsable, size: " + restaurantList.size());
    	for(RestauranteDTO r: restaurantList) {
    		try {
    		LatLng coord = new LatLng(r.getLatitud(), r.getLongitud());
            simpleModel.addOverlay(new Marker<Integer>(coord, r.getNombre(), r.getId()));
    	}catch(Exception e) {
    		System.out.println("oops");
    	}	
    	}
    	
	}
	@PostConstruct
    public void obtenerUsuarioSesion() {
        responsableId = usuarioSesion.getUsuario().getId();
        initializeMapModel();
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        latitudSelected = latlng.getLat();
        longitudSelected = latlng.getLng();
    }

    public void onMarkerRestauranteSelect(OverlaySelectEvent<Integer> event) {
        Marker<Integer> marker = (Marker<Integer>) event.getOverlay();
        Integer restauranteSelectedId = (Integer) marker.getData();
        try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/formPlatos.xhtml?id=" + restauranteSelectedId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearRestaurante() {
        Integer restauranteId = servicio.registrarRestaurante(nombreRestaurante, responsableId, calle, codigoPostal,numero, ciudad, latitudSelected, longitudSelected, categorias);

        if (restauranteId == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El restaurante no se ha podido crear", ""));
        } else {
            LatLng coord = new LatLng(latitudSelected, longitudSelected);
            simpleModel.addOverlay(new Marker<Integer>(coord, nombreRestaurante, restauranteId));
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurante creado correctamente", ""));
        }
    }
	public Double getLatitudSelected() {
		return latitudSelected;
	}
	public void setLatitudSelected(Double latitudSelected) {
		this.latitudSelected = latitudSelected;
	}
	public Double getLongitudSelected() {
		return longitudSelected;
	}
	public void setLongitudSelected(Double longitudSelected) {
		this.longitudSelected = longitudSelected;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public List<CategoriaRestaurante> getAllCategorias() {
		
		return CategoriaRestauranteDAO.getCategoriaRestauranteDAO().getAll();
		//return categorias;
	}
	
	public List<Integer> getCategorias(){
		
		return this.categorias;
	}
	
	public void setCategorias(List<Integer> categorias) {
		this.categorias = categorias;
	}
	public MapModel<Integer> getSimpleModel() {
		return simpleModel;
	}
	public void setSimpleModel(MapModel<Integer> simpleModel) {
		this.simpleModel = simpleModel;
	}
	public FacesContext getFacesContext() {
		return facesContext;
	}
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
	public UserSessionWeb getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(UserSessionWeb usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	public Integer getResponsableId() {
		return responsableId;
	}
	public void setResponsableId(Integer responsableId) {
		this.responsableId = responsableId;
	}
	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}
	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}
  
    
    
    
}