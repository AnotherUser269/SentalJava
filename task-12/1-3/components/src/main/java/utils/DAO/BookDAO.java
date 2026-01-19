package utils.DAO;

import components.core.Book;
import components.status_enums.BookStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDAO extends AbstractDAO<Book> {

    public BookDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected Book mapRowToEntity(ResultSet resultSet) throws Exception {
        Book book = new Book(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getString("description"),
                resultSet.getLong("timeStamp"),
                resultSet.getBigDecimal("price")
        );
        String statusRaw = resultSet.getString("status");
        book.setStatus(BookStatus.valueOf(statusRaw));
        return book;
    }

    @Override
    protected String getTableName() {
        return "Books";
    }

    @Override
    public void create(Book book) throws Exception {
        String query = "INSERT INTO Books (id, title, author, description, timeStamp, price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, book.getId());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.setString(4, book.getDescription());
        statement.setLong(5, book.getTimeStamp());
        statement.setBigDecimal(6, book.getPrice());
        statement.setString(7, book.getStatus().name());
        statement.executeUpdate();
    }

    @Override
    public Book read(int id) throws Exception {
        String query = "SELECT * FROM Books WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapRowToEntity(resultSet);
        }
        return null;
    }

    @Override
    public void update(Book book) throws Exception {
        delete(book.getId());
        create(book);
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Books WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
