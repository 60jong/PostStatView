package site._60jong.poststatview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostStatViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostStatViewApplication.class, args);
	}

}
