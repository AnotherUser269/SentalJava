package manager;

import archive.OrderArchive;
import catalog.OrderCatalog;
import core.Order;
import enums.OrderStatus;

import java.util.Optional;

public class OrderManager {
    private final OrderCatalog orderCatalog;
    private final OrderArchive orderArchive;

    public OrderManager(OrderCatalog orderCatalog, OrderArchive orderArchive) {
        this.orderCatalog = orderCatalog;
        this.orderArchive = orderArchive;
    }

    /**
     * Finds an order by its identifier.
     *
     * @param id order identifier
     * @return {@link Optional} containing the {@link Order} if found, or empty otherwise
    */
    public Optional<Order> findOrder(int id) {
        return orderArchive.find(id);
    }

    /**
     * Creates a new order for the given book id.
     * Generates a unique order id, creates an Order, stores it in the catalog and archive.
     *
     * @param id book identifier associated with the new order
     * @return the created {@link Order}
    */
    public Order createOrder(int id) {
        int orderId = generateId();
        Order newOrder = new Order(orderId, id);
        orderCatalog.put(newOrder);
        orderArchive.put(newOrder);

        return newOrder;
    }

    /**
     Removes an order by its identifier.
     Delegates removal to the catalog and, if removed, sets the order status to the given value.

     @param id identifier of the order to remove
     @param orderStatus the status to assign to the order after removal
     @return {@link Optional} containing the removed {@link Order} if it existed, or empty otherwise
    */
    public Optional<Order> removeOrder(int id, OrderStatus orderStatus) {
        Optional<Order> removedOrder = orderCatalog.remove(id);

        if(removedOrder.isPresent()) {
            removedOrder.get().setStatus(orderStatus);
        }

        return removedOrder;
    }

    /**
     * Generates a new unique order identifier.
     * Starts from 0 and increments until an id is found that does not exist in the archive.
     *
     * @return new unique order id
    */
    private int generateId() {
        int startId = 0;

        while (orderArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
