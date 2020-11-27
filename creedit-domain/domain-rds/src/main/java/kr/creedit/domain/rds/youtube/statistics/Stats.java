package kr.creedit.domain.rds.youtube.statistics;

import kr.creedit.domain.rds.youtube.channel.Channel;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of Statistics
 */
@Embeddable
public class Stats {

    @OneToMany(mappedBy = "channel")
    @OrderBy("id DESC")
    private final List<Statistics> stats;

    public Stats() {
        stats = new ArrayList<>();
    }

    public Stats(List<Statistics> stats) {
        this.stats = stats;
    }

    public void addStatistics(Statistics statistics) {
        stats.add(statistics);
    }
}
