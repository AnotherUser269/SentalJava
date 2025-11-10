package components.catalog;

import components.core.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderCatalog implements ICatalog<Order> {
    private final Map<Integer, Order> orders;

    public OrderCatalog() {
        this.orders = new HashMap<>();
    }

    @Override
    public void put(Order newOrder) {
        orders.put(newOrder.getId(), newOrder);
    }

    @Override
    public Optional<Order> remove(int id) {
        return Optional.ofNullable(orders.remove(id));
    }

    @Override
    public Optional<Order> get(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    public void printAll() {
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
