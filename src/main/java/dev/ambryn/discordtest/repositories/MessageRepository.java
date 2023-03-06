package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.errors.DataAccessException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;


@Dependent
public class MessageRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Transactional
    public void addMessage(Message message) {
        try {
            em.persist(message);
        } catch (PersistenceException ex) {
            throw new DataAccessException("Could not create Message", ex);
        }
    }
}
