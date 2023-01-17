package com.bwp.app.service;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.ItemOrder;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.ItemOrderDto;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemOrderService {
    private final ItemOrderRepository itemOrderRepository;
    private final ItemRepository itemRepository;
    private final UserAccountRepository userAccountRepository;

    /** 특정 유저의 모든 주문 내역 (유저용) */
    @Transactional(readOnly = true)
    public Page<ItemOrderDto> searchOrdersOfUser(Long userId, Pageable pageable) {
        return itemOrderRepository.findByUserAccount_Id(userId, pageable).map(ItemOrderDto::from);
    }

    /** 특정 유저의 특정 단계 주문 내역 (유저용) */
    @Transactional(readOnly = true)
    public Page<ItemOrderDto> searchOrdersOfUserByOrderStep(int orderStep, Long userId, Pageable pageable) {
        return itemOrderRepository.findByOrderStepAndUserAccount_Id(orderStep, userId, pageable).map(ItemOrderDto::from);
    }

    /** 특정 업체의 모든 주문 내역 (업체용) */
    @Transactional(readOnly = true)
    public Page<ItemOrderDto> searchOrdersOfCompany(Long companyId, Pageable pageable) {
        return itemOrderRepository.findByItem_Company_Id(companyId, pageable).map(ItemOrderDto::from);
    }

    /** 특정 업체의 특정 단계 주문 내역 (업체용) */
    @Transactional(readOnly = true)
    public Page<ItemOrderDto> searchOrdersOfCompanyByOrderStep(int orderStep, Long userId, Pageable pageable) {
        return itemOrderRepository.findByOrderStepAndItem_Company_Id(orderStep, userId, pageable).map(ItemOrderDto::from);
    }

    /** 새 주문 만들기 (유저용) */
    @Transactional(readOnly = true)
    public void createOrder(ItemOrderDto itemOrderDto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(itemOrderDto.id());
        Item item = itemRepository.getReferenceById(itemOrderDto.id());
        itemOrderRepository.save(itemOrderDto.toEntity(item, userAccount));
    }

    /** orderStep 변경 (유저, 업체용) */
    @Transactional(readOnly = true)
    public void updateOrder(Long itemOrderId, ItemOrderDto newItemOrderDto) {
        try {
            ItemOrder oldItemOrder = itemOrderRepository.getReferenceById(itemOrderId);
            UserAccount userAccount = userAccountRepository.getReferenceById(newItemOrderDto.userAccountDto().id());
            if (oldItemOrder.getUserAccount().equals(userAccount)) {
                oldItemOrder.setOrderStep(newItemOrderDto.orderStep());
            }
        } catch (EntityNotFoundException e) {
            log.warn("해당 주문을 찾을 수 없습니다.");
        }
    }

}
