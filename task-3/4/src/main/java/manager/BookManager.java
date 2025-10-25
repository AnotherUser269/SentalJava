package manager;

import archive.BookArchive;
import core.Book;
import enums.BookStatus;

import java.util.Optional;

public class BookManager {
    private final BookArchive bookArchive;

    public BookManager(BookArchive bookArchive) {
        this.bookArchive = bookArchive;
    }
    public Optional<Book> findBook(int id) {
        return bookArchive.get(id);
    }
    public Optional<Book> findBookByTitle(String bookTitle) {
        return bookArchive.get(bookTitle);
    }
    public Book addBook(String title) {
        int bookId = generateId();
        Book newBook = new Book(bookId, title);
        bookArchive.put(newBook);

        return newBook;
    }
    public Optional<Book> removeBook(int id) {
        Optional<Book> removedBook = bookArchive.remove(id);

        if(removedBook.isPresent()) {
            removedBook.get().setStatus(BookStatus.NOT_IN_ORDER);
        }

        return removedBook;
    }

    private int generateId() {
        int startId = 0;

        while (bookArchive.get(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
