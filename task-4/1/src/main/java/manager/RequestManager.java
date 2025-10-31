package manager;

import archive.RequestArchive;
import catalog.RequestCatalog;
import core.Request;
import status_enums.RequestStatus;

import java.util.Optional;

public class RequestManager {
    private final RequestCatalog requestCatalog;
    private final RequestArchive requestArchive;

    public RequestManager(RequestCatalog requestCatalog, RequestArchive requestArchive) {
        this.requestCatalog = requestCatalog;
        this.requestArchive = requestArchive;
    }

    /**
     * Finds a request by its unique identifier.
     *
     * @param id the unique identifier of the request to be found.
     * @return an Optional containing the found Request, or an empty Optional if not found.
     */
    public Optional<Request> findRequest(int id) {
        return requestCatalog.get(id);
    }

    /**
     * Finds a request by its book title.
     *
     * @param bookTitle the title of the book for which the request is made.
     * @return an Optional containing the found Request, or an empty Optional if not found.
     */
    public Optional<Request> findRequestByTitle(String bookTitle) {
        return requestCatalog.get(bookTitle);
    }

    /**
     * Creates a new request with a unique ID for a given book title.
     * The new request is added to both the request catalog and archive.
     *
     * @param bookTitle the title of the book for which the request is being created.
     * @return the newly created Request object.
     */
    public Request createRequest(String bookTitle) {
        int requestId = generateId();
        Request newRequest = new Request(requestId, bookTitle);
        requestCatalog.put(newRequest);
        requestArchive.put(newRequest);

        return newRequest;
    }

    /**
     * Removes a request by its unique identifier.
     * The request's status is set to "Closed" if it is successfully removed.
     *
     * @param id the unique identifier of the request to be removed.
     * @return an Optional containing the removed Request, or an empty Optional if no request was found.
     */
    public Optional<Request> removeRequest(int id) {
        Optional<Request> removedRequest = requestCatalog.remove(id);

        if (removedRequest.isEmpty()) {
            return removedRequest;
        }
        removedRequest.get().setStatus(RequestStatus.Closed);

        return removedRequest;
    }

    /**
     * Generates a unique identifier for a new request by checking existing IDs
     * in the request archive and ensuring that the new ID is not already in use.
     *
     * @return a unique ID that is not already present in the archive.
     */
    private int generateId() {
        int startId = 0;

        while (requestArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
