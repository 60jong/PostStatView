package site._60jong.poststatview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site._60jong.poststatview.domain.AuthInfo;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthInfoRepository {

    private final EntityManager em;

    public void save(AuthInfo authInfo) {

        em.persist(authInfo);
    }

    public Optional<AuthInfo> findByUsername(String username) {

        return em.createQuery("select a from AuthInfo a where a.username = :username", AuthInfo.class)
                .setParameter("username", username)
                .getResultStream()
                .findAny();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }
}
