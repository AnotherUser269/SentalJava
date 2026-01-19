package utils.DAO;

import components.core.Order;
import components.status_enums.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected Order mapRowToEntity(ResultSet resultSet) throws Exception {
        Order order = new Order(
                resultSet.getInt("id"),
                resultSet.getInt("bookId"),
                resultSet.getLong("startTime"),
                resultSet.getString("phoneNumber"),
                resultSet.getBigDecimal("deliveryPrice"),
                OrderStatus.New,
                resultSet.getLong("completionTime")
        );
        String statusRaw = resultSet.getString("status");
        order.setStatus(OrderStatus.valueOf(statusRaw));
        return order;
    }

    @Override
    protected String getTableName() {
        return "Orders";
    }

    @Override
    public void create(Order order) throws Exception {
        String query = "INSERT INTO Orders (id, bookId, startTime, phoneNumber, deliveryPrice, status, completionTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, order.getId());
        statement.setInt(2, order.getBookId());
        statement.setLong(3, order.getStartTime());
        statement.setString(4, order.getPhoneNumber());
        statement.setBigDecimal(5, order.getDeliveryPrice());
        statement.setString(6, order.getStatus().name());
        statement.setLong(7, order.getCompletionTime());
        statement.executeUpdate();
    }

    @Override
    public Order read(int id) throws Exception {
        String query = "SELECT * FROM Orders WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapRowToEntity(resultSet);
        }
        return null;
    }

    @Override
    public void update(Order order) throws Exception {
        delete(order.getId());
        create(order);
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Orders WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
