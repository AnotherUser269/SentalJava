package catalog;

import core.Book;
import enums.BookStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class BookCatalog implements Catalog<Book> {
    private final Map<Integer, Book> books;

    public BookCatalog() {
        this.books = new HashMap<>();
    }

    public Optional<Book> get(String bookTitle) {
        for(Map.Entry<Integer, Book> i: books.entrySet()) {
            Book currentBook = i.getValue();

            if(Objects.equals(currentBook.getTitle(), bookTitle) && (currentBook.getStatus() == BookStatus.IN_ORDER)){
                return Optional.ofNullable(i.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void put(Book newBook) {
        books.put(newBook.getId(), newBook);
    }

    @Override
    public Optional<Book> remove(int id) {
        return Optional.ofNullable(books.remove(id));
    }

    @Override
    public Optional<Book> get(int id) {
        return Optional.ofNullable(books.get(id));
    }
}
