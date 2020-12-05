package kr.creedit.batch.job;

import kr.creedit.TestBatchConfig;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ComponentScan(value = {"kr.creedit.client.youtube", "kr.creedit.domain.rds.youtube"})
@EnableWebFlux
@SpringBatchTest
@SpringBootTest(classes = {YoutubeBatchConfiguration.class, TestBatchConfig.class})
@ActiveProfiles({"client-youtube", "domain-rds", "batch"})
class YoutubeBatchConfigurationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void tearDown() {
        statisticsRepository.deleteAll();
        channelRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Disabled("매번 Job Parameter 를 변경해야 성공하므로 Disable")
    @DisplayName("Youtube Batch 테스트")
    @Test
    void batch_test() throws Exception {
        // given
        String initChannelName = "initChannelName";

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

        LocalDate requestDate = LocalDate.of(2019, 01, 01);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.format(FORMATTER))
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertEquals(1, statisticsList.size());
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
    }


}