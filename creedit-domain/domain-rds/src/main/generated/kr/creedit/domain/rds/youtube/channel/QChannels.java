package kr.creedit.domain.rds.youtube.channel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannels is a Querydsl query type for Channels
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QChannels extends BeanPath<Channels> {

    private static final long serialVersionUID = -811198072L;

    public static final QChannels channels1 = new QChannels("channels1");

    public final ListPath<Channel, QChannel> channels = this.<Channel, QChannel>createList("channels", Channel.class, QChannel.class, PathInits.DIRECT2);

    public QChannels(String variable) {
        super(Channels.class, forVariable(variable));
    }

    public QChannels(Path<? extends Channels> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChannels(PathMetadata metadata) {
        super(Channels.class, metadata);
    }

}

