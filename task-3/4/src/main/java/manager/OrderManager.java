package manager;

import archive.OrderArchive;
import core.Order;
import enums.OrderStatus;

import java.util.Optional;

public class OrderManager {
    private final OrderArchive orderArchive;

    public OrderManager(OrderArchive orderArchive) {
        this.orderArchive = orderArchive;
    }
    public Optional<Order> findOrder(int id) {
        return orderArchive.get(id);
    }
    public Order createOrder(int id) {
        int orderId = generateId();
        Order newOrder = new Order(orderId, id);
        orderArchive.put(newOrder);

        return newOrder;
    }
    public Optional<Order> removeOrder(int id) {
        Optional<Order> removedOrder = orderArchive.remove(id);

        if(removedOrder.isPresent()) {
            removedOrder.get().setStatus(OrderStatus.SUCCESS);
        }

        return removedOrder;
    }

    private int generateId() {
        int startId = 0;

        while (orderArchive.get(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
