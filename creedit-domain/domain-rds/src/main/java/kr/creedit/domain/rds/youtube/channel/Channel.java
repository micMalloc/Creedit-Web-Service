package kr.creedit.domain.rds.youtube.channel;

import kr.creedit.domain.rds.youtube.category.Category;
import kr.creedit.domain.rds.youtube.statistics.Statistics;
import kr.creedit.domain.rds.youtube.statistics.Stats;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Youtube Channel 정보 엔터티
 */
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

    @Column(name = "cname", nullable = false, length = 128)
    private String channelName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    private final Stats stats = new Stats();

    @Builder
    public Channel(String channelId, String channelName, Category category) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.category = category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addStatistics(Statistics stat) {
        stat.setChannel(this);
        stats.addStatistics(stat);
    }
}
