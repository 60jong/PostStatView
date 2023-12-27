package site._60jong.poststatview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import site._60jong.poststatview.service.velog.VelogRestTemplate;

@Configuration
public class VelogConfig {

    @Bean
    public VelogRestTemplate velogRestTemplate(@Value("${datasource.velog.graphql.url}") String requestUrl) {

        return new VelogRestTemplate(requestUrl, restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}