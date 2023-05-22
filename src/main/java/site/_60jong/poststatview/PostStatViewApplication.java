package site._60jong.poststatview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.io.File;


@EnableJpaAuditing
@SpringBootApplication
public class PostStatViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostStatViewApplication.class, args);
	}

	@Bean
	public File backgroundImgFile() {
		return new File("src/main/resources/static/viewimage/view-background.png");
	}
}
