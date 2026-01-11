package utils.sql;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.catalog.BookCatalog;
import components.catalog.OrderCatalog;
import components.catalog.RequestCatalog;
import components.core.Book;
import components.core.Order;
import components.core.Request;
import components.status_enums.BookStatus;
import components.status_enums.OrderStatus;
import components.status_enums.RequestStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

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

        Statement statement = connection.createStatement();

        // Books
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Books");

        while (resultSet.next()) {
            String bookStatusRaw = resultSet.getString("status");
            BookStatus bookStatus;

            if (Objects.equals(bookStatusRaw, "InOrder")) {
                bookStatus = BookStatus.InOrder;
            } else if (Objects.equals(bookStatusRaw, "NotInOrder")) {
                bookStatus = BookStatus.NotInOrder;
            } else {
                throw new Exception("Wrong book status!");
            }

            Book newBook = new Book(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("description"),
                    resultSet.getLong("timeStamp"),
                    resultSet.getBigDecimal("price")
            );

            newBook.setStatus(bookStatus);

            if(bookStatus == BookStatus.InOrder) {
                bookCatalog.put(newBook);
            }

            bookArchive.put(newBook);
        }

        // Orders
        resultSet = statement.executeQuery("SELECT * FROM Orders");

        while (resultSet.next()) {
            String orderStatusRaw = resultSet.getString("status");
            OrderStatus orderStatus;

            if (Objects.equals(orderStatusRaw, "New")) {
                orderStatus = OrderStatus.New;
            } else if (Objects.equals(orderStatusRaw, "Dismissed")) {
                orderStatus = OrderStatus.Dismissed;
            } else if (Objects.equals(orderStatusRaw, "Success")) {
                orderStatus = OrderStatus.Success;
            } else {
                throw new Exception("Wrong order status!");
            }

            Order newOrder = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("bookId"),
                    resultSet.getLong("startTime"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getBigDecimal("deliveryPrice"),
                    orderStatus,
                    resultSet.getLong("completionTime")
            );

            if(orderStatus == OrderStatus.New) {
                orderCatalog.put(newOrder);
            }

            orderArchive.put(newOrder);
        }

        // Requests
        resultSet = statement.executeQuery("SELECT * FROM Requests");

        while (resultSet.next()) {
            String requestStatusRaw = resultSet.getString("status");
            RequestStatus requestStatus;

            if (Objects.equals(requestStatusRaw, "Opened")) {
                requestStatus = RequestStatus.Opened;
            } else if (Objects.equals(requestStatusRaw, "Closed")) {
                requestStatus = RequestStatus.Closed;
            } else {
                throw new Exception("Wrong request status!");
            }

            Request newRequest = new Request(
                    resultSet.getInt("id"),
                    resultSet.getString("bookTitle")
            );

            newRequest.setStatus(requestStatus);

            if(requestStatus == RequestStatus.Opened) {
                requestCatalog.put(newRequest);
            }

            requestArchive.put(newRequest);
        }
    }
}
