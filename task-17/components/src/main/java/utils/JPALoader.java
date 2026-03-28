package utils;

import components.archive.*;
import components.catalog.*;
import components.core.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utils.DAO.BookDAO;
import utils.DAO.OrderDAO;
import utils.DAO.RequestDAO;

import jakarta.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class JPALoader {
    private final BookDAO bookRepo;
    private final OrderDAO orderRepo;
    private final RequestDAO requestRepo;

    public JPALoader(BookDAO b, OrderDAO o, RequestDAO r) {
        this.bookRepo = b; this.orderRepo = o; this.requestRepo = r;
    }

    public void load(BookArchive bookArchive, BookCatalog bookCatalog,
                     OrderArchive orderArchive, OrderCatalog orderCatalog,
                     RequestArchive requestArchive, RequestCatalog requestCatalog) throws Exception {
        List<Book> books = bookRepo.findAll();
        books.forEach(bookCatalog::put);
        books.forEach(bookArchive::put);

        List<Order> orders = orderRepo.findAll();
        orders.forEach(orderCatalog::put);
        orders.forEach(orderArchive::put);

        List<Request> requests = requestRepo.findAll();
        requests.forEach(requestCatalog::put);
        requests.forEach(requestArchive::put);
    }
}
