package utils.DAO;

import java.util.List;

public interface GenericDAO<T> {
    void create(T entity) throws Exception;
    T read(int id) throws Exception;
    void update(T entity) throws Exception;
    void delete(int id) throws Exception;
    List<T> findAll() throws Exception;
}
