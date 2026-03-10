package utils.DAO;

import components.core.Order;

import jakarta.persistence.EntityManager;

public class OrderDAO extends AbstractDAO<Order, Integer> {
    public OrderDAO(EntityManager em) {
        super(em, Order.class);
    }
}
