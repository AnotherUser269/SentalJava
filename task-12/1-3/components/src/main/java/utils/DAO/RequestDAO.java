package utils.DAO;

import components.core.Request;
import components.status_enums.RequestStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RequestDAO extends AbstractDAO<Request> {

    public RequestDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected Request mapRowToEntity(ResultSet resultSet) throws Exception {
        Request request = new Request(
                resultSet.getInt("id"),
                resultSet.getString("bookTitle")
        );
        String statusRaw = resultSet.getString("status");
        request.setStatus(RequestStatus.valueOf(statusRaw));
        return request;
    }

    @Override
    protected String getTableName() {
        return "Requests";
    }

    @Override
    public void create(Request request) throws Exception {
        String query = "INSERT INTO Requests (id, bookTitle, status) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, request.getId());
        statement.setString(2, request.getBookTitle());
        statement.setString(3, request.getStatus().name());
        statement.executeUpdate();
    }

    @Override
    public Request read(int id) throws Exception {
        String query = "SELECT * FROM Requests WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapRowToEntity(resultSet);
        }
        return null;
    }

    @Override
    public void update(Request request) throws Exception {
        delete(request.getId());
        create(request);
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Requests WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
