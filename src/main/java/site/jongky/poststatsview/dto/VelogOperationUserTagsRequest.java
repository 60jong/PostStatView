package site.jongky.poststatsview.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VelogOperationUserTagsRequest {
    private String username;

    @Override
    public String toString() {
        return String.format(
                "{\n" +
                "    \"operationName\" : \"UserTags\",\n" +
                "    \"variables\" : {\n" +
                "        \"username\" : \"%s\"\n" +
                "    },\n" +
                "    \"query\" : \"query UserTags($username: String) {\\n  userTags(username: $username) {\\n    tags {\\n      id\\n      name\\n      description\\n      posts_count\\n      thumbnail\\n      __typename\\n    }\\n    posts_count\\n    __typename\\n  }\\n}\"\n" +
                "}", username);
    }
}
