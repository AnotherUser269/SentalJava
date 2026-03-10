package utils;

import components.archive.*;
import components.core.*;
import utils.DAO.BookDAO;
import utils.DAO.OrderDAO;
import utils.DAO.RequestDAO;

import jakarta.persistence.*;

public class JPASaver {
    private final EntityManager em;
    private final BookDAO bookRepo;
    private final OrderDAO orderRepo;
    private final RequestDAO requestRepo;

    public JPASaver(EntityManager em, BookDAO b, OrderDAO o, RequestDAO r) {
        this.em = em; this.bookRepo = b; this.orderRepo = o; this.requestRepo = r;
    }

    public void save(BookArchive bookArchive, OrderArchive orderArchive, RequestArchive requestArchive) throws Exception {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            for (Book b : bookArchive.getAll()) {
                bookRepo.create(b);
            }
            for (Order o : orderArchive.getAll()) {
                orderRepo.create(o);
            }
            for (Request r : requestArchive.getAll()) {
                requestRepo.create(r);
            }

            if (em.getTransaction().isActive()) em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }
}
