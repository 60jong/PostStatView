package site.jongky.poststatview.dto.velog;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VelogGetPostIdsRequest implements VelogOperationRequest {
    private String username;

    public String toJson() {
        return String.format(
                "{\n" +
                "    \"operationName\" : \"Posts\",\n" +
                "    \"variables\" : {\n" +
                "        \"username\" : \"%s\"\n" +
                "    },\n" +
                "    \"query\" : \"query Posts($username: String) {\\n  posts(username: $username, limit: 0) {\\n    id\\n  }\\n}\"" +
                "}"
                , username);
    }
}
