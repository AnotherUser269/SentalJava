package utils.DAO;

import java.util.List;

public interface GenericDAO<T, ID> {
    void create(T entity) throws Exception;
    T read(ID id) throws Exception;
    void update(T entity) throws Exception;
    void delete(ID id) throws Exception;
    List<T> findAll() throws Exception;
}
