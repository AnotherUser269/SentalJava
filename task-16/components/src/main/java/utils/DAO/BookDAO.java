package utils.DAO;

import components.core.Book;

import jakarta.persistence.EntityManager;

public class BookDAO extends AbstractDAO<Book, Integer> {
    public BookDAO(EntityManager em) {
        super(em, Book.class);
    }
}
