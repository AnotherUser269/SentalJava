package utils;

import components.archive.*;
import components.catalog.*;
import components.core.*;
import utils.DAO.BookDAO;
import utils.DAO.OrderDAO;
import utils.DAO.RequestDAO;

import jakarta.persistence.EntityManager;
import java.util.List;

public class JPALoader {
    private final EntityManager em;
    private final BookDAO bookRepo;
    private final OrderDAO orderRepo;
    private final RequestDAO requestRepo;

    public JPALoader(EntityManager em, BookDAO b, OrderDAO o, RequestDAO r) {
        this.em = em; this.bookRepo = b; this.orderRepo = o; this.requestRepo = r;
    }

    public void load(BookArchive bookArchive, BookCatalog bookCatalog,
                     OrderArchive orderArchive, OrderCatalog orderCatalog,
                     RequestArchive requestArchive, RequestCatalog requestCatalog) throws Exception {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            List<Book> books = bookRepo.findAll();
            books.forEach(bookCatalog::put);
            books.forEach(bookArchive::put);

            List<Order> orders = orderRepo.findAll();
            orders.forEach(orderCatalog::put);
            orders.forEach(orderArchive::put);

            List<Request> requests = requestRepo.findAll();
            requests.forEach(requestCatalog::put);
            requests.forEach(requestArchive::put);

            if (em.getTransaction().isActive()) em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        }
    }
}
