package site._60jong.poststatview.service.velog.stat;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import site._60jong.poststatview.domain.AuthInfo;
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

    public <T> VelogStat<T> postRequest(AuthInfo authInfo, VelogStatRequestBody body) {

        HttpHeaders headers = createHeadersByAuthInfo(authInfo);
        HttpEntity<VelogStatRequestBody> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<VelogStat<T>> response = restTemplate.exchange(VELOG_GRAPHQL_URL,
                                                                      POST,
                                                                      requestEntity,
                                                                      new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public <T> List<VelogStat<T>> postBatchRequest(AuthInfo authInfo, List<VelogStatRequestBody> bodies) {

        HttpHeaders headers = createHeadersByAuthInfo(authInfo);
        HttpEntity<List<VelogStatRequestBody>> requestEntity = new HttpEntity<>(bodies, headers);

        ResponseEntity<List<VelogStat<T>>> responses = restTemplate.exchange(VELOG_GRAPHQL_URL,
                                                                      POST,
                                                                      requestEntity,
                                                                      new ParameterizedTypeReference<>() {});
        return responses.getBody();
    }

    private HttpHeaders createHeadersByAuthInfo(AuthInfo authInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Cookie
        addRefreshTokenIfExists(headers, authInfo);

        return headers;
    }

    private void addRefreshTokenIfExists(HttpHeaders headers, AuthInfo authInfo) {
        if (authInfo.hasRefreshToken()) {
            headers.add("Cookie", "refresh_token=" + authInfo.getRefreshToken());
        }
    }
}
