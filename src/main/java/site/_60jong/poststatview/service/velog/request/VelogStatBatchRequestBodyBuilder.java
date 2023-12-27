package site._60jong.poststatview.service.velog.request;

import java.util.ArrayList;
import java.util.List;

public class VelogStatBatchRequestBodyBuilder {

    private static final int BATCH_SIZE = 32;

    public static List<List<VelogStatRequestBody>> createBatchBodies(List<VelogStatRequestBody> bodies) {

        int batchBodyCount = bodies.size() / BATCH_SIZE;

        List<List<VelogStatRequestBody>> batchBodies = new ArrayList<>(batchBodyCount);

        for (int batchSeq = 0; batchSeq < batchBodyCount; batchSeq++) {

            List<VelogStatRequestBody> subRequests = bodies.subList(BATCH_SIZE * batchSeq, BATCH_SIZE * (batchSeq + 1));
            batchBodies.add(subRequests);
        }

        if ((bodies.size() % BATCH_SIZE) != 0) {

            List<VelogStatRequestBody> lastSubRequests = bodies.subList(BATCH_SIZE * batchBodyCount, bodies.size());
            batchBodies.add(lastSubRequests);
        }

        return batchBodies;
    }
}
