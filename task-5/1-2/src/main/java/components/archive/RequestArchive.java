package components.archive;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import components.sort_enums.RequestSort;
import components.core.Request;

public class RequestArchive implements IArchive<Request> {
    private final List<AbstractMap.SimpleEntry<Integer, Request>> archive = new ArrayList<>();

    public void sortBy(RequestSort sortType) {
        if (sortType == RequestSort.AlphabetAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getBookTitle().charAt(0)));
        } else if (sortType == RequestSort.AmountAscending) {
            Map<String, Integer> frequencies = new HashMap<>();

            for (AbstractMap.SimpleEntry<Integer, Request> entry : archive) {
                String bookTitle = entry.getValue().getBookTitle();

                frequencies.put(bookTitle, frequencies.getOrDefault(bookTitle, 0) + 1);
            }

            archive.sort(Comparator.comparing(item -> frequencies.get(item.getValue().getBookTitle())));

        } else {
            throw new RuntimeException("Wrong sort type provided!");
        }
    }

    @Override
    public void put(Request request) {
        AbstractMap.SimpleEntry<Integer, Request> entry = new AbstractMap.SimpleEntry<>(request.getId(), request);
        archive.add(entry);
    }

    @Override
    public Optional<Request> remove(int id) {
        for (AbstractMap.SimpleEntry<Integer, Request> entry : archive) {
            if (entry.getKey().equals(id)) {
                archive.remove(entry);

                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Request> find(int id) {
        for (AbstractMap.SimpleEntry<Integer, Request> entry : archive) {
            if (entry.getKey().equals(id)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public void printAll() {
        for (AbstractMap.SimpleEntry<Integer, Request> entry : archive) {
            System.out.println(entry.getValue());
        }
    }

    @Override
    public ArrayList<Request> getAll() {
        ArrayList<Request> requests = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Integer, Request> entry : archive) {
            requests.add(entry.getValue());
        }

        return requests;
    }
}
