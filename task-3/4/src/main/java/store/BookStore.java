package store;

import core.Order;
import core.Book;
import core.Request;
import enums.BookStatus;
import enums.OrderStatus;
import enums.RequestStatus;
import manager.BookManager;
import manager.OrderManager;
import manager.RequestManager;

import java.util.Optional;

public class BookStore {
    private String name;
    private BookManager bookManager;
    private OrderManager orderManager;
    private RequestManager requestManager;

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

    public Request createRequest(String bookTitle) {
        return requestManager.createRequest(bookTitle);
    }

    public Order createOrder(String bookTitle) throws Exception {
        Optional<Book> bookToOrder = bookManager.findBookByTitle(bookTitle);

        if(bookToOrder.isPresent()) {
            bookToOrder.get().setStatus(BookStatus.NOT_IN_ORDER);
            return orderManager.createOrder(bookToOrder.get().getId());
        } else {
            throw new Exception("You are ordering a non existing book!");
        }
    }

    public Optional<Book> findBook(String bookTitle) {
        return bookManager.findBookByTitle(bookTitle);
    }

    public Book addBook(String bookTitle) {
        Book newBook = bookManager.addBook(bookTitle);
        Optional<Request> request = requestManager.findRequestByTitle(bookTitle);

        if(request.isPresent()) {
            request.get().setStatus(RequestStatus.CLOSED);
            requestManager.removeRequest(request.get().getId());
        }

        return newBook;
    }

    public Optional<Book> removeBook(int id) {
        Optional<Book> deletedBook = bookManager.removeBook(id);

        if(deletedBook.isPresent()) {
            deletedBook.get().setStatus(BookStatus.NOT_IN_ORDER);
        }

        return deletedBook;
    }

    public Optional<Request> cancelRequest(int id) {
        Optional<Request> deleteRequest = requestManager.removeRequest(id);

        if(deleteRequest.isPresent()) {
            deleteRequest.get().setStatus(RequestStatus.CLOSED);
        }

        return deleteRequest;
    }

    public Optional<Order> closeOrder(int id) {
        Optional<Order> deleteOrder = orderManager.removeOrder(id);

        if(deleteOrder.isPresent()) {
            Optional<Book> orderedBook = bookManager.findBook(deleteOrder.get().getBookId());
            if(orderedBook.isPresent()) {
                orderedBook.get().setStatus(BookStatus.IN_ORDER);
            }

            deleteOrder.get().setStatus(OrderStatus.DISMISSED);
        }

        return deleteOrder;
    }
}
