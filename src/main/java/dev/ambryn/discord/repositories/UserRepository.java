package dev.ambryn.discord.repositories;

import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.errors.DataAccessException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository {
    Logger logger = LoggerFactory.getLogger("UserRepository");
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<User> getUsers() {
        logger.debug("Fetching all users");
        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.select(cq.from(User.class));
        try {
            return em.createQuery(cq).getResultList();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch all users");
            throw new DataAccessException("Could not fetch all users", pe);
        }
    }

    public Optional<User> getUser(Long id) {
        try {
            logger.debug("Fetching user with id=" + id);
            User user = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
            logger.debug("Not user with id=" + id);
            return Optional.empty();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch user with id=" + id);
            throw new DataAccessException("Could not fetch user with id=" + id, pe);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            logger.debug("Fetching user with email=" + email);
            User user = (User) em
                    .createQuery("SELECT u FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
            logger.debug("No user with email=" + email);
           return Optional.empty();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch user with email=" + email);
            throw new DataAccessException("Could not fetch user with email=" + email, pe);
        }
    }

    @Transactional
    public void saveUser(User user) {
        try {
            logger.debug("{}", user);
            if (user.getId() != null) {
                logger.debug("Saving updated user");
                em.merge(user);
            } else {
                logger.debug("Persisting new user");
                em.persist(user);
            }
            em.flush();
        } catch (PersistenceException ex) {
            logger.error("Could not add User", ex);
            throw new DataAccessException("Could not add User", ex);
        }
    }
}
