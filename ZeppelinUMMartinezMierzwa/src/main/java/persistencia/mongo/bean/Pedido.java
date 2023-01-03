package persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import persistencia.mongo.dao.PedidoDAO;


public class Pedido implements Serializable {
	 
	public Pedido() {}
	
	private static final long serialVersionUID = 1L;
	@BsonId
	private ObjectId id;
	private LocalDateTime fechaHora;
	private String comentarios;
	private double importe;
	private String direccion;
	private Integer restaurante;
	private Integer repartidor;
	private Integer cliente;
	private List<EstadoPedido> estados;
	private List<ItemPedido> items;

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fechaHora=" + fechaHora + ", comentarios=" + comentarios + ", importe=" + importe
				+ ", direccion=" + direccion + ", restaurante=" + restaurante + ", repartidor=" + repartidor
				+ ", cliente=" + cliente + ", estados=" + estados + ", items=" + items + "]";
	}
	public void accept() {
		this.addEstado(new EstadoPedido(LocalDateTime.now(), TipoEstado.ACEPTADO));
		PedidoDAO.getPedidoDAO().updatePedido(id, getNewestState());
	}
	public void cancel() {
		this.addEstado(new EstadoPedido(LocalDateTime.now(), TipoEstado.CANCELADO));
		PedidoDAO.getPedidoDAO().updatePedido(id, getNewestState());
	}
	public void prepare() {
		this.addEstado(new EstadoPedido(LocalDateTime.now(), TipoEstado.PREPARADO));
		PedidoDAO.getPedidoDAO().updatePedido(id, getNewestState());
	}
	public void receive() {
		this.addEstado(new EstadoPedido(LocalDateTime.now(), TipoEstado.RECOGIDO));
		PedidoDAO.getPedidoDAO().updatePedido(id, getNewestState());
	}

	public EstadoPedido getNewestState(){
		EstadoPedido ep = null;
		if(estados.size() > 0) {
		ep = estados.get(estados.size() - 1);
		System.out.println(ep.getEstado());
		}
		return ep;
	}
	
	private Pedido(OrderBuilder builder) {
		LocalDateTime now = LocalDateTime.now();
		this.fechaHora = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), builder.fechaHora.getHour(), builder.fechaHora.getMinute());
		this.comentarios = builder.comentarios;
		this.cliente = builder.cliente;
		this.importe = builder.importe;
		this.direccion = builder.direccion;
		this.restaurante = builder.restaurante;
		this.repartidor = builder.repartidor;
		this.estados = builder.estado;
		this.items = builder.items;
	}

	public void setEstados(List<EstadoPedido> estado) {
		this.estados = estado;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
	}

	public void setRepartidor(Integer repartidor) {
		this.repartidor = repartidor;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}

	public boolean addEstado(EstadoPedido e) {
		estados.add(e);
		return true;
	}

	public ObjectId getId() {
		return id;
	}


	public LocalDateTime getFechaHora() {
		return fechaHora;
	}


	public String getComentarios() {
		return comentarios;
	}


	public double getImporte() {
		return importe;
	}


	public String getDireccion() {
		return direccion;
	}



	public Integer getRestaurante() {
		return restaurante;
	}



	public Integer getRepartidor() {
		return repartidor;
	}



	public Integer getCliente() {
		return cliente;
	}



	public List<EstadoPedido> getEstados() {
		return estados;
	}



	public List<ItemPedido> getItems() {
		return items;
	}



	public static class OrderBuilder {
		@BsonId
		private ObjectId id;
		private LocalTime fechaHora;
		private String comentarios;
		private double importe;
		private String direccion;
		private Integer restaurante;
		private Integer repartidor;
		private Integer cliente;
		private List<EstadoPedido> estado = new ArrayList<EstadoPedido>();
		private List<ItemPedido> items = new ArrayList<ItemPedido>();

		
		public LocalTime getFechaHora() {
			return fechaHora;
		}

		public List<ItemPedido> getItems(){
			return items;
		}
		
		public ObjectId getId() {
			return id;
		}
		
		public Integer getRestaurante() {
			return restaurante;
		}
		
		public OrderBuilder setFechaHora(LocalTime fechaHora) {
			this.fechaHora = fechaHora;
			return this;
		}

		public OrderBuilder setComentarios(String comentarios) {
			this.comentarios = comentarios;
			return this;
		}

		public OrderBuilder setImporte(double importe) {
			this.importe = importe;
			return this;
		}

		public OrderBuilder setDireccion(String direccion) {
			this.direccion = direccion;
			return this;
		}
		
		public OrderBuilder setRestaurante(Integer restaurante) {
			this.restaurante = restaurante;
			return this;
		}
		
		public OrderBuilder setRepartidor(Integer repartidor) {
			this.repartidor = repartidor;
			return this;
		}
		
		public OrderBuilder setCliente(Integer cliente) {
			this.cliente = cliente;
			return this;
		}

		public OrderBuilder addItems(ItemPedido item) {
			  // Check if the item already exists in the list
		    Optional<ItemPedido> existingItem = items.stream().filter(i -> i.getTitulo().equals(item.getTitulo())).findFirst();
		    if (existingItem.isPresent()) {
		        // If the item exists, update the quantity
		        existingItem.get().setCantidad(existingItem.get().getCantidad() + item.getCantidad());
		    } else {
		        // If the item does not exist, add it to the list
		        items.add(item);
		    }
		    return this;
		}
		
		public OrderBuilder addEstado(EstadoPedido estado) {
			this.estado.add(estado);
			return this;
		}

		public Pedido build() {
			return new Pedido(this);
		}
	}
}
