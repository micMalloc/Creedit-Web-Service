package kr.creedit.client.youtube;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("client.youtube")
public class YouTubeProperties {

    String host;
    int timeout;
    String token;
}
