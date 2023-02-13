package site.jongky.poststatsview.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VelogGetPostStatsRequest implements VelogOperationRequest {
    private String postId;

    public String toJson() {
        return String.format(
                "{\n" +
                        "    \"operationName\" : \"GetStats\",\n" +
                        "    \"variables\" : {\n" +
                        "        \"postId\" : \"%s\"\n" +
                        "    },\n" +
                        "    \"query\" : \"query GetStats($postId: ID!) {\\n  getStats(post_id: $postId) {\\n    total\\n  }\\n}\"" +
                        "}"
                , postId);
    }
}
