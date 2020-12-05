package kr.creedit.client.youtube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"client-youtube", "test"})
@SpringBootTest
class YouTubeClientTest {

    @Autowired
    private YouTubeClient youTubeClient;

    @Value("${client.youtube.token}")
    private String token;

    @Disabled("API 호출로 인한 disabled")
    @DisplayName("채널 통계 정보 획득 테스트")
    @Test
    void getStatisticsForChannel_test() throws JsonProcessingException {
        // given
        System.out.println("token : " + token);
        String channelId = "UCqI5lyTpC79pOy2D-VXAMdA";

        // when
        ChannelStatisticsDto.Response response = youTubeClient.getStatisticsByChannel(channelId)
                .block();

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        // then
        assert response != null;
        assertThat(response.getStatisticsDto()).isInstanceOf(ChannelStatisticsDto.StatisticsDto.class);
    }

    @DisplayName("존재하지 않는 채널 통계 획득 요청 테스트")
    @Test
    void getStatisticsForChannel_fail_test() {
        // given
        String channelId = "no channel";

        // when
        ChannelStatisticsDto.Response response = youTubeClient.getStatisticsByChannel(channelId)
                .block();

        // then
        // TODO 에러처리 IndexOutOfBoundsException 이 IllegalArgument 보다 먼저 나오는듯?
        assert response != null;
        assertThrows(IndexOutOfBoundsException.class, () -> response.getStatisticsDto());
    }

}