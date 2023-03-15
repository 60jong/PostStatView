package site.jongky.poststatview.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.jongky.poststatview.dto.velog.VelogGetPostIdsRequest;
import site.jongky.poststatview.dto.velog.VelogGetStatsRequest;
import site.jongky.poststatview.dto.velog.VelogOperationRequest;
import site.jongky.poststatview.dto.velog.VelogUserTagsRequest;
import site.jongky.poststatview.exception.PostStatViewException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.jongky.poststatview.exception.PostStatViewResponseStatus.*;

@Service
public class VelogStatService {
    @Value("${datasource.velog.graphql.url}")
    private String VELOG_GRAPHQL_URL;
    @Value("${datasource.velog.graphql.batch.size.max}")
    private int VELOG_GRAPHQL_BATCH_MAX_SIZE;
    @Value("${datasource.velog.graphql.user.tag.size.max}")
    private int VELOG_GRAPHQL_USER_TAGS_MAX_SIZE;

    private JSONParser parser = new JSONParser();

    // 유저의 총 게시글 수를 조회
    public int getTotalPosts(String username) {
        return askAllPostIdsByUsername(username).size();
    }

    // 유저의 게시글 태그를 태그가 많은 순으로 조회
    public List<String> getTags(String username) {
        return askAllTagsByUsername(username)
                .subList(0, VELOG_GRAPHQL_USER_TAGS_MAX_SIZE);
    }

    // 유저의 총 방문자를 조회
    public Long getTotalVisitors(String username, String refreshToken) {
        List<String> postIds = askAllPostIdsByUsername(username);
        List<VelogOperationRequest> batchRequests = creatBatchRequests(postIds);
        List<String> visitorJsonsBatch = postWith(batchRequests, refreshToken);

        long totalVisitors = 0l;

        try {
            for (String batch : visitorJsonsBatch) {
                JSONArray visitorJsons = (JSONArray) parser.parse(batch);
                for (int i = 0; i < visitorJsons.size(); i++) {
                    totalVisitors += getVisitors((JSONObject) visitorJsons.get(i));
                }
            }
            return totalVisitors;
        } catch (ParseException exception) {
            throw new PostStatViewException(POST_VISITORS_JSON_PARSING_FAILURE);
        }
    }

    public List<String> askAllPostIdsByUsername(String username) {
        String postIdJson = postWith(new VelogGetPostIdsRequest(username));
        JSONArray posts = getPostJsonArray(postIdJson);

        List<String> postIds = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            JSONObject post = (JSONObject) posts.get(i);
            String postId = (String) post.get("id");
            postIds.add(postId);
        }

        return postIds;
    }

    public List<String> askAllTagsByUsername(String username) {
        String userTagsJson = postWith(new VelogUserTagsRequest(username));
        JSONArray tagJsonArray = getTagsJsonArray(userTagsJson);

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < tagJsonArray.size(); i++) {
            JSONObject tag = (JSONObject) tagJsonArray.get(i);
            String tagName = (String) tag.get("name");
            tags.add(tagName);
        }

        return tags;
    }

    private JSONArray getTagsJsonArray(String userTagsJson) {
        System.out.println(userTagsJson);
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(userTagsJson);
            System.out.println(jsonObject.toString());
            JSONObject data = (JSONObject) jsonObject.get("data");
            System.out.println(data.toString());
            JSONObject userTags = (JSONObject) data.get("userTags");
            System.out.println(userTags.toString());
            JSONArray tagJsonArray = (JSONArray) userTags.get("tags");
            System.out.println(tagJsonArray.toJSONString());

            return tagJsonArray;
        } catch (ParseException exception) {
            throw new PostStatViewException(USER_TAGS_JSON_PARSING_FAILURE);
        }
    }

    // Velog GraphQL에 POST요청을 보내고, 응답 json을 String으로 리턴
    public String postWith(VelogOperationRequest request, String refreshToken) {
        // header에 JSON 타입의 Body임을 명시
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "refresh_token="+refreshToken);

        // Http 요청에 포함할 body, header를 포함
        HttpEntity<String> entity = new HttpEntity(request.toJson(), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(VELOG_GRAPHQL_URL, entity, String.class);
    }

    public String postWith(VelogOperationRequest request) {
        return postWith(request, "");
    }

    public List<String> postWith(List<VelogOperationRequest> batchRequests, String refreshToken) {
        return batchRequests.stream()
                .map(batchRequest -> postWith(batchRequest, refreshToken))
                .collect(Collectors.toList());
    }

    private JSONArray getPostJsonArray(String postIdJson)  {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(postIdJson);
            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONArray posts = (JSONArray) data.get("posts");

            return posts;
        } catch (ParseException exception) {
            throw new PostStatViewException(USER_POST_ID_JSON_PARSING_FAILURE);
        }
    }

    public List<VelogOperationRequest> creatBatchRequests(List<String> postIds) {
        List<VelogOperationRequest> batchRequests = new ArrayList<>();

        int fullBatchRequestsCount = postIds.size() / VELOG_GRAPHQL_BATCH_MAX_SIZE;

        for (int i = 0; i < fullBatchRequestsCount; i++) {
            batchRequests.add(new VelogGetStatsRequest(
                    postIds.subList(VELOG_GRAPHQL_BATCH_MAX_SIZE * i, VELOG_GRAPHQL_BATCH_MAX_SIZE * (i + 1))
            ));
        }

        if (postIds.size() % VELOG_GRAPHQL_BATCH_MAX_SIZE != 0) {
            batchRequests.add(new VelogGetStatsRequest(
                    postIds.subList(fullBatchRequestsCount * VELOG_GRAPHQL_BATCH_MAX_SIZE, postIds.size()
                    )));
        }

        return batchRequests;
    }

    public long getVisitors(JSONObject from) {
        JSONObject data = (JSONObject) from.get("data");
        JSONObject getStats = (JSONObject) data.get("getStats");
        return (long) getStats.get("total");
    }
}
