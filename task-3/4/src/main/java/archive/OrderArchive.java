package archive;

import core.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderArchive implements Archive<Order> {
    private final Map<Integer, Order> orders;

    public OrderArchive() {
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
}
