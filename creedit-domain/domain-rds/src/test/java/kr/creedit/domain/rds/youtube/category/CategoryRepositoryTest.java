package kr.creedit.domain.rds.youtube.category;

import kr.creedit.domain.rds.youtube.channel.Channel;
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
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category givenCategory;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .type(CategoryType.valueOf("GAME"))
                .build();
        givenCategory = categoryRepository.save(category);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @DisplayName("카테고리 저장 테스트")
    @Test
    void save_test() {
        // given
        Category category = Category.builder()
                .type(CategoryType.valueOf("GAME"))
                .build();

        // when
        Category saved = categoryRepository.save(category);

        // then
        assertThat(saved).isEqualTo(category);
        assertThat(saved.getType()).isEqualTo(CategoryType.GAME);
    }

    @DisplayName("카테고리에 속한 채널 추가 테스트")
    @Test
    void addChannel_test() {
        // given
        String channelId = "sample channel id";
        String channelName = "sample name";

        Channel channel = Channel.builder()
                .channelId(channelId)
                .channelName(channelName)
                .build();

        // when
        givenCategory.addChannel(channel);

        // then
        assertThat(givenCategory.getChannels().size()).isEqualTo(1);
    }
}