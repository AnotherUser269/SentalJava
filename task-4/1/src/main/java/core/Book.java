package core;

import enums.BookStatus;

public class Book {
    private BookStatus status = BookStatus.IN_ORDER;
    private final String title;
    private final String author;
    private final String description;
    private final long timeStamp;
    private final double price;
    private final int id;

    public Book(int id, String title, String author, String description, long timeStamp, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.timeStamp = timeStamp;
        this.price = price;
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

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", timeStamp=" + timeStamp +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
