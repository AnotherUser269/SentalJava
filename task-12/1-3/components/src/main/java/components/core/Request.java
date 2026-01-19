package components.core;

import components.status_enums.RequestStatus;

import java.util.Objects;

public final class Request {
    private final int id;
    private final String bookTitle;
    private RequestStatus status = RequestStatus.Opened;

    public Request(int id, String bookTitle) {
        this.id = id;
        this.bookTitle = Objects.requireNonNull(bookTitle, "bookTitle не может быть null");
    }

    // Getters
    public RequestStatus getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public String getBookTitle() { return bookTitle; }


    public void setStatus(RequestStatus newStatus) {
        this.status = newStatus;
    }

    // Hash logic
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request r)) return false;
        return id == r.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Request{id=%d, bookTitle='%s', status=%s}", id, bookTitle, status);
    }
}
