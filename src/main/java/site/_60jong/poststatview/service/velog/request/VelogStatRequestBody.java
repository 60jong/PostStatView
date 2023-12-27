package site._60jong.poststatview.service;

import java.util.HashMap;
import java.util.Map;

public class VelogStatRequestBody {

    private String operationName;
    private Map<String, Object> variables;
    private String query;

    public VelogStatRequestBody() {
        this.variables = new HashMap<>();
    }

    public static VelogStatRequestBodyBuilder builder() {
        return new VelogStatRequestBodyBuilder(new VelogStatRequestBody());
    }

    //== Builder ==//
    public static class VelogStatRequestBodyBuilder {

        private final VelogStatRequestBody body;

        public VelogStatRequestBodyBuilder(VelogStatRequestBody body) {
            this.body = body;
        }

        public VelogStatRequestBodyBuilder operationName(String operationName) {

            body.operationName = operationName;

            return this;
        }

        public VelogStatRequestBodyBuilder variable(String key, Object value) {

            body.variables.put(key, value);

            return this;
        }

        public VelogStatRequestBodyBuilder query(String query) {

            body.query = query;

            return this;
        }

        public VelogStatRequestBody build() {
            return body;
        }
    }
}
