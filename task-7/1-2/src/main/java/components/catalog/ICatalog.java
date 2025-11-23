package components.catalog;

import java.util.Optional;

public interface ICatalog<T> {
    void put(T element);

    Optional<T> remove(int id);

    Optional<T> get(int id);

    void clearAll();
}
