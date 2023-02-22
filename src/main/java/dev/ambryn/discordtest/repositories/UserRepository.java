package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.errors.DataException;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.*;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    @Resource
    private UserTransaction utx;


    public Iterable<User> getUsers() {
        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
//        return (Iterable<User>) em.createQuery("SELECT u FROM User u").getResultList();
    }

    public User getUser(Long id) {
        User user = null;
        try {
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
throw new NoResultException("Test");
//            return user;

        } catch (NoResultException ex) {
            throw new NoResultException("Did not find User with id=" + id);
        }

    }

    public User addUser(User user) {
        try {
            utx.begin();
            em.persist(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
                throw new DataException("[UserRepository - addUser] could not add User - transaction rolled back");
            } catch (IllegalStateException | SecurityException | SystemException e2) {
                throw new DataException("[UserRepository - addUser] could not rollback transaction");
            }
        }
        return (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", user.getEmail())
                .getSingleResult();
    }
}
