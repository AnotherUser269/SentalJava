package archive;

import java.util.Optional;

public interface Archive<T> {
    void put(T element);

    Optional<T> remove(int id);
    Optional<T> find(int id);

    // TODO: void sortBy();
    // TODO: void printAll();
}
