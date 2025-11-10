package components.core;

import components.status_enums.RequestStatus;

public class Request {
    private RequestStatus status = RequestStatus.Opened;
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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", status=" + status +
                '}';
    }
}
