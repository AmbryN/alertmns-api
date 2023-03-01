package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Dependent
public class ChannelRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Transactional
    public Optional<Channel> createChannel(Channel channel) {
        try {
            em.persist(channel);
            em.flush();
            return Optional.of(channel);
        } catch (PersistenceException e) {
            return Optional.empty();
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

    public Optional<List<Message>> getMessages(Long id) {
        return getChannel(id).flatMap(channel -> Optional.ofNullable(channel.getMessages()));
    }

    @Transactional
    public void updateChannel(Channel channel) {
        em.merge(channel);
    }
}
