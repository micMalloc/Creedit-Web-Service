package kr.creedit.client.youtube;

import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static kr.creedit.client.youtube.constant.YouTubeConstant.CHANNEL;
import static kr.creedit.client.youtube.constant.YouTubeConstant.STATISTICS;

@Slf4j
@RequiredArgsConstructor
public class YouTubeClient {

    private final WebClient webClient;
    private final String token;

    public Mono<ChannelStatisticsDto.Response> getStatisticsByChannel(String channelId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(CHANNEL)
                        .queryParam("part", STATISTICS)
                        .queryParam("id", channelId)
                        .queryParam("key", token)
                        .build())
                .retrieve()
                .bodyToMono(ChannelStatisticsDto.Response.class)
                .doOnError(error -> {
                    log.error("[CUSTOM CLIENT ERROR] YouTube Data API 호출하는 과정에서 에러가 발생하였습니다.", error);
                    throw new IllegalArgumentException("error");
                })
                .onErrorReturn(ChannelStatisticsDto.EMPTY_RESPONSE);
    }
}
