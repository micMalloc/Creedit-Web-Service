package kr.creedit.batch.job.retry;

import kr.creedit.TestBatchConfig;
import kr.creedit.batch.job.YoutubeBatchConfiguration;
import kr.creedit.client.youtube.YouTubeClient;
import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import kr.creedit.domain.rds.youtube.category.Category;
import kr.creedit.domain.rds.youtube.category.CategoryRepository;
import kr.creedit.domain.rds.youtube.category.CategoryType;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.ChannelRepository;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import kr.creedit.domain.rds.youtube.statistics.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ComponentScan(value = {"kr.creedit.client.youtube", "kr.creedit.domain.rds.youtube"})
@EnableWebFlux
@SpringBatchTest
@SpringBootTest(classes = {YoutubeBatchConfiguration.class, TestBatchConfig.class})
@ActiveProfiles({"client-youtube", "domain-rds", "batch"})
public class YoutubeBatchConfigurationRetryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean
    private YouTubeClient youTubeClient;

    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void tearDown() {
        statisticsRepository.deleteAll();
        channelRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Disabled("매번 Job Parameter 를 변경해야 성공하므로 Disable")
    @DisplayName("실패한 경우 에러를 던지고, 재시도를 하도록 하는 테스트")
    @Test
    void retry_batch_test() throws Exception {
        // given
        String initChannelName = "initChannelName";

        ChannelStatisticsDto.StatisticsDto statisticsDto = ChannelStatisticsDto.StatisticsDto.builder()
                .hiddenSubscriberCount(false)
                .subscriberCount(1L)
                .videoCount(1L)
                .viewCount(1L)
                .build();

        ChannelStatisticsDto.Item item = ChannelStatisticsDto.Item.builder().statistics(statisticsDto).build();
        ChannelStatisticsDto.Response response = ChannelStatisticsDto.Response.builder().items(new ArrayList<>(Arrays.asList(item))).build();

        Category category = categoryRepository.save(
                Category.builder()
                        .type(CategoryType.GAME)
                        .build()
        );

        Channel channel = channelRepository.save(
                Channel.builder()
                        .channelId("UCqI5lyTpC79pOy2D-VXAMdA")
                        .channelName(initChannelName)
                        .category(category)
                        .build()
        );

        LocalDate requestDate = LocalDate.of(2020, 01, 11);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.format(FORMATTER))
                .toJobParameters();

        when(youTubeClient.getStatisticsByChannel(any()))
                .thenThrow(new IllegalArgumentException())
                .thenThrow(new IllegalArgumentException())
                .thenReturn(Mono.just(response));

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertEquals(1, statisticsList.size());
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
    }
}
