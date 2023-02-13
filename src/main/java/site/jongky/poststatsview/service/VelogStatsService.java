package site.jongky.poststatsview.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.jongky.poststatsview.dto.VelogGetPostIdsRequest;
import site.jongky.poststatsview.dto.VelogGetPostStatsRequest;
import site.jongky.poststatsview.dto.VelogOperationRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class VelogStatsService {
    private static final String VELOG_GRAPHQL_URL = "https://v2cdn.velog.io/";
    private JSONParser parser = new JSONParser();

    public List<String> findAllPostIdsByUsername(String username, String refreshToken) throws ParseException {
        String postIdJson = postWith(new VelogGetPostIdsRequest(username), refreshToken);
        JSONArray posts = getPostJsonArray(postIdJson);

        List<String> postIds = new ArrayList<>();
        for (int postIndex = 0; postIndex < posts.size(); postIndex++) {
            JSONObject post = (JSONObject) posts.get(postIndex);
            String postId = (String) post.get("id");
            postIds.add(postId);
        }

        return postIds;
    }

    // Velog GraphQL에 POST요청을 보내고, 응답 json을 String으로 리턴
    public String postWith(VelogOperationRequest velogOperationRequest, String refreshToken) {
        // header에 JSON 타입의 Body임을 명시
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("cookie", "refresh_token="+refreshToken);

        // Http 요청에 포함할 body, header를 포함
        HttpEntity<String> entity = new HttpEntity(velogOperationRequest.toJson(), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(VELOG_GRAPHQL_URL, entity, String.class);
    }

    private JSONArray getPostJsonArray(String postIdJson) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(postIdJson);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray posts = (JSONArray) data.get("posts");

        return posts;
    }

    public Long getTotalReads(List<String> postIds, String refreshToken) throws ParseException {
        Long totalReads = 0L;

        for (String postId : postIds) {
            String totalReadsJson = postWith(new VelogGetPostStatsRequest(postId), refreshToken);

            JSONObject jsonObject = (JSONObject)parser.parse(totalReadsJson);
            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONObject getStats = (JSONObject) data.get("getStats");

            totalReads += (Long) getStats.get("total");
        }

        return totalReads;
    }
}
