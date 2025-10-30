package archive;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Optional;
import core.Book;

public class BookArchive implements Archive<Book> {
    private final ArrayList<AbstractMap.SimpleEntry<Integer, Book>> archive = new ArrayList<>();

    @Override
    public void put(Book book) {
        AbstractMap.SimpleEntry<Integer, Book> entry = new AbstractMap.SimpleEntry<>(book.getId(), book);
        archive.add(entry);
    }

    @Override
    public Optional<Book> remove(int id) {
        for(AbstractMap.SimpleEntry<Integer, Book> entry: archive) {
            if(entry.getKey().equals(id)) {
                archive.remove(entry);

                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> find(int id) {
        for(AbstractMap.SimpleEntry<Integer, Book> entry: archive) {
            if(entry.getKey().equals(id)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
