package core;

import enums.RequestStatus;

public class Request {
    private RequestStatus status = RequestStatus.OPENED;
    private final int id;
    private final String bookTitle;

    public Request(int id, String bookTitle) {
        this.id = id;
        this.bookTitle = bookTitle;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus newStatus) {
        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
