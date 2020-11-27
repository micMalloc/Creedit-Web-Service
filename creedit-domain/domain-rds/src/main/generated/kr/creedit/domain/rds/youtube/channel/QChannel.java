package kr.creedit.domain.rds.youtube.channel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannel is a Querydsl query type for Channel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChannel extends EntityPathBase<Channel> {

    private static final long serialVersionUID = -1688735669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChannel channel = new QChannel("channel");

    public final kr.creedit.domain.rds.youtube.category.QCategory category;

    public final StringPath channelId = createString("channelId");

    public final StringPath channelName = createString("channelName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.creedit.domain.rds.youtube.statistics.QStats stats;

    public QChannel(String variable) {
        this(Channel.class, forVariable(variable), INITS);
    }

    public QChannel(Path<? extends Channel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChannel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChannel(PathMetadata metadata, PathInits inits) {
        this(Channel.class, metadata, inits);
    }

    public QChannel(Class<? extends Channel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new kr.creedit.domain.rds.youtube.category.QCategory(forProperty("category"), inits.get("category")) : null;
        this.stats = inits.isInitialized("stats") ? new kr.creedit.domain.rds.youtube.statistics.QStats(forProperty("stats")) : null;
    }

}

