package kr.creedit.client.youtube;

import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles({"client-youtube", "test"})
@SpringBootTest
class YouTubeClientTest {

    @Autowired
    private YouTubeClient youTubeClient;

    @DisplayName("채널 통계 정보 획득 테스트")
    @Test
    void getStatisticsForChannel_test() {
        // given
        String channelId = "UCqI5lyTpC79pOy2D-VXAMdA";

        // when
        ChannelStatisticsDto response = youTubeClient.getStatisticsByChannel(channelId)
                .block();

        // then
        assert response != null;
        assertThat(response.getStatistics()).isInstanceOf(Statistics.class);
    }

    @DisplayName("존재하지 않는 채널 통계 획득 요청 테스트")
    @Test
    void getStatisticsForChannel_fail_test() {
        // given
        String channelId = "no channel";

        // when
        ChannelStatisticsDto response = youTubeClient.getStatisticsByChannel(channelId)
                .block();

        // then
        assert response != null;
        assertThatThrownBy(response::getStatistics)
                .isInstanceOf(IllegalArgumentException.class);
    }
}