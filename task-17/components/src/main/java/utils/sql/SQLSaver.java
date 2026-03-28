package utils.sql;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.core.Book;
import components.core.Order;
import components.core.Request;
import utils.DAO.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class SQLSaver {
    private final EntityManager entityManager;
    private final BookDAO bookDAO;
    private final OrderDAO orderDAO;
    private final RequestDAO requestDAO;

    public SQLSaver(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bookDAO = new BookDAO(entityManager);
        this.orderDAO = new OrderDAO(entityManager);
        this.requestDAO = new RequestDAO(entityManager);
    }

    public void save(BookArchive bookArchive, OrderArchive orderArchive, RequestArchive requestArchive) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
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

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error saving data to the database: " + e.getMessage(), e);
        }
    }
}
