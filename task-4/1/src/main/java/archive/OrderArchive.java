package archive;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Optional;

import core.Order;

public class OrderArchive implements Archive<Order> {
    private final ArrayList<AbstractMap.SimpleEntry<Integer, Order>> archive = new ArrayList<>();

    @Override
    public void put(Order order) {
        AbstractMap.SimpleEntry<Integer, Order> entry = new AbstractMap.SimpleEntry<>(order.getId(), order);
        archive.add(entry);
    }

    @Override
    public Optional<Order> remove(int id) {
        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            if (entry.getKey().equals(id)) {
                archive.remove(entry);

                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Order> find(int id) {
        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            if (entry.getKey().equals(id)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
