package components.store;

import components.core.*;
import components.manager.*;
import components.status_enums.*;
import components.core.*;
import components.manager.*;

import java.math.BigDecimal;
import java.util.Optional;

public class BookStore {
    private final String name;
    private final BookManager bookManager;
    private final OrderManager orderManager;
    private final RequestManager requestManager;

    public BookStore(String name,
                     BookManager bookManager,
                     OrderManager orderManager,
                     RequestManager requestManager) {
        this.name = name;
        this.bookManager = bookManager;
        this.orderManager = orderManager;
        this.requestManager = requestManager;
    }

    /**
     * Gets the name of the bookstore.
     *
     * @return The name of the bookstore.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new request for a book.
     *
     * @param bookTitle The title of the book to request.
     * @return The created request object.
     */
    public Request createRequest(String bookTitle) {
        Request request = requestManager.createRequest(bookTitle);

        return request;
    }


    /**
     * Creates an order for a book with the specified start time.
     *
     * @param bookTitle     The title of the book to order.
     * @param startTime     The start time of the order (in seconds since epoch).
     * @param phoneNumber   The client's phone number.
     * @param deliveryPrice The price of the delivery.
     * @return The created order object.
     * @throws Exception If the book does not exist.
     */
    public Order createOrder(String bookTitle, long startTime, String phoneNumber, BigDecimal deliveryPrice) throws Exception {
        Optional<Book> bookToOrder = bookManager.findBookByTitle(bookTitle);

        if (bookToOrder.isPresent()) {
            bookToOrder.get().setStatus(BookStatus.NotInOrder);
            if (startTime < 0) {
                startTime = System.currentTimeMillis() / 1000L;
            }

            Order order = orderManager.createOrder(bookToOrder.get().getId(), startTime, phoneNumber, deliveryPrice);

            return order;
        } else {
            throw new Exception("You are ordering a non existing book!");
        }
    }

    /**
     * Creates an order for a book with the current time as the start time.
     *
     * @param bookTitle     The title of the book to order.
     * @param phoneNumber   The client's phone number.
     * @param deliveryPrice The price of the delivery.
     * @return The created order object.
     * @throws Exception If the book does not exist.
     */
    public Order createOrder(String bookTitle, String phoneNumber, BigDecimal deliveryPrice) throws Exception {
        return createOrder(bookTitle, -1, phoneNumber, deliveryPrice);
    }

    /**
     * Finds a book by its title.
     *
     * @param bookTitle The title of the book to search for.
     * @return An Optional containing the found book, or an empty Optional if not found.
     */
    public Optional<Book> findBook(String bookTitle) {
        return bookManager.findBookByTitle(bookTitle);
    }

    /**
     * Adds a new book to the bookstore with detailed information.
     *
     * @param bookTitle   The title of the book.
     * @param author      The author of the book.
     * @param description A description of the book.
     * @param timestamp   The timestamp when the book was added.
     * @param price       The price of the book.
     * @return The newly added book.
     */
    public Book addBook(String bookTitle,
                        String author,
                        String description,
                        long timestamp,
                        BigDecimal price) {
        Book newBook = bookManager.addBook(bookTitle, author, description, timestamp, price);
        Optional<Request> request = requestManager.findRequestByTitle(bookTitle);

        if (request.isPresent()) {
            request.get().setStatus(RequestStatus.Closed);
            requestManager.removeRequest(request.get().getId());
        }

        return newBook;
    }

    /**
     * Adds a new book to the bookstore with basic details and a default description.
     *
     * @param bookTitle The title of the book.
     * @param author    The author of the book.
     * @param timestamp The timestamp when the book was added.
     * @param price     The price of the book.
     * @return The newly added book.
     */
    public Book addBook(String bookTitle,
                        String author,
                        long timestamp,
                        BigDecimal price) {
        return addBook(bookTitle, author, "No description provided", timestamp, price);
    }

    /**
     * Adds a new book to the bookstore with basic details, default description, and a default timestamp.
     *
     * @param bookTitle The title of the book.
     * @param author    The author of the book.
     * @param price     The price of the book.
     * @return The newly added book.
     */
    public Book addBook(String bookTitle,
                        String author,
                        BigDecimal price) {
        return addBook(bookTitle, author, "No description provided", -1, price);
    }

    /**
     * Adds a new book to the bookstore with basic details and a default timestamp and description.
     *
     * @param bookTitle   The title of the book.
     * @param author      The author of the book.
     * @param description The description of the book.
     * @param price       The price of the book.
     * @return The newly added book.
     */
    public Book addBook(String bookTitle,
                        String author,
                        String description,
                        BigDecimal price) {
        return addBook(bookTitle, author, description, -1, price);
    }

    /**
     * Removes a book from the bookstore by its ID.
     *
     * @param id The ID of the book to remove.
     * @return An Optional containing the removed book, or an empty Optional if no book with that ID was found.
     */
    public Optional<Book> removeBook(int id) throws Exception {
        Optional<Book> deletedBook = bookManager.removeBook(id);

        if (deletedBook.isPresent()) {
            if (deletedBook.get().getStatus() == BookStatus.NotInOrder) {
                throw new Exception("You are removing a book, that someone has already ordered!");
            }

            deletedBook.get().setStatus(BookStatus.NotInOrder);
        }

        return deletedBook;
    }

    /**
     * Cancels a request by its ID.
     *
     * @param id The ID of the request to cancel.
     * @return An Optional containing the canceled request, or an empty Optional if no request with that ID was found.
     */
    public Optional<Request> cancelRequest(int id) {
        Optional<Request> deleteRequest = requestManager.removeRequest(id);

        if (deleteRequest.isPresent()) {
            deleteRequest.get().setStatus(RequestStatus.Closed);
        }

        return deleteRequest;
    }

    /**
     * Closes an order by its ID and updates the status of the ordered book.
     *
     * @param id     The ID of the order to close.
     * @param status The status to set for the order.
     * @return An Optional containing the closed order, or an empty Optional if no order with that ID was found.
     */
    public Optional<Order> closeOrder(int id, OrderStatus status) {
        Optional<Order> deleteOrder = orderManager.removeOrder(id, status);

        if (deleteOrder.isPresent()) {
            Optional<Book> orderedBook = bookManager.findBook(deleteOrder.get().getBookId());

            if (orderedBook.isPresent() && status == OrderStatus.Dismissed) {
                orderedBook.get().setStatus(BookStatus.InOrder);
            } else if (orderedBook.isPresent() && status == OrderStatus.Success) {
                bookManager.removeBook(orderedBook.get().getId());
            }
        }

        return deleteOrder;
    }
}
