package store;

import core.*;
import enums.*;
import manager.*;

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

    public String getName() {
        return name;
    }

    /** Creates a new request for a book by its title.
     *  Delegates the creation of a Request to the corresponding RequestManager.

     * @param bookTitle is the title of the book for which the application is being created
     * @return created {@link Request} object
    */
    public Request createRequest(String bookTitle) {
        Request request = requestManager.createRequest(bookTitle);

        return request;
    }

    /**
     * Creates a new order for a book by title.
     * - Searches for the book via {@link BookManager}.
     * - If the book is found, sets its status to {@link BookStatus#NOT_IN_ORDER}
     *   and creates an order via {@link OrderManager}.
     * - If not found, throws an exception.
     *
     * @param bookTitle title of the book to order
     * @return created {@link Order}
     * @throws Exception if no book with the given title is found
    */
    public Order createOrder(String bookTitle) throws Exception {
        Optional<Book> bookToOrder = bookManager.findBookByTitle(bookTitle);

        if(bookToOrder.isPresent()) {
            bookToOrder.get().setStatus(BookStatus.NOT_IN_ORDER);
            Order order = orderManager.createOrder(bookToOrder.get().getId());

            return order;
        } else {
            throw new Exception("You are ordering a non existing book!");
        }
    }

    /**
     * Finds the first book matching its title.
     *
     * @param bookTitle title of the book to find
     * @return {@link Optional} containing the {@link Book} if found, or empty otherwise
    */
    public Optional<Book> findBook(String bookTitle) {
        return bookManager.findBookByTitle(bookTitle);
    }

    /**
     * Adds a new book to the store.
     * Delegates creation to {@link BookManager#addBook(String)}.
     * If there is an open {@link Request} with the same title, marks it as {@link RequestStatus#CLOSED}
     * and removes it from the RequestManager.
     *
     * @param bookTitle title of the new book
     * @return created {@link Book}
    */
    public Book addBook(String bookTitle) {
        Book newBook = bookManager.addBook(bookTitle);
        Optional<Request> request = requestManager.findRequestByTitle(bookTitle);

        if(request.isPresent()) {
            request.get().setStatus(RequestStatus.CLOSED);
            requestManager.removeRequest(request.get().getId());
        }

        return newBook;
    }

    /**
     * Removes a book by its identifier.
     * Delegates removal to {@link BookManager#removeBook(int)}.
     * If a book was removed, sets its status to {@link BookStatus#NOT_IN_ORDER}.
     *
     * @param id identifier of the book to remove
     * @return {@link Optional} containing the removed {@link Book} if found, or empty otherwise
    */
    public Optional<Book> removeBook(int id) {
        Optional<Book> deletedBook = bookManager.removeBook(id);

        if(deletedBook.isPresent()) {
            deletedBook.get().setStatus(BookStatus.NOT_IN_ORDER);
        }

        return deletedBook;
    }

    /**
     * Cancels (removes) a request by its identifier.
     * Delegates removal to {@link RequestManager#removeRequest(int)}.
     * If the request existed, marks it as {@link RequestStatus#CLOSED}.
     *
     * @param id identifier of the request to cancel
     * @return {@link Optional} containing the removed {@link Request} if found, or empty otherwise
    */
    public Optional<Request> cancelRequest(int id) {
        Optional<Request> deleteRequest = requestManager.removeRequest(id);

        if(deleteRequest.isPresent()) {
            deleteRequest.get().setStatus(RequestStatus.CLOSED);
        }

        return deleteRequest;
    }

    /**
     * Closes (removes) an order by its identifier and sets the provided status.
     * Delegates removal to {@link OrderManager#removeOrder(int)}. If the order is found, attempts
     * to find the related book by {@link core.Order#getBookId()} and, if found, sets the book status
     * to {@link BookStatus#IN_ORDER}. Sets the order status to the provided {@link OrderStatus}.
     *
     * @param id identifier of the order to close
     * @param status status to assign to the order upon closing
     * @return {@link Optional} containing the removed {@link Order} if found, or empty otherwise
    */
    public Optional<Order> closeOrder(int id, OrderStatus status) {
        Optional<Order> deleteOrder = orderManager.removeOrder(id);

        if(deleteOrder.isPresent()) {
            Optional<Book> orderedBook = bookManager.findBook(deleteOrder.get().getBookId());
            if(orderedBook.isPresent()) {
                orderedBook.get().setStatus(BookStatus.IN_ORDER);
            }

            deleteOrder.get().setStatus(status);
        }

        return deleteOrder;
    }
}
