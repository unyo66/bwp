package com.bwp.app.repository;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepository extends
        JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>,
        QuerydslBinderCustomizer<QItem> {

    Page<Item> findByRoastingPoint(String roastingPoint, Pageable pageable);
    Page<Item> findByOrigin(String origin, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QItem root) {
        bindings.excludeUnlistedProperties(true);

        bindings.including(root.roastingPoint, root.origin);
    }
}
