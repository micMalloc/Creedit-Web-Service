package kr.creedit.client.youtube.dto;

import kr.creedit.domain.rds.youtube.statistics.Statistics;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private List<Item> items = new ArrayList<>();

        public Statistics getStatistics() {
            return Optional.ofNullable(items)
                    .map(item -> item.get(0))
                    .map(Item::toEntity)
                    .orElseThrow(IllegalArgumentException::new);
        }
    }



    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Item {
        private StatisticsDto statistics;

        private Statistics toEntity() {
            return statistics.toEntity();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public static class StatisticsDto {
        private Long viewCount;
        private Long subscriberCount;
        private boolean hiddenSubscriberCount;
        private Long videoCount;

        private Statistics toEntity() {
            return Statistics.builder()
                    .viewCount(viewCount)
                    .videoCount(videoCount)
                    .hiddenSubscriberCount(hiddenSubscriberCount)
                    .subscriberCount(subscriberCount)
                    .build();
        }
    }
}
