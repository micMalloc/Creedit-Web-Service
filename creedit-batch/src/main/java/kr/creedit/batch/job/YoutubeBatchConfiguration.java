package kr.creedit.batch.job;

import kr.creedit.client.youtube.YouTubeClient;
import kr.creedit.client.youtube.dto.ChannelStatisticsDto;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class YoutubeBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final YouTubeClient youTubeClient;

    private static final String YOUTUBE_JOB_NAME = "youtubeJob";
    private static final String YOUTUBE_PREFIX = YOUTUBE_JOB_NAME + "_";

    /**
     * Query
     */
    private static final String SELECT_ALL_CHANNELS = "SELECT c FROM Channel c";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Job youtubeJob() {
        return jobBuilderFactory.get(YOUTUBE_JOB_NAME)
                .start(youtubeStep())
                .build();
    }

    @Bean
    public Step youtubeStep() {
        return stepBuilderFactory.get(YOUTUBE_PREFIX + "step")
                .<Channel, ChannelStatisticsDto.Response>chunk(chunkSize)
                .reader(youtubeReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Channel> youtubeReader() {
        return new JpaPagingItemReaderBuilder<Channel>()
                .name(YOUTUBE_PREFIX + "reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString(SELECT_ALL_CHANNELS)
                .build();
    }

    @Bean
    public ItemProcessor<Channel, ChannelStatisticsDto.Response> processor() {
        return channel -> {
            log.info(">>>>>>> channel id : {}", channel.getChannelId());
            return youTubeClient.getStatisticsByChannel(channel.getChannelId()).block();
        };
    }

    private ItemWriter<ChannelStatisticsDto.Response> writer() {
        return items -> {
            for(ChannelStatisticsDto.Response item : items) {
                log.info(">>>>>>> Channel subscriberCount : {}", item.getItems().get(0).getStatistics().getSubscriberCount());
                log.info(">>>>>>> Channel videoCount : {}", item.getItems().get(0).getStatistics().getVideoCount());
                log.info(">>>>>>> Channel viewCount : {}", item.getItems().get(0).getStatistics().getViewCount());
                log.info(">>>>>>> Channel hiddenSubscriberCount : {}", item.getItems().get(0).getStatistics().isHiddenSubscriberCount());
            }
        };
    }
}
