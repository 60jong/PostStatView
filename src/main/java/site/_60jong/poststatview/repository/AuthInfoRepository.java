package site._60jong.poststatview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site._60jong.poststatview.domain.AuthInfo;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    Optional<AuthInfo> findByUsername(String username);

    boolean existsByUsername(String username);
}
