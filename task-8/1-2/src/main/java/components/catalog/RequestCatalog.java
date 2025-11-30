package components.catalog;

import components.core.Order;
import components.status_enums.RequestStatus;
import components.core.Request;

import java.util.*;

public class RequestCatalog implements ICatalog<Request> {
    private final Map<Integer, Request> requestCatalog;

    public RequestCatalog() {
        this.requestCatalog = new HashMap<>();
    }

    public Optional<Request> get(String bookTitle) {
        for (Map.Entry<Integer, Request> i : requestCatalog.entrySet()) {
            if (Objects.equals(i.getValue().getBookTitle(), bookTitle) &&
                    i.getValue().getStatus() == RequestStatus.Opened) {

                return Optional.ofNullable(i.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void put(Request newRequest) {
        requestCatalog.put(newRequest.getId(), newRequest);
    }

    @Override
    public Optional<Request> remove(int id) {
        return Optional.ofNullable(requestCatalog.remove(id));
    }

    @Override
    public Optional<Request> get(int id) {
        return Optional.ofNullable(requestCatalog.get(id));
    }

    public ArrayList<Request> getAll() {
        ArrayList<Request> requests = new ArrayList<>();

        for (AbstractMap.Entry<Integer, Request> entry : requestCatalog.entrySet()) {
            requests.add(entry.getValue());
        }

        return requests;
    }

    public void printAll() {
        for (Map.Entry<Integer, Request> entry : requestCatalog.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public void clearAll() {
        requestCatalog.clear();
    }
}
