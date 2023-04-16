package site._60jong.poststatview.exception;

import lombok.Getter;

@Getter
public enum PostStatViewResponseStatus {
    GRAPHQL_BATCH_JSON_PARSING_FAILURE(1001, "GraphQL Batch 파일을 Json 변환에 실패하였습니다."),
    POST_VISITORS_JSON_PARSING_FAILURE(1002, "게시글 방문자 Json 파일을 변환하는 데에 실패하였습니다."),
    USER_POST_ID_JSON_PARSING_FAILURE(1003, "유저의 게시글 ID Json 파일을 변환하는 데에 실패하였습니다."),
    USER_TAGS_JSON_PARSING_FAILURE(1004, "유저의 게시글 태그 Json 파일을 변환하는 데에 실패하였습니다."),
    REFRESH_TOKEN_REGISTER_SUCCESS(1005, "refresh_token 저장에 성공하였습니다."),
    VIEW_BACKGROUND_NOT_FOUND(1006, "View 배경 이미지를 찾을 수 없습니다."),
    VIEW_MADE_IMAGE_LOAD_FAILURE(1007, "유저의 View 이미지를 불러올 수 없습니다."),
    GRAPHQL_JSON_PARSING_FAILURE(1008, "GraphQL 파일을 Json 변환에 실패하였습니다."),
    NO_SUCH_USER(1009, "refresh_token이 등록되지 않는 유저입니다."),
    INVALID_REFRESH_TOKEN(1010, "유효하지 않은 refresh_token입니다.");

    private int code;
    private String message;

    PostStatViewResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
