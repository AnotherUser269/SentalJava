package archive;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Optional;
import core.Request;

public class RequestArchive implements Archive<Request> {
    private final ArrayList<AbstractMap.SimpleEntry<Integer, Request>> archive = new ArrayList<>();

    @Override
    public void put(Request request) {
        AbstractMap.SimpleEntry<Integer, Request> entry = new AbstractMap.SimpleEntry<>(request.getId(), request);
        archive.add(entry);
    }

    @Override
    public Optional<Request> remove(int id) {
        for(AbstractMap.SimpleEntry<Integer, Request> entry: archive) {
            if(entry.getKey().equals(id)) {
                archive.remove(entry);

                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Request> find(int id) {
        for(AbstractMap.SimpleEntry<Integer, Request> entry: archive) {
            if(entry.getKey().equals(id)) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
