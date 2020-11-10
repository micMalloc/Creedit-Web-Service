package kr.creedit.domain.rds.youtube.channel;

import kr.creedit.domain.rds.enums.Category;
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

@Getter
@NoArgsConstructor
@Entity
public class Channel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    @Id
    private Long id;

    @Column(name = "cid", nullable = false, unique = true, columnDefinition = "char(50)")
    private String channelId;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Category category; // 여러 개일 수 있음

    @Builder
    public Channel(String channelId, Category category) {
        this.channelId = channelId;
        this.category = category;
    }
}
