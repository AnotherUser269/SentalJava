package utils.sql;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.core.Book;
import components.core.Order;
import components.core.Request;
import utils.DAO.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSaver {
    private final Connection connection;
    private final BookDAO bookDAO;
    private final OrderDAO orderDAO;
    private final RequestDAO requestDAO;

    public SQLSaver(Connection connection) {
        this.connection = connection;
        this.bookDAO = new BookDAO(connection);
        this.orderDAO = new OrderDAO(connection);
        this.requestDAO = new RequestDAO(connection);
    }

    public void save(BookArchive bookArchive, OrderArchive orderArchive, RequestArchive requestArchive) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        try {
            // Drop and create tables
            dropTables();
            createTables();

            // Save books
            for (Book book : bookArchive.getAll()) {
                bookDAO.create(book);
            }

            // Save orders
            for (Order order : orderArchive.getAll()) {
                orderDAO.create(order);
            }

            // Save requests
            for (Request request : requestArchive.getAll()) {
                requestDAO.create(request);
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Error saving data to the database: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private void dropTables() throws SQLException {
        String dropOrdersSQL = "DROP TABLE IF EXISTS Orders";
        String dropBooksSQL = "DROP TABLE IF EXISTS Books";
        String dropRequestsSQL = "DROP TABLE IF EXISTS Requests";

        try (Statement statement = connection.createStatement()) {
            statement.execute(dropOrdersSQL);
            statement.execute(dropBooksSQL);
            statement.execute(dropRequestsSQL);
        }
    }

    private void createTables() throws SQLException {
        String createBooksSQL = "CREATE TABLE Books (" +
                "id INTEGER PRIMARY KEY, " +
                "title VARCHAR(200), " +
                "author VARCHAR(100), " +
                "description TEXT, " +
                "timeStamp BIGINT, " +
                "price NUMERIC(10, 2), " +
                "status VARCHAR(15))";

        String createOrdersSQL = "CREATE TABLE Orders (" +
                "id INTEGER PRIMARY KEY, " +
                "bookId INTEGER, " +
                "startTime BIGINT, " +
                "phoneNumber VARCHAR(25), " +
                "deliveryPrice NUMERIC(10, 2), " +
                "status VARCHAR(15), " +
                "completionTime BIGINT, " +
                "FOREIGN KEY (bookId) REFERENCES Books(id))";

        String createRequestsSQL = "CREATE TABLE Requests (" +
                "id INTEGER PRIMARY KEY, " +
                "bookTitle VARCHAR(200), " +
                "status VARCHAR(15))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createBooksSQL);
            statement.execute(createOrdersSQL);
            statement.execute(createRequestsSQL);
        }
    }
}
