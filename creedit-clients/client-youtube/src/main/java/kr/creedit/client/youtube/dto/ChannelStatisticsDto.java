package kr.creedit.client.youtube.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelStatisticsDto {

    public static final ChannelStatisticsDto EMPTY = new ChannelStatisticsDto();

    private List<Item> items;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Item {
        private StatisticsDto statistics;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class StatisticsDto {
        private Long viewCount;
        private Long subscriberCount;
        private boolean hiddenSubscriberCount;
        private Long videoCount;
    }
}
