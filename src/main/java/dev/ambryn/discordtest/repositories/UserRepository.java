package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.errors.DataConflictException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
//    @Resource
//    private UserTransaction utx;


    public Iterable<User> getUsers() {
        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    public Optional<User> getUser(Long id) {
        try {
            User user = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(user);

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            User user = (User) em
                    .createQuery("SELECT u FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
           return Optional.empty();
        }
    }

    @Transactional
    public void addUser(User user) {
        try {
            em.persist(user);
        } catch (PersistenceException ex) {
            final Throwable cause = ex.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new DataConflictException(cause);
            }
            throw new DataAccessException(ex);
        }
    }
}
