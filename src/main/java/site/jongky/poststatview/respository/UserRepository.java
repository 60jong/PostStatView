package site.jongky.poststatview.respository;

import org.springframework.stereotype.Repository;
import site.jongky.poststatview.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(User user) {
        em.persist(user);

        return user.getId();
    }

    public Optional<User> findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findAny();
    }
}
