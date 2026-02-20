package site._60jong.poststatview.service.velog.response.getstats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class GetStatsResponses {

    private List<GetStatsResponse> responses;

    public int getTotalVisitors() {
        return responses.stream()
                .map(GetStatsResponse::getGetStats)
                .filter(Objects::nonNull)
                .mapToInt(PostStats::getTotal)
                .sum();
    }
}
