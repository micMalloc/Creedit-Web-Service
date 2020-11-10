package kr.creedit.domain.rds.youtube.channel;

import kr.creedit.domain.rds.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"domain-rds", "local"})
@DataJpaTest
class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    @DisplayName("채널 저장 테스트")
    @Test
    void save_test() {
        // given
        String channelId = "SomeChannel";
        Category category = Category.GAME;

        Channel channel = Channel.builder()
                .channelId(channelId)
                .category(category)
                .build();

        // when
        Channel saved = channelRepository.save(channel);

        // then
        assertThat(channelId).isEqualTo(saved.getChannelId());
        assertThat(category).isEqualTo(saved.getCategory());
    }

    @Test
    void find_by_channelId_test() throws IllegalAccessException {
        // given
        String channelId = "SomeChannel";
        Category category = Category.GAME;

        Channel channel = Channel.builder()
                .channelId(channelId)
                .category(category)
                .build();
        channelRepository.save(channel);

        // when
        Channel saved = channelRepository.findByChannelId(channelId)
                .orElseThrow(IllegalAccessException::new);

        // then
        assertThat(channelId).isEqualTo(saved.getChannelId());
        assertThat(category).isEqualTo(saved.getCategory());
    }
}