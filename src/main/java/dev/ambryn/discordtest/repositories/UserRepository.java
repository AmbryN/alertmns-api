package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.User;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;

import java.util.List;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    public Iterable<User> getUsers() {
//        Connection conn = null;
//        List<User> users = new ArrayList<>();
//        try {
//            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/discord", "root", "root");
//            PreparedStatement query = conn.prepareStatement("SELECT * FROM User");
//            ResultSet result = query.executeQuery();
//            while (result.next()) {
//                User user = new User(
//                        result.getString("usr_email"),
//                        result.getString("usr_firstname"),
//                        result.getString("usr_lastname"),
//                        result.getString("usr_password")
//                );
//                users.add(user);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return users;
//

//        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
//        cq.select(cq.from(User.class));
//        return em.createQuery(cq).getResultList();
        return (Iterable<User>) em.createQuery("SELECT u FROM User u").getResultList();
    }

    public User getUser(Long id) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
