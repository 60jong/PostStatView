package site._60jong.poststatview.service.velog.response.getstats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetStatsResponses {

    private List<GetStatsResponse> responses;

    public int getTotalVisitors() {

        return responses.stream()
                        .mapToInt(res -> res.getGetStats().getTotal())
                        .sum();
    }
}
