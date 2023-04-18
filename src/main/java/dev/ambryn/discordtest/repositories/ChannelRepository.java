package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.errors.DataAccessException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Dependent
public class ChannelRepository {
    private final Logger logger = LoggerFactory.getLogger("ChannelRepository");
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Transactional
    public void saveChannel(Channel channel) {
        try {
            if (channel.getId() != null) {
                em.merge(channel);
            } else {
                em.persist(channel);
            }
            em.flush();
        } catch (PersistenceException pe) {
            logger.error("Could not create Channel={}", channel);
            throw new DataAccessException("Could not create Channel="+channel, pe);
        }
    }

    @Transactional
    public Optional<Channel> getChannel(Long id) {
        try {
            Channel channel = (Channel) em.createQuery("SELECT c FROM Channel c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(channel);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
