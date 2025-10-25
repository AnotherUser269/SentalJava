package archive;

import java.util.Optional;

public interface Archive<T> {
    void put(T element);

    Optional<T> remove(int id);
    Optional<T> get(int id);
}
