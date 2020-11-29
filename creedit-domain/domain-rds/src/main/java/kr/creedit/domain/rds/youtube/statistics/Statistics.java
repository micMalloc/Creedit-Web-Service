package kr.creedit.domain.rds.youtube.statistics;

import kr.creedit.domain.rds.BaseTimeEntity;
import kr.creedit.domain.rds.youtube.channel.Channel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Youtube Channel 통계 정보 엔터티
 */
@Getter
@NoArgsConstructor
@Entity
public class Statistics extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    @Id
    private Long id;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Long subscriberCount;

    @Column(nullable = true)
    private Long commentCount;

    @Column(nullable = false)
    private boolean hiddenSubscriberCount;

    @Column(nullable = false)
    private Long videoCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Builder
    public Statistics(Long viewCount, Long subscriberCount, Long commentCount, boolean hiddenSubscriberCount, Long videoCount, Channel channel) {
        this.viewCount = viewCount;
        this.subscriberCount = subscriberCount;
        this.commentCount = commentCount;
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        this.videoCount = videoCount;
        this.channel = channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
