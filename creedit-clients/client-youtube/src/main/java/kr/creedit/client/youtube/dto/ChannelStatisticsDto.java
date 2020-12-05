package kr.creedit.client.youtube.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.creedit.client.youtube.mapper.ChannelStaticsDtoMapper;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelStatisticsDto {

    public static final ChannelStatisticsDto.Response EMPTY_RESPONSE = new ChannelStatisticsDto.Response();

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private List<Item> items = new ArrayList<>();

        @JsonIgnore
        public StatisticsDto getStatisticsDto() {
            return Optional.ofNullable(items)
                    .map(item -> item.get(0))
                    .map(Item::getStatistics)
                    .orElseThrow(IllegalArgumentException::new);
        }

        @JsonIgnore
        public Statistics toEntity(Channel channel) {
            Statistics statistics = ChannelStaticsDtoMapper.INSTANCE.toEntity(getStatisticsDto());
            statistics.setChannel(channel);
            statistics.setCommentCountZero();

            return statistics;
        }
    }



    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Item {
        @JsonProperty("statistics")
        private StatisticsDto statistics;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StatisticsDto {
        private Long viewCount;
        private Long subscriberCount;
        private boolean hiddenSubscriberCount;
        private Long videoCount;
    }
}
