package components.archive;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Optional;
import java.util.List;

import components.sort_enums.BookSort;
import components.status_enums.BookStatus;
import components.core.Book;

public class BookArchive implements IArchive<Book> {
    private final List<AbstractMap.SimpleEntry<Integer, Book>> archive = new ArrayList<>();

    public void sortBy(BookSort sortType) {
        if (sortType == BookSort.AlphabetAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getTitle().charAt(0)));
        } else if (sortType == BookSort.DateAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getTimeStamp()));
        } else if (sortType == BookSort.StatusAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getStatus() == BookStatus.InOrder));
        } else if (sortType == BookSort.PriceAscending) {
            archive.sort(Comparator.comparing(item -> item.getValue().getPrice()));
        } else {
            throw new RuntimeException("Wrong sort type provided!");
        }
    }

    @Override
    public void put(Book book) {
        AbstractMap.SimpleEntry<Integer, Book> entry = new AbstractMap.SimpleEntry<>(book.getId(), book);
        archive.add(entry);
    }

    @Override
    public Optional<Book> remove(int id) {
        for (AbstractMap.SimpleEntry<Integer, Book> entry : archive) {
            if (entry.getKey().equals(id)) {
                archive.remove(entry);

                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> find(int id) {
        for (AbstractMap.SimpleEntry<Integer, Book> entry : archive) {
            if (entry.getKey().equals(id)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public void printAll() {
        for (AbstractMap.SimpleEntry<Integer, Book> entry : archive) {
            System.out.println(entry.getValue());
        }
    }

    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Integer, Book> entry : archive) {
            books.add(entry.getValue());
        }

        return books;
    }
}
