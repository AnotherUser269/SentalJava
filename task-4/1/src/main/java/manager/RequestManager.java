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

    /**
     * Finds a request by its identifier.
     *
     * @param id request identifier
     * @return {@link Optional} containing the {@link Request} if found, or empty otherwise
     */
    public Optional<Request> findRequest(int id) {
        return requestCatalog.get(id);
    }

    /**
     * Finds a request by book title.
     *
     * @param bookTitle title of the requested book
     * @return {@link Optional} containing the {@link Request} if found, or empty otherwise
     */
    public Optional<Request> findRequestByTitle(String bookTitle) {
        return requestCatalog.get(bookTitle);
    }

    /**
     * Creates a new request for the given book title.
     * Generates a unique request id, creates a Request, stores it in the catalog and archive.
     *
     * @param bookTitle title of the requested book
     * @return the created {@link Request}
     */
    public Request createRequest(String bookTitle) {
        int requestId = generateId();
        Request newRequest = new Request(requestId, bookTitle);
        requestCatalog.put(newRequest);
        requestArchive.put(newRequest);

        return newRequest;
    }

    /**
     * Removes a request by its identifier.
     * Delegates removal to the catalog and, if removed, sets the request status to {@link RequestStatus#CLOSED}.
     *
     * @param id identifier of the request to remove
     * @return {@link Optional} containing the removed {@link Request} if it existed, or empty otherwise
     */
    public Optional<Request> removeRequest(int id) {
        Optional<Request> removedRequest = requestCatalog.remove(id);

        if (removedRequest.isPresent()) {
            removedRequest.get().setStatus(RequestStatus.CLOSED);
        }

        return removedRequest;
    }

    /**
     * Generates a new unique request identifier.
     * Starts from 0 and increments until an id is found that does not exist in the archive.
     *
     * @return new unique request id
     */
    private int generateId() {
        int startId = 0;

        while (requestArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
