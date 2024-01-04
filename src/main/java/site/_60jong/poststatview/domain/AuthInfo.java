package site._60jong.poststatview.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(length = 511)
    private String refreshToken;

    public AuthInfo(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public boolean hasRefreshToken() {
        return StringUtils.hasText(this.refreshToken);
    }

    public void registerToken(String token) {
        this.refreshToken = token;
    }
}