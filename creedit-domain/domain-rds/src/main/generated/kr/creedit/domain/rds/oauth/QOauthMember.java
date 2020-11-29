package kr.creedit.domain.rds.oauth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOauthMember is a Querydsl query type for OauthMember
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOauthMember extends EntityPathBase<OauthMember> {

    private static final long serialVersionUID = 1809139640L;

    public static final QOauthMember oauthMember = new QOauthMember("oauthMember");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<kr.creedit.domain.rds.role.Role> role = createEnum("role", kr.creedit.domain.rds.role.Role.class);

    public QOauthMember(String variable) {
        super(OauthMember.class, forVariable(variable));
    }

    public QOauthMember(Path<? extends OauthMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOauthMember(PathMetadata metadata) {
        super(OauthMember.class, metadata);
    }

}

