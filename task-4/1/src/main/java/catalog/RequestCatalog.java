package catalog;

import core.Request;
import status_enums.RequestStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestCatalog implements ICatalog<Request> {
    private final Map<Integer, Request> requests;

    public RequestCatalog() {
        this.requests = new HashMap<>();
    }

    public Optional<Request> get(String bookTitle) {
        for (Map.Entry<Integer, Request> i : requests.entrySet()) {
            if (Objects.equals(i.getValue().getBookTitle(), bookTitle) &&
                    i.getValue().getStatus() == RequestStatus.Opened) {

                return Optional.ofNullable(i.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void put(Request newRequest) {
        requests.put(newRequest.getId(), newRequest);
    }

    @Override
    public Optional<Request> remove(int id) {
        return Optional.ofNullable(requests.remove(id));
    }

    @Override
    public Optional<Request> get(int id) {
        return Optional.ofNullable(requests.get(id));
    }
}
