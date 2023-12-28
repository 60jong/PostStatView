package site._60jong.poststatview.service.velog.stat;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import site._60jong.poststatview.service.velog.request.VelogStatRequestBody;
import site._60jong.poststatview.service.velog.response.VelogStat;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

public class VelogRestTemplate {

    private final String VELOG_GRAPHQL_URL;

    private final RestTemplate restTemplate;

    public VelogRestTemplate(String url, RestTemplate restTemplate) {

        this.VELOG_GRAPHQL_URL = url;
        this.restTemplate = restTemplate;
    }

    public <T> VelogStat<T> postRequest(VelogStatRequestBody body) {

        HttpHeaders headers = getHeaders();
        HttpEntity<VelogStatRequestBody> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<VelogStat<T>> response = restTemplate.exchange(VELOG_GRAPHQL_URL,
                                                                      POST,
                                                                      requestEntity,
                                                                      new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public <T> List<VelogStat<T>> postBatchRequest(List<VelogStatRequestBody> bodies) {

        HttpHeaders headers = getHeaders();
        HttpEntity<List<VelogStatRequestBody>> requestEntity = new HttpEntity<>(bodies, headers);

        ResponseEntity<List<VelogStat<T>>> responses = restTemplate.exchange(VELOG_GRAPHQL_URL,
                                                                      POST,
                                                                      requestEntity,
                                                                      new ParameterizedTypeReference<>() {});
        return responses.getBody();
    }

    private static HttpHeaders getHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Cookie
        headers.add("Cookie", "refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZTIxMTY0ZWEtNGFmYy00M2JiLTk0ZDctN2UyMjFmMTA3ZDQyIiwidG9rZW5faWQiOiI0NTQzNDc3Yi05NjNlLTQ2MjMtYThmYS0zMzM1OTE4NjI5YTYiLCJpYXQiOjE3MDM1NjQxNDAsImV4cCI6MTcwNjE1NjE0MCwiaXNzIjoidmVsb2cuaW8iLCJzdWIiOiJyZWZyZXNoX3Rva2VuIn0.hS4cUh-O-5ECRr9h64zNo3OV0XJu-_lkjsy1GsQhuAQ");

        return headers;
    }
}
