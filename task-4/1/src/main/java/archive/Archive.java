package archive;

import core.Book;

import java.util.Optional;

public interface Archive<T> {
    void put(T element);

    Optional<T> remove(int id);
    Optional<T> find(int id);
}
