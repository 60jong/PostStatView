package site._60jong.poststatview.service.velog.request;

import java.util.List;
import java.util.stream.IntStream;

public class VelogStatBatchRequestBodyBuilder {

    private static final int BATCH_SIZE = 32;

    public static List<List<VelogStatRequestBody>> createBatchBodies(List<VelogStatRequestBody> bodies) {
        int totalBatches = (bodies.size() + BATCH_SIZE - 1) / BATCH_SIZE;

        return IntStream.range(0, totalBatches)
                .mapToObj(i -> bodies.subList(
                        i * BATCH_SIZE,
                        Math.min((i + 1) * BATCH_SIZE, bodies.size())))
                .toList();
    }
}
