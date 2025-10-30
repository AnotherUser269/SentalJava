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
    public Optional<Order> findOrder(int id) {
        return orderCatalog.get(id);
    }
    public Order createOrder(int id) {
        int orderId = generateId();
        Order newOrder = new Order(orderId, id);
        orderCatalog.put(newOrder);
        orderArchive.put(newOrder);

        return newOrder;
    }
    public Optional<Order> removeOrder(int id) {
        Optional<Order> removedOrder = orderCatalog.remove(id);

        if(removedOrder.isPresent()) {
            removedOrder.get().setStatus(OrderStatus.SUCCESS);
        }

        return removedOrder;
    }

    private int generateId() {
        int startId = 0;

        while (orderArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
