package web.usuario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import persistencia.dto.RestauranteDTO;
import persistencia.jpa.dao.RestauranteDAO;


@Named
@SessionScoped
public class RestaurantListView implements Serializable {

    private List<RestauranteDTO> restaurants;

    private RestauranteDTO selectedRestaurante;

    RestaurantListView(){
    	init();
    }
    
    public void init() {
    	restaurants = RestauranteDAO.getRestauranteDAO().getAllRestaurantes();

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
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }
}