package com.bwp.app.repository;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.ItemOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
    Page<ItemOrder> findByUserAccount_Id(Long userId, Pageable pageable);
    Page<ItemOrder> findByOrderStepAndUserAccount_Id(int orderStep, Long userId, Pageable pageable);

    Page<ItemOrder> findByItem_Company_Id(Long CompanyId, Pageable pageable);
    Page<ItemOrder> findByOrderStepAndItem_Company_Id(int orderStep, Long CompanyId, Pageable pageable);
    void deleteByIdAndUserAccount_Id(Long id, Long userId);

    @Query(value = "SELECT DISTINCT o.item, COUNT(o.item) " +
            "FROM ItemOrder o " +
            "GROUP BY o.item " +
            "ORDER BY COUNT(o.item) DESC")
    List<Item> findBestItem();
}
