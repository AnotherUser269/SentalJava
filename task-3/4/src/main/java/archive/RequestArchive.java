package archive;

import core.Book;
import core.Request;
import enums.BookStatus;
import enums.RequestStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestArchive implements Archive<Request> {
    private final Map<Integer, Request> requests;

    public RequestArchive() {
        this.requests = new HashMap<>();
    }

    public Optional<Request> get(String bookTitle) {
        for(Map.Entry<Integer, Request> i: requests.entrySet()) {
            if(Objects.equals(i.getValue().getBookTitle(), bookTitle)){
                if(i.getValue().getStatus() == RequestStatus.OPENED) {
                    return Optional.ofNullable(i.getValue());
                }
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
