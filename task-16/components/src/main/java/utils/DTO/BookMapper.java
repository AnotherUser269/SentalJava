package utils.DTO;

import components.core.Book;
import components.status_enums.BookStatus;

public final class BookMapper {
    private BookMapper(){}

    public static BookResponse toResponse(Book b) {
        if (b == null) return null;
        BookResponse r = new BookResponse();
        r.id = b.getId();
        r.title = b.getTitle();
        r.author = b.getAuthor();
        r.description = b.getDescription();
        r.timeStamp = b.getTimeStamp();
        r.price = b.getPrice();
        r.status = b.getStatus() != null ? b.getStatus().name() : null;
        return r;
    }

    public static Book toEntity(BookRequest req) {
        if (req == null) return null;

        int id = req.id != null ? req.id : 0;
        long ts = req.timeStamp != null ? req.timeStamp : System.currentTimeMillis() / 1000L;
        Book b = new Book(id, req.title, req.author, req.description, ts, req.price);
        if (req.status != null) {
            try { b.setStatus(BookStatus.valueOf(req.status)); } catch (IllegalArgumentException ignored) {}
        }
        return b;
    }
}
