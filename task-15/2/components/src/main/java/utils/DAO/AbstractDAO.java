package utils.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractDAO<T, ID> implements GenericDAO<T, ID> {
    protected final EntityManager em;
    private final Class<T> entityClass;

    public AbstractDAO(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) throws Exception {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public T read(ID id) throws Exception {
        return em.find(entityClass, id);
    }

    @Override
    public void update(T entity) throws Exception {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(ID id) throws Exception {
        em.getTransaction().begin();
        T ref = em.find(entityClass, id);
        if (ref != null) em.remove(ref);
        em.getTransaction().commit();
    }

    @Override
    public List<T> findAll() throws Exception {
        String q = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query = em.createQuery(q, entityClass);
        return query.getResultList();
    }
}
