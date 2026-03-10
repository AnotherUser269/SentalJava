package components.manager;

import components.archive.BookArchive;
import components.catalog.BookCatalog;
import components.core.Book;

import java.math.BigDecimal;
import java.util.Optional;

public class BookManager {
    private final BookCatalog bookCatalog;
    private final BookArchive bookArchive;

    public BookManager(BookCatalog bookCatalog, BookArchive bookArchive) {
        this.bookCatalog = bookCatalog;
        this.bookArchive = bookArchive;
    }

    /**
     * Finds a book by its unique identifier in the book archive.
     *
     * @param id the unique identifier of the book to be found.
     * @return an Optional containing the found Book, or an empty Optional if not found.
     */
    public Optional<Book> findBook(int id) {
        return bookArchive.find(id);
    }

    /**
     * Finds a book by its title in the book catalog.
     *
     * @param bookTitle the title of the book to be found.
     * @return an Optional containing the found Book, or an empty Optional if not found.
     */
    public Optional<Book> findBookByTitle(String bookTitle) {
        return bookCatalog.get(bookTitle);
    }

    /**
     * Adds a new book to the catalog and archive with a unique ID and specified details.
     * If the provided timestamp is negative, the current system timestamp is used.
     *
     * @param title       the title of the book.
     * @param author      the author of the book.
     * @param description a brief description of the book.
     * @param timeStamp   the timestamp (in seconds since the epoch) when the book was added;
     *                    if negative, the current time is used.
     * @param price       the price of the book.
     * @return the newly added Book object.
     */
    public Book addBook(String title,
                        String author,
                        String description,
                        long timeStamp,
                        BigDecimal price) {
        int bookId = generateId();

        if (timeStamp < 0) {
            timeStamp = getCurrentTimeStamp();
        }

        Book newBook = new Book(bookId, title, author, description, timeStamp, price);
        bookCatalog.put(newBook);
        bookArchive.put(newBook);

        return newBook;
    }

    /**
     * Removes a book by its unique identifier from the book catalog.
     * The status of the removed book is set to "NotInOrder".
     *
     * @param id the unique identifier of the book to be removed.
     * @return an Optional containing the removed Book, or an empty Optional if no book was found.
     */
    public Optional<Book> removeBook(int id) {
        Optional<Book> removedBook = bookCatalog.remove(id);

        if (removedBook.isEmpty()) {
            return removedBook;
        }

        return removedBook;
    }

    /**
     * Generates a unique identifier for a new book by checking existing IDs
     * in the book archive and ensuring that the new ID is not already in use.
     *
     * @return a unique ID that is not already present in the archive.
     */
    private int generateId() {
        int startId = 0;

        while (bookArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }

    /**
     * Retrieves the current system timestamp in seconds.
     * This timestamp is typically used when no specific time is provided.
     *
     * @return the current timestamp in seconds since the epoch.
     */
    private long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000L;
    }
}
