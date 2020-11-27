package kr.creedit.domain.rds.youtube.category;

import kr.creedit.domain.rds.youtube.channel.Channel;
import kr.creedit.domain.rds.youtube.channel.Channels;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Category Entity
 */
@Getter
@NoArgsConstructor
@Entity
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType type;

    private final Channels channels = new Channels();

    @Builder
    public Category(CategoryType type) {
        this.type = type;
    }

    void addChannel(Channel channel) {
        channel.setCategory(this);
        channels.add(channel);
    }
}
