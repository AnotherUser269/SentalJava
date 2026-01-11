package utils.sql;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.core.Book;
import components.core.Order;
import components.core.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSaver {
    private final Connection connection;

    public SQLSaver(Connection connection) {
        this.connection = connection;
    }

    public void save(BookArchive bookArchive, OrderArchive orderArchive, RequestArchive requestArchive) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        try {
            // Drop and create tables
            dropTables();
            createTables();

            // Save books
            String insertBookSQL = "INSERT INTO Books (id, title, author, description, timeStamp, price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement bookStatement = connection.prepareStatement(insertBookSQL)) {
                for (Book book : bookArchive.getAll()) {
                    bookStatement.setInt(1, book.getId());
                    bookStatement.setString(2, book.getTitle());
                    bookStatement.setString(3, book.getAuthor());
                    bookStatement.setString(4, book.getDescription());
                    bookStatement.setLong(5, book.getTimeStamp());
                    bookStatement.setBigDecimal(6, book.getPrice());
                    bookStatement.setString(7, book.getStatus().name());
                    bookStatement.addBatch();
                }
                bookStatement.executeBatch();
            }

            // Save orders
            String insertOrderSQL = "INSERT INTO Orders (id, bookId, startTime, phoneNumber, deliveryPrice, status, completionTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL)) {
                for (Order order : orderArchive.getAll()) {
                    orderStatement.setInt(1, order.getId());
                    orderStatement.setInt(2, order.getBookId());
                    orderStatement.setLong(3, order.getStartTime());
                    orderStatement.setString(4, order.getPhoneNumber());
                    orderStatement.setBigDecimal(5, order.getDeliveryPrice());
                    orderStatement.setString(6, order.getStatus().name());
                    orderStatement.setLong(7, order.getCompletionTime());
                    orderStatement.addBatch();
                }
                orderStatement.executeBatch();
            }

            // Save requests
            String insertRequestSQL = "INSERT INTO Requests (id, bookTitle, status) VALUES (?, ?, ?)";
            try (PreparedStatement requestStatement = connection.prepareStatement(insertRequestSQL)) {
                for (Request request : requestArchive.getAll()) {
                    requestStatement.setInt(1, request.getId());
                    requestStatement.setString(2, request.getBookTitle());
                    requestStatement.setString(3, request.getStatus().name());
                    requestStatement.addBatch();
                }
                requestStatement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
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