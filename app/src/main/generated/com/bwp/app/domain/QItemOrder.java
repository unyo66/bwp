package com.bwp.app.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemOrder is a Querydsl query type for ItemOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemOrder extends EntityPathBase<ItemOrder> {

    private static final long serialVersionUID = 1921191038L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemOrder itemOrder = new QItemOrder("itemOrder");

    public final QAuditingFields _super = new QAuditingFields(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final NumberPath<Long> itemCount = createNumber("itemCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> optionGrinding = createNumber("optionGrinding", Integer.class);

    public final NumberPath<Integer> optionWeight = createNumber("optionWeight", Integer.class);

    public final NumberPath<Integer> orderStep = createNumber("orderStep", Integer.class);

    public final QUserAccount userAccount;

    public QItemOrder(String variable) {
        this(ItemOrder.class, forVariable(variable), INITS);
    }

    public QItemOrder(Path<? extends ItemOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemOrder(PathMetadata metadata, PathInits inits) {
        this(ItemOrder.class, metadata, inits);
    }

    public QItemOrder(Class<? extends ItemOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.userAccount = inits.isInitialized("userAccount") ? new QUserAccount(forProperty("userAccount")) : null;
    }

}

