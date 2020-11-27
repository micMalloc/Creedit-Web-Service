package kr.creedit.domain.rds.youtube.statistics;

import kr.creedit.domain.rds.youtube.category.Category;
import kr.creedit.domain.rds.youtube.category.CategoryRepository;
import kr.creedit.domain.rds.youtube.category.CategoryType;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.ChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"domain-rds", "local"})
@DataJpaTest
class StatisticsRepositoryTest {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChannelRepository channelRepository;

    private Channel channel;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .type(CategoryType.valueOf("GAME"))
                .build();
        categoryRepository.save(category);

        channel = Channel.builder()
                .category(category)
                .channelId("UC1qyRC_RczgsNs3UaDZ9hIA")
                .channelName("크로스핏")
                .build();
        channelRepository.save(channel);

        saveStatistics(channel);
    }

    @AfterEach
    void tearDown() {
        statisticsRepository.deleteAll();
        channelRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @DisplayName("7일간 통계 정보 획득 테스트")
    @Test
    void findStatisticsForWeek_test() {
        // given
        String channelId = channel.getChannelId();

        // when
        List<Statistics> stats = statisticsRepository.findAll();

        // then
        assertThat(stats.size()).isEqualTo(10);
    }

    private void saveStatistics(Channel channel) {
        IntStream.range(0, 10)
                .asLongStream()
                .forEach(idx -> statisticsRepository.save(makeStatistics(idx, channel)));
    }

    private Statistics makeStatistics(Long idx, Channel channel) {
        return Statistics.builder()
                .channel(channel)
                .commentCount(idx)
                .hiddenSubscriberCount(false)
                .subscriberCount(idx)
                .videoCount(idx)
                .viewCount(idx)
                .build();
    }
}