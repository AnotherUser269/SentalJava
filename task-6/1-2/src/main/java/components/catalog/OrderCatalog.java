package components.catalog;

import components.core.Book;
import components.core.Order;

import java.util.*;

public class OrderCatalog implements ICatalog<Order> {
    private final Map<Integer, Order> orderCatalog;

    public OrderCatalog() {
        this.orderCatalog = new HashMap<>();
    }

    @Override
    public void put(Order newOrder) {
        orderCatalog.put(newOrder.getId(), newOrder);
    }

    @Override
    public Optional<Order> remove(int id) {
        return Optional.ofNullable(orderCatalog.remove(id));
    }

    @Override
    public Optional<Order> get(int id) {
        return Optional.ofNullable(orderCatalog.get(id));
    }

    public void printAll() {
        for (Map.Entry<Integer, Order> entry : orderCatalog.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public ArrayList<Order> getAll() {
        ArrayList<Order> orders = new ArrayList<>();

        for (AbstractMap.Entry<Integer, Order> entry : orderCatalog.entrySet()) {
            orders.add(entry.getValue());
        }

        return orders;
    }

    public void clearAll() {
        orderCatalog.clear();
    }
}
