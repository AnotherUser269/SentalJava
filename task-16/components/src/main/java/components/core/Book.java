package components.core;

import components.status_enums.BookStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "timeStamp")
    private Long timeStamp;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private BookStatus status;

    protected Book() {}

    public Book(int id,
                String title,
                String author,
                String description,
                long timeStamp,
                BigDecimal price) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "Title can't be null");
        this.author = author != null ? author : "Unknown";
        this.description = description != null ? description : "No description provided";
        this.timeStamp = timeStamp;
        this.price = Objects.requireNonNull(price, "Price can't be null");;
    }

    // Getters
    public BookStatus getStatus() { return status; }
    public String getTitle() { return title; }
    public void setStatus(BookStatus newStatus) { this.status = newStatus; }
    public int getId() { return id; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public long getTimeStamp() { return timeStamp; }
    public BigDecimal getPrice() { return price; }

    // Hash logic
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', description='%s', price=%s, status=%s}",
                id, title, author, description, price, status);
    }
}
