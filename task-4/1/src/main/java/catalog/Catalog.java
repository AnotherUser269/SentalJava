package catalog;

import java.util.Optional;

public interface Catalog<T> {
    void put(T element);

    Optional<T> remove(int id);

    Optional<T> get(int id);
}
