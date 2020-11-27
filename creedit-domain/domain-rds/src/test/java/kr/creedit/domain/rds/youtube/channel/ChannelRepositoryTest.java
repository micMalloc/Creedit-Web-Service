package kr.creedit.domain.rds.youtube.channel;

import kr.creedit.domain.rds.youtube.category.Category;
import kr.creedit.domain.rds.youtube.category.CategoryType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .type(CategoryType.ENTERTAINMENT)
                .build();
    }

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
    }

    @DisplayName("채널 저장 테스트")
    @Test
    void save_test() {
        // given
        String channelId = "channel id";
        String channelName = "channel name";

        Channel channel = Channel.builder()
                .channelId(channelId)
                .channelName(channelName)
                .build();

        // when
        Channel saved = channelRepository.save(channel);

        // then
        assertThat(saved.getChannelId()).isEqualTo(channelId);
        assertThat(saved.getChannelName()).isEqualTo(channelName);
    }

    @Test
    void find_by_channelId_test() throws IllegalAccessException {
        // given

        // when

        // then
    }
}