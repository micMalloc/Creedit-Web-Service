package kr.creedit.client.youtube;

import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"client-youtube", "test"})
@SpringBootTest
class YouTubeClientTest {

    @Autowired
    private YouTubeClient youTubeClient;

    @Test
    void getStatisticsForChannel_test() {
        // given
        String channelId = "UCqI5lyTpC79pOy2D-VXAMdA";

        // when
        ChannelStatisticsDto response = youTubeClient.getStatisticsByChannel(channelId)
                .block();

        // then
        System.out.println(response);
    }
}