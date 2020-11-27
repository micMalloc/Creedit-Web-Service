package kr.creedit.client.youtube;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(YouTubeProperties.class)
public class YouTubeConfig {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeClient youTubeClient(YouTubeProperties youTubeProperties, WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, youTubeProperties.timeout)
                                .doOnConnected(connection -> connection
                                        .addHandlerLast(new ReadTimeoutHandler(youTubeProperties.timeout, TimeUnit.MILLISECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(youTubeProperties.timeout, TimeUnit.MILLISECONDS))));
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        WebClient webClient = builder
                .baseUrl(youTubeProperties.getHost())
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return new YouTubeClient(webClient, youTubeProperties.token);
    }
}
