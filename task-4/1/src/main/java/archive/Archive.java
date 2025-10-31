package archive;

import java.util.ArrayList;
import java.util.Optional;

public interface Archive<T> {
    void put(T element);

    Optional<T> remove(int id);

    Optional<T> find(int id);

    void printAll();

    ArrayList<T> getAll();
}
