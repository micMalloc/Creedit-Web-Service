package kr.creedit.domain.rds.youtube.statistics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStatistics is a Querydsl query type for Statistics
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStatistics extends EntityPathBase<Statistics> {

    private static final long serialVersionUID = -1476563455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStatistics statistics = new QStatistics("statistics");

    public final kr.creedit.domain.rds.QBaseTimeEntity _super = new kr.creedit.domain.rds.QBaseTimeEntity(this);

    public final kr.creedit.domain.rds.youtube.channel.QChannel channel;

    public final NumberPath<Long> commentCount = createNumber("commentCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final BooleanPath hiddenSubscriberCount = createBoolean("hiddenSubscriberCount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> subscriberCount = createNumber("subscriberCount", Long.class);

    public final NumberPath<Long> videoCount = createNumber("videoCount", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QStatistics(String variable) {
        this(Statistics.class, forVariable(variable), INITS);
    }

    public QStatistics(Path<? extends Statistics> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStatistics(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStatistics(PathMetadata metadata, PathInits inits) {
        this(Statistics.class, metadata, inits);
    }

    public QStatistics(Class<? extends Statistics> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new kr.creedit.domain.rds.youtube.channel.QChannel(forProperty("channel"), inits.get("channel")) : null;
    }

}

