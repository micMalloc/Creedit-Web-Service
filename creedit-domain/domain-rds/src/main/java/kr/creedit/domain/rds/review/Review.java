package kr.creedit.domain.rds.review;

import kr.creedit.domain.rds.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Review 에 대한 엔터티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, length = 128)
    private String comment;

    @Column(nullable = false)
    private int rate; // 평점

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false)
    private Long categoryId;

    @Builder
    public Review(String comment, int rate, String author, Long categoryId) {
        this.comment = comment;
        this.rate = rate;
        this.author = author;
        this.categoryId = categoryId;
    }

    public void editComment(String comment) {
        this.comment = StringUtils.isEmpty(comment) ? this.comment : comment;
    }
}
