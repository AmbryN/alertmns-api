package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
        List<User> users = new ArrayList<>();
        try {
            users = em.createQuery(cq).getResultList();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch all users");
            throw new DataAccessException("Could not fetch all users", pe);
        }
        return users;
    }

    public Optional<User> getUser(Long id) {
        try {
            logger.debug("Fetching user with id=");
            User user = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
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
    public void addUser(User user) {
        try {
            logger.debug("Persisting new user");
            em.persist(user);
        } catch (PersistenceException ex) {
            logger.error("Could not add User", ex);
            throw new DataAccessException("Could not add User", ex);
        }
    }
}
