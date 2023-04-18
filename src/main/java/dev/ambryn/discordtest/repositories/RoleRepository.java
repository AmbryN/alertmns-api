package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.errors.DataAccessException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dependent
public class RoleRepository {
    Logger logger = LoggerFactory.getLogger("RoleRepository");
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Role> getRoles() {
        logger.debug("Fetching all roles");
        CriteriaQuery<Role> cq = em.getCriteriaBuilder().createQuery(Role.class);
        cq.select(cq.from(Role.class));
        List<Role> roles = new ArrayList<>();
        try {
            roles = em.createQuery(cq).getResultList();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch all Roles");
            throw new DataAccessException("Could not fetch all roles", pe);
        }
        return roles;
    }

    public Optional<Role> getRole(Long id) {
        try {
            logger.debug("Fetching role with id={}", id);
            Role role = (Role) em.createQuery("SELECT r FROM Role r WHERE r.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(role);
        } catch (NoResultException ex) {
            logger.debug("Not role with id=" + id);
            return Optional.empty();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch role with id=" + id);
            throw new DataAccessException("Could not fetch role with id=" + id, pe);
        }
    }
}
