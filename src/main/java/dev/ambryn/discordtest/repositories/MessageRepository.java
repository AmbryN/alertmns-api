package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.errors.DataConflictException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Dependent
public class MessageRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Transactional
    public void addMessage(Message message) {
        try {
            em.persist(message);
        } catch (PersistenceException ex) {
            final Throwable cause = ex.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new DataConflictException(cause);
            }
            throw new DataAccessException(ex);
        }
    }
}
