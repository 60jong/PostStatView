package site._60jong.poststatview.service.velog.response.getstats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetStatsResponses {

    private List<GetStatsResponse> responses;

    public int getTotalVisitors() {
        int totalVisitors = 0;

        for (GetStatsResponse resp : this.responses) {
            PostStats stats = resp.getGetStats();
            if (stats  == null) {
                break;
            }
            totalVisitors += stats.getTotal();
        }
        return totalVisitors;
    }
}
