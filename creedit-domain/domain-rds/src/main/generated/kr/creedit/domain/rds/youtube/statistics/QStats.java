package kr.creedit.domain.rds.youtube.statistics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStats is a Querydsl query type for Stats
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStats extends BeanPath<Stats> {

    private static final long serialVersionUID = 1807938817L;

    public static final QStats stats1 = new QStats("stats1");

    public final ListPath<Statistics, QStatistics> stats = this.<Statistics, QStatistics>createList("stats", Statistics.class, QStatistics.class, PathInits.DIRECT2);

    public QStats(String variable) {
        super(Stats.class, forVariable(variable));
    }

    public QStats(Path<? extends Stats> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStats(PathMetadata metadata) {
        super(Stats.class, metadata);
    }

}

