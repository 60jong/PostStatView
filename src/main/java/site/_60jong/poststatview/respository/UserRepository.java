package site._60jong.poststatview.respository;

import org.springframework.stereotype.Repository;
import site._60jong.poststatview.domain.User;

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

    public void updateRefreshToken(String username, String refreshToken) {

    }
}
