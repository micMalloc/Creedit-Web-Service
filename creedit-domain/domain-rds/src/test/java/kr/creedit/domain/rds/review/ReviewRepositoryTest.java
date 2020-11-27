package kr.creedit.domain.rds.review;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"domain-rds", "local"})
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
    }

    @DisplayName("리뷰 등록 테스트")
    @Test
    void save_test() {
        // given
        Review review = Review.builder()
                .author("Heesu")
                .categoryId(1L)
                .comment("Hello World")
                .rate(5)
                .build();

        // when
        Review saved = reviewRepository.save(review);

        // then
        assertEquals(review, saved);
        assertEquals(review.getAuthor(), saved.getAuthor());
        assertEquals(review.getCategoryId(), saved.getCategoryId());
        assertEquals(review.getComment(), saved.getComment());
        assertEquals(review.getCreateAt(), saved.getCreateAt());
        assertEquals(review.getCreateAt(), saved.getModifiedAt());
    }

    @DisplayName("리뷰 조회 테스트 - ID")
    @Test
    void findById_test() {
        // given
        Review review = Review.builder()
                .author("Heesu")
                .categoryId(1L)
                .comment("Hello World")
                .rate(5)
                .build();

        // when
        Long id = reviewRepository.save(review)
                .getId();

        // then
        Review saved = reviewRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        assertEquals(review, saved);
        assertEquals(review.getAuthor(), saved.getAuthor());
        assertEquals(review.getCategoryId(), saved.getCategoryId());
        assertEquals(review.getComment(), saved.getComment());
        assertEquals(review.getCreateAt(), saved.getCreateAt());
        assertEquals(review.getModifiedAt(), saved.getModifiedAt());
    }

    @DisplayName("리뷰 댓글 수정 테스트")
    @Test
    void editComment_test() {
        // given
        Review review = Review.builder()
                .author("Heesu")
                .categoryId(1L)
                .comment("Hello World")
                .rate(5)
                .build();
        Long id = reviewRepository.save(review)
                .getId();

        String newComment = "GoodBye Hello";

        // when
        Review saved = reviewRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        saved.editComment(newComment);

        // then
        assertEquals(newComment, saved.getComment());
    }

    @DisplayName("동일 카테고리 리뷰 목록 전체 조회")
    @Test
    void findByCategoryId_test() {
        // given
        int size = 5;
        Long categoryId = 1L;

        // when
        saveMultipleReview(size);

        // then
        List<Review> reviews = reviewRepository.findByCategoryId(categoryId);

        assertEquals(size, reviews.size());
    }

    private void saveMultipleReview(int size) {
        IntStream.rangeClosed(1, size).forEach(idx -> {
                Review review = Review.builder()
                .author("Heesu" + idx)
                .categoryId(1L)
                .comment("Hello World " + idx)
                .rate(5)
                .build();
                reviewRepository.save(review);
        });
    }
}