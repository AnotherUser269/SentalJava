package manager;

import archive.RequestArchive;
import core.Book;
import core.Request;
import enums.RequestStatus;

import java.util.Optional;

public class RequestManager {
    private final RequestArchive requestArchive;

    public RequestManager(RequestArchive requestArchive) {
        this.requestArchive = requestArchive;
    }
    public Optional<Request> findRequest(int id) {
        return requestArchive.get(id);
    }
    public Optional<Request> findRequestByTitle(String bookTitle) {
        return requestArchive.get(bookTitle);
    }
    public Request createRequest(String bookTitle) {
        int requestId = generateId();
        Request newRequest = new Request(requestId, bookTitle);
        requestArchive.put(newRequest);

        return newRequest;
    }
    public Optional<Request> removeRequest(int id) {
        Optional<Request> removedRequest = requestArchive.remove(id);

        if(removedRequest.isPresent()) {
            removedRequest.get().setStatus(RequestStatus.CLOSED);
        }

        return removedRequest;
    }

    private int generateId() {
        int startId = 0;

        while (requestArchive.get(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
