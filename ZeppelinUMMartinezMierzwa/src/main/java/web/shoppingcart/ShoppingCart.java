package web.shoppingcart;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import persistencia.mongo.bean.EstadoPedido;
import persistencia.mongo.bean.ItemPedido;
import persistencia.mongo.bean.Pedido;
import persistencia.mongo.bean.TipoEstado;
import persistencia.mongo.dao.PedidoDAO;
import web.usuario.UserSessionWeb;

@Named
@ViewScoped
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido.OrderBuilder pedido;

	private LocalTime deliveryTime;
	
	private String datosDireccion;

	private String comentario;

	@Inject
	private FacesContext facesContext;

	@Inject
	private UserSessionWeb usuarioSesion;

	@PostConstruct
	public void updatePedido() {
		try {
			pedido = usuarioSesion.getPedido();
		} catch (Exception e) {
			System.out.println("something went wrong with updating the pedido‚");
		}
	}

	public void decreaseQuantity(ItemPedido item) {
	    // Get the index of the item in the list
		System.out.println("hallo");
	    int index = pedido.getItems().indexOf(item);
	    System.out.println("updating item nr: " + index);
	    ItemPedido existingItem = pedido.getItems().get(index);

	    // Decrease the quantity of the item
	    int newQuantity = existingItem.getCantidad() - 1;
	    if (newQuantity > 0) {
	        // If the quantity is greater than zero, update the item
	        existingItem.setCantidad(newQuantity);
	    } else {
	        // If the quantity is zero or less, delete the item
	        pedido.getItems().remove(index);
	       
	    }
	}

	public void increaseQuantity(ItemPedido item) {
	    // Get the index of the item in the list
		System.out.println("hallo");
	    int index = pedido.getItems().indexOf(item);
	    System.out.println("updating item nr: " + index);
	    ItemPedido existingItem = pedido.getItems().get(index);

	    // Decrease the quantity of the item
	    int newQuantity = existingItem.getCantidad() + 1;
	    if (newQuantity > 0) {
	        // If the quantity is greater than zero, update the item
	        existingItem.setCantidad(newQuantity);
	    } else {
	        // If the quantity is zero or less, delete the item
	        pedido.getItems().remove(index);
	    }
	}

	
	public double calculateTotal() {
		double total = 0;
		if (pedido != null) {
			for (ItemPedido ip : pedido.getItems()) {
				total += ip.getPrecio() * ip.getCantidad();
			}
		}
		return total;
	}

	public void showShoppingCart() {
		updatePedido();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(ec.getRequestContextPath() + "/shoppingCart/shoppingCart.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pedido.OrderBuilder getPedido() {
		return pedido;
	}

	public void placeOrder() {
		System.out.println(datosDireccion);
		if (datosDireccion == null || datosDireccion.isEmpty() || deliveryTime == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid address and/or time", null));
		} else {
			Pedido finalPedido = pedido.setDireccion(datosDireccion).setComentarios(comentario)
					.setImporte(calculateTotal()).setFechaHora(deliveryTime).build();
			finalPedido.addEstado(new EstadoPedido(LocalDateTime.now(), TipoEstado.INCIADO));
			PedidoDAO.getPedidoDAO().save(finalPedido);
			usuarioSesion.setPedido(null);
			pedido = null;
			deliveryTime = null;
			comentario = "";
			datosDireccion = "";
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Order has been successfully added!", null));
		}
	}

	public void setPedido(Pedido.OrderBuilder pedido) {
		this.pedido = pedido;
	}

	public String getDatosDireccion() {
		return datosDireccion;
	}

	public void setDatosDireccion(String datosDireccion) {
		this.datosDireccion = datosDireccion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalTime deliveryTime) {
		System.out.println("am using the setter");
		this.deliveryTime = deliveryTime;

	}

}
