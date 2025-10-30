package manager;

import archive.RequestArchive;
import catalog.RequestCatalog;
import core.Request;
import enums.RequestStatus;

import java.util.Optional;

public class RequestManager {
    private final RequestCatalog requestCatalog;
    private final RequestArchive requestArchive;

    public RequestManager(RequestCatalog requestCatalog, RequestArchive requestArchive) {
        this.requestCatalog = requestCatalog;
        this.requestArchive = requestArchive;
    }
    public Optional<Request> findRequest(int id) {
        return requestCatalog.get(id);
    }
    public Optional<Request> findRequestByTitle(String bookTitle) {
        return requestCatalog.get(bookTitle);
    }
    public Request createRequest(String bookTitle) {
        int requestId = generateId();
        Request newRequest = new Request(requestId, bookTitle);
        requestCatalog.put(newRequest);
        requestArchive.put(newRequest);

        return newRequest;
    }
    public Optional<Request> removeRequest(int id) {
        Optional<Request> removedRequest = requestCatalog.remove(id);

        if(removedRequest.isPresent()) {
            removedRequest.get().setStatus(RequestStatus.CLOSED);
        }

        return removedRequest;
    }

    private int generateId() {
        int startId = 0;

        while (requestArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
