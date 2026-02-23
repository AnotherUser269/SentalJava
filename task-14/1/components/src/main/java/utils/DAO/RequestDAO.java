package utils.DAO;

import components.core.Request;

import jakarta.persistence.EntityManager;

public class RequestDAO extends AbstractDAO<Request, Integer> {
    public RequestDAO(EntityManager em) {
        super(em, Request.class);
    }
}
