package site.jongky.poststatview.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(length = 500)
    private String refreshToken;

    public User(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }
}
