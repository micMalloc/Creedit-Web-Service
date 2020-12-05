package kr.creedit.batch.job;

import kr.creedit.client.youtube.YouTubeClient;
import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
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

    @Value("${chunkSize:50}")
    private int chunkSize;

    @Bean
    public Job youtubeJob() {
        return jobBuilderFactory.get(YOUTUBE_JOB_NAME)
                .start(youtubeStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step youtubeStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get(YOUTUBE_PREFIX + "step")
                .<Channel, Statistics>chunk(chunkSize)
                .reader(youtubeReader())
                .processor(processor())
                .writer(jpaItemWriter())
                .faultTolerant()
                .retryLimit(3)
                .retry(IllegalArgumentException.class)
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
    public ItemProcessor<Channel, Statistics> processor() {
        return channel -> {
            Statistics statistics = youTubeClient.getStatisticsByChannel(channel.getChannelId()).block().toEntity(channel);
            log.info(">>>>>>> channel channelId : {}, channelName : {}", channel.getChannelId(), channel.getChannelName());
            log.info(">>>>>>> statistics : {}", statistics);
            return statistics;
        };
    }

    @Bean
    public JpaItemWriter<Statistics> jpaItemWriter() {
        JpaItemWriter<Statistics> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
