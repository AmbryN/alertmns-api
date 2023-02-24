package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.errors.DataConflictException;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Dependent
public class ChannelRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

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

    public List<Message> getMessages(Long id) {
        Optional<Channel> channelOptional = getChannel(id);
        if (channelOptional.isPresent()) {
            return channelOptional.get().getMessages();
        }
        throw new RuntimeException();
    }

    @Transactional
    public void updateChannel(Channel channel) {
        em.merge(channel);
    }
}
