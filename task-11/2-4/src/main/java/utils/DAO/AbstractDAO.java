package utils.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> implements GenericDAO<T> {
    protected Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    protected abstract T mapRowToEntity(ResultSet resultSet) throws Exception;

    @Override
    public List<T> findAll() throws Exception {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            entities.add(mapRowToEntity(resultSet));
        }

        return entities;
    }

    protected abstract String getTableName();
}
