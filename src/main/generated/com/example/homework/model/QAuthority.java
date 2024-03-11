package com.example.homework.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthority is a Querydsl query type for Authority
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthority extends EntityPathBase<Authority> {

    private static final long serialVersionUID = -318368783L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthority authority = new QAuthority("authority");

    public final QTimeStamped _super = new QTimeStamped(this);

    public final StringPath authorityName = createString("authorityName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QUser user;

    public QAuthority(String variable) {
        this(Authority.class, forVariable(variable), INITS);
    }

    public QAuthority(Path<? extends Authority> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthority(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthority(PathMetadata metadata, PathInits inits) {
        this(Authority.class, metadata, inits);
    }

    public QAuthority(Class<? extends Authority> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

