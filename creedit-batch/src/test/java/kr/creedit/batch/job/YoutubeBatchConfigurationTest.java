package kr.creedit.batch.job;

import kr.creedit.TestBatchConfig;
import kr.creedit.domain.rds.youtube.category.Category;
import kr.creedit.domain.rds.youtube.category.CategoryRepository;
import kr.creedit.domain.rds.youtube.category.CategoryType;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.ChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.config.EnableWebFlux;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ComponentScan(value = {"kr.creedit.client.youtube", "kr.creedit.domain.rds.youtube"})
@EnableWebFlux
@SpringBatchTest
@SpringBootTest(classes = {YoutubeBatchConfiguration.class, TestBatchConfig.class})
@ActiveProfiles({"client-youtube", "test"})
class YoutubeBatchConfigurationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void test() throws Exception {
        // given
        Category category = categoryRepository.save(
                Category.builder()
                        .type(CategoryType.GAME)
                        .build()
        );
        Channel channel = channelRepository.save(
                Channel.builder()
                        .channelId("UCqI5lyTpC79pOy2D-VXAMdA")
                        .channelName("이석준테스트")
                        .category(category)
                        .build()
        );

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
    }
}