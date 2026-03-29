package utils;

import components.archive.*;
import components.core.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utils.DAO.BookDAO;
import utils.DAO.OrderDAO;
import utils.DAO.RequestDAO;

@Service
@Transactional
public class JPASaver {
    private final BookDAO bookRepo;
    private final OrderDAO orderRepo;
    private final RequestDAO requestRepo;

    public JPASaver(BookDAO b, OrderDAO o, RequestDAO r) {
        this.bookRepo = b;
        this.orderRepo = o;
        this.requestRepo = r;
    }

    public void save(BookArchive bookArchive, OrderArchive orderArchive, RequestArchive requestArchive) throws Exception {
        for (Book b : bookArchive.getAll()) {
            bookRepo.create(b);
        }
        for (Order o : orderArchive.getAll()) {
            orderRepo.create(o);
        }
        for (Request r : requestArchive.getAll()) {
            requestRepo.create(r);
        }
    }
}
