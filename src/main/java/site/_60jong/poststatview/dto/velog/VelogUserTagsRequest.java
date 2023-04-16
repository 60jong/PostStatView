package site._60jong.poststatview.dto.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import site._60jong.poststatview.exception.PostStatViewException;

import java.util.Map;

import static site._60jong.poststatview.exception.PostStatViewResponseStatus.GRAPHQL_JSON_PARSING_FAILURE;

@AllArgsConstructor
public class VelogUserTagsRequest implements VelogOperationRequest{
    private String username;
    private ObjectMapper objectMapper;

    private final String VELOG_USER_TAGS_GRAPHQL = "query UserTags($username: String) { userTags(username: $username) { tags { name posts_count }}}";

    public VelogUserTagsRequest(String username) {
        this.username = username;
        this.objectMapper = new ObjectMapper();
    }

    public String toJson() {
        ObjectNode node = objectMapper.createObjectNode();
        node.set("variables", objectMapper.valueToTree(Map.of("username", username)));
        node.put("query", VELOG_USER_TAGS_GRAPHQL);

        try {
            return objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException exception) {
            throw new PostStatViewException(GRAPHQL_JSON_PARSING_FAILURE);
        }
    }
}
