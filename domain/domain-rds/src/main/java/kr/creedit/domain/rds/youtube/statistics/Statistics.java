package kr.creedit.domain.rds.youtube.statistics;

import kr.creedit.domain.rds.BaseTimeEntity;
import kr.creedit.domain.rds.youtube.channel.Channel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Statistics extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    @Id
    private Long id;

    @Column
    private Long viewCount;

    @Column
    private Long subscriberCount;

    @Column
    private Long commentCount;

    @Column
    private boolean hiddenSubscriberCount;

    @Column
    private Long videoCount;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
