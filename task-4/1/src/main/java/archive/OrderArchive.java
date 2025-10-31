package archive;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Optional;

import core.Book;
import core.Order;
import sort_enums.BookSort;
import sort_enums.OrderSort;
import status_enums.BookStatus;
import status_enums.OrderStatus;

public class OrderArchive implements Archive<Order> {
    private final ArrayList<AbstractMap.SimpleEntry<Integer, Order>> archive = new ArrayList<>();

    public void sortBy(OrderSort sortType) {
        if (sortType == OrderSort.EndTimeAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getCompletionTime()));
        } else if (sortType == OrderSort.PriceAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getDeliveryPrice()));
        } else if (sortType == OrderSort.StatusAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getStatus() == OrderStatus.New));
        } else {
            throw new RuntimeException("Wrong sort type provided!");
        }
    }

    private int amountOrderedInTime(long StartTime, long EndTime) {
        int currentAmount = 0;

        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            if (entry.getValue().getStartTime() >= StartTime && entry.getValue().getCompletionTime() <= EndTime) {
                currentAmount++;
            }
        }

        return currentAmount;
    }

    private ArrayList<Order> getOrderedInTime(long StartTime, long EndTime) {
        ArrayList<Order> orders = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            if (entry.getValue().getStartTime() >= StartTime && entry.getValue().getCompletionTime() <= EndTime) {
                orders.add(entry.getValue());
            }
        }

        return orders;
    }

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

    @Override
    public void printAll() {
        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            System.out.println(entry.getValue());
        }
    }

    @Override
    public ArrayList<Order> getAll() {
        ArrayList<Order> orders = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Integer, Order> entry : archive) {
            orders.add(entry.getValue());
        }

        return orders;
    }
}
