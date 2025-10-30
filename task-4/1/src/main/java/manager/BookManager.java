package manager;

import archive.BookArchive;
import catalog.BookCatalog;
import core.Book;
import enums.BookStatus;

import java.util.Optional;

public class BookManager {
    private final BookCatalog bookCatalog;
    private final BookArchive bookArchive;

    public BookManager(BookCatalog bookCatalog, BookArchive bookArchive) {
        this.bookCatalog = bookCatalog;
        this.bookArchive = bookArchive;
    }

    /**
     * Finds a book by its identifier.
     *
     * @param id book identifier
     * @return {@link Optional} containing the {@link Book} if found, or empty otherwise
    */
    public Optional<Book> findBook(int id) {
        return bookArchive.find(id);
    }

    /**
     * Finds a book by its title.
     *
     * @param bookTitle title to search for
     * @return {@link Optional} containing the {@link Book} if found, or empty otherwise
    */
    public Optional<Book> findBookByTitle(String bookTitle) {
        return bookCatalog.get(bookTitle);
    }

    /**
     * Adds a new book with the given title.
     * Generates a new unique id, creates a Book, stores it in the catalog and archive.
     *
     * @param title book title
     * @return the created {@link Book}
    */
    public Book addBook(String title) {
        int bookId = generateId();
        Book newBook = new Book(bookId, title);
        bookCatalog.put(newBook);
        bookArchive.put(newBook);

        return newBook;
    }

    /**
     * Removes a book by its identifier.
     * Delegates removal to the catalog and, if removed, sets the book status to {@link BookStatus#NOT_IN_ORDER}.
     *
     * @param id identifier of the book to remove
     * @return {@link Optional} containing the removed {@link Book} if it existed, or empty otherwise
    */
    public Optional<Book> removeBook(int id) {
        Optional<Book> removedBook = bookCatalog.remove(id);

        if(removedBook.isPresent()) {
            removedBook.get().setStatus(BookStatus.NOT_IN_ORDER);
        }

        return removedBook;
    }

    /**
     * Generates a new unique book identifier.
     * Starts from 0 and increments until an id is found that does not exist in the archive.
     *
     * @return new unique book id
    */
    private int generateId() {
        int startId = 0;

        while (bookArchive.find(startId).isPresent()) {
            ++startId;
        }

        return startId;
    }
}
