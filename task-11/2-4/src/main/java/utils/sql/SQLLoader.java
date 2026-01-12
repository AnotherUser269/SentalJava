package utils.sql;

import components.archive.*;
import components.catalog.*;
import components.core.*;
import components.status_enums.*;
import utils.DAO.*;

import java.sql.Connection;

public class SQLLoader {
    Connection connection;

    public SQLLoader(Connection connection) {
        this.connection = connection;
    }

    public void load(BookArchive bookArchive, BookCatalog bookCatalog,
                     OrderArchive orderArchive, OrderCatalog orderCatalog,
                     RequestArchive requestArchive, RequestCatalog requestCatalog) throws Exception {
        bookArchive.clearAll(); orderArchive.clearAll(); requestArchive.clearAll();
        bookCatalog.clearAll(); orderCatalog.clearAll(); requestCatalog.clearAll();

        BookDAO bookDAO = new BookDAO(connection);

        // Books
        for (Book book : bookDAO.findAll()) {
            bookArchive.put(book);
            if (book.getStatus() == BookStatus.InOrder) {
                bookCatalog.put(book);
            }
        }

        // Orders
        OrderDAO orderDAO = new OrderDAO(connection);
        for (Order order : orderDAO.findAll()) {
            orderArchive.put(order);
            if (order.getStatus() == OrderStatus.New) {
                orderCatalog.put(order);
            }
        }

        // Requests
        RequestDAO requestDAO = new RequestDAO(connection);
        for (Request request : requestDAO.findAll()) {
            requestArchive.put(request);
            if (request.getStatus() == RequestStatus.Opened) {
                requestCatalog.put(request);
            }
        }
    }
}
