package persistencia.jpa.dao;

import persistencia.mongo.bean.ItemPedido;

public class ItemPedidoDAO extends ExtensionDAO<ItemPedido> {

    public ItemPedidoDAO(Class<ItemPedido> persistedClass) {
        super(persistedClass);
    }

    private static ItemPedidoDAO itemDAO;

    public static ItemPedidoDAO getItemPedidoDAO() {
        if (itemDAO == null)
        	itemDAO = new ItemPedidoDAO(ItemPedido.class);
        return itemDAO;
    } 
    
}