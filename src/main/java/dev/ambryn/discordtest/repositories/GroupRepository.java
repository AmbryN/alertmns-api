package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Group;
import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.controllers.GroupController;
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
public class GroupRepository {
    Logger logger = LoggerFactory.getLogger("GroupRepository");
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Group> getGroups() {
        logger.debug("Fetching all group");
        CriteriaQuery<Group> cq = em.getCriteriaBuilder().createQuery(Group.class);
        cq.select(cq.from(Group.class));
        List<Group> groups = new ArrayList<>();
        try {
            groups = em.createQuery(cq).getResultList();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch all Groups");
            throw new DataAccessException("Could not fetch all group", pe);
        }
        return groups;
    }

    public Optional<Group> getGroup(Long id) {
        try {
            logger.debug("Fetching group with id={}", id);
            Group group = (Group) em.createQuery("SELECT g FROM Group g WHERE g.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(group);
        } catch (NoResultException ex) {
            logger.debug("Not role with id=" + id);
            return Optional.empty();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch group with id=" + id);
            throw new DataAccessException("Could not fetch group with id=" + id, pe);
        }
    }

    public Group saveGroup(Group group) {
        try {
            logger.debug("Saving group={}", group);

            if (group.getId() != null) {
                em.merge(group);
            } else em.persist(group);

            return group;
        } catch (PersistenceException pe) {
            logger.error("Could not save group={}", group);
            throw new DataAccessException("Could not save group=" + group, pe);
        }
    }
}
