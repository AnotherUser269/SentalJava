package components.archive;

import java.util.ArrayList;
import java.util.Optional;

public interface IArchive<T> {
    void put(T element);

    Optional<T> remove(int id);

    Optional<T> find(int id);

    void printAll();

    ArrayList<T> getAll();

    void clearAll();
}
