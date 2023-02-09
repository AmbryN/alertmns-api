package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.User;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public Iterable<User> getUsers() {
        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    public User getUser(Long id) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
