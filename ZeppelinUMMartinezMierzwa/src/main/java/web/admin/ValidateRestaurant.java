package web.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import persistencia.dto.UsuarioDTO;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class ValidateRestaurant implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<UsuarioDTO> restaurantList;
	private ServicioGestionPlataforma servicio;

	public List<UsuarioDTO> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(List<UsuarioDTO> restaurantList) {
		this.restaurantList = restaurantList;
	}

	ValidateRestaurant() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		init();
	}

	public void init() {
		restaurantList = servicio.findNonValidatedRestauranteUsers();
		System.out.println(restaurantList.size());
	}

	public void validateUser(int id) {
		servicio.validarUsuario(id);
		restaurantList = servicio.findNonValidatedRestauranteUsers();
	}

}
