package core;

import enums.BookStatus;

public class Book {
    private BookStatus status = BookStatus.IN_ORDER;
    private final String title;
    private final int id;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(BookStatus newStatus) {
        this.status = newStatus;
    }

    public int getId() {
        return id;
    }
}
