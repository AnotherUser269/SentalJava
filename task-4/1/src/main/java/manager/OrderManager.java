package manager;

import archive.OrderArchive;
import catalog.OrderCatalog;
import core.Order;
import status_enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Optional;

public class OrderManager {
    private final OrderCatalog orderCatalog;
    private final OrderArchive orderArchive;

    public OrderManager(OrderCatalog orderCatalog, OrderArchive orderArchive) {
        this.orderCatalog = orderCatalog;
        this.orderArchive = orderArchive;
    }

    public Optional<Order> findOrder(int id) {
        return orderArchive.find(id);
    }

    public Order createOrder(int id, long startTime, String phoneNumber, BigDecimal deliveryPrice) {
        int orderId = generateId();

        if (startTime < 0) {
            startTime = System.currentTimeMillis() / 1000L;
        }

        Order newOrder = new Order(orderId, id, startTime, phoneNumber, deliveryPrice);
        orderCatalog.put(newOrder);
        orderArchive.put(newOrder);

        return newOrder;
    }

    public Optional<Order> removeOrder(int id, OrderStatus orderStatus) {
        Optional<Order> removedOrder = orderCatalog.remove(id);

        if (removedOrder.isEmpty()) {
            return removedOrder;
        }
        removedOrder.get().setStatus(orderStatus);

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
