package dev.ambryn.discord.repositories;

import dev.ambryn.discord.beans.Group;
import dev.ambryn.discord.errors.DataAccessException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        try {
            return em.createQuery(cq).getResultList();
        } catch (PersistenceException pe) {
            logger.error("Could not fetch all Groups");
            throw new DataAccessException("Could not fetch all group", pe);
        }
    }

    public Optional<Group> getGroup(Long id) {
        try {
            logger.debug("Fetching group with id={}", id);
            Group group = (Group) em.createQuery("SELECT g FROM Group g JOIN FETCH g.members WHERE g.id = :id")
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

    @Transactional
    public void saveGroup(Group group) {
        try {
            logger.debug("Saving group={}", group);

            if (group.getId() != null) {
                em.merge(group);
            } else em.persist(group);
            em.flush();
        } catch (PersistenceException pe) {
            logger.error("Could not save group={}", group);
            throw new DataAccessException("Could not save group=" + group, pe);
        }
    }
}
