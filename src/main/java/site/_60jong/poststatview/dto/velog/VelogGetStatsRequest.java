package site._60jong.poststatview.dto.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import site._60jong.poststatview.exception.PostStatViewException;

import java.util.List;
import java.util.Map;

import static site._60jong.poststatview.exception.PostStatViewResponseStatus.*;

public class VelogGetStatsRequest implements VelogOperationRequest {
    private List<String> postIds;
    private ObjectMapper objectMapper;

    private final String VELOG_GET_STATS_GRAPHQL = "query GetStats($postId: ID!) {getStats(post_id: $postId) {total}}";

    public VelogGetStatsRequest(List<String> postIds) {
        this.postIds = postIds;
        this.objectMapper = new ObjectMapper();
    }

    public String toJson() {
        ArrayNode batch = objectMapper.createArrayNode();

        for (String postId : postIds) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("query", VELOG_GET_STATS_GRAPHQL);
            node.set("variables", objectMapper.valueToTree(Map.of("postId", postId)));
            batch.add(node);
        }

        try {
            return objectMapper.writeValueAsString(batch);
        } catch (JsonProcessingException exception) {
            throw new PostStatViewException(GRAPHQL_BATCH_JSON_PARSING_FAILURE);
        }
    }
}
