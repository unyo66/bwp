package com.bwp.app.dto;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.ItemOrder;
import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record ItemOrderDto(
        Long id,
        ItemDto itemDto,
        UserAccountDto userAccountDto,
        Long itemCount,
        Integer orderStep,
        Integer optionGrinding,
        Integer optionWeight,
        LocalDateTime createdAt
) {
    public static ItemOrderDto of(Long id, ItemDto itemDto, UserAccountDto userAccountDto, Long itemCount, int orderStep, int optionGrinding, int optionWeight, LocalDateTime createdAt) {
        return new ItemOrderDto(id, itemDto, userAccountDto, itemCount, orderStep, optionGrinding, optionWeight, createdAt);
    }
    public static ItemOrderDto of(ItemDto itemDto, UserAccountDto userAccountDto, Long itemCount, int orderStep, int optionGrinding, int optionWeight) {
        return new ItemOrderDto(null, itemDto, userAccountDto, itemCount, orderStep, optionGrinding, optionWeight, null);
    }


    /** entity -> dto */
    public static ItemOrderDto from(ItemOrder entity) {
        return new ItemOrderDto(
                entity.getId(),
                ItemDto.from(entity.getItem()),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getItemCount(),
                entity.getOrderStep(),
                entity.getOptionGrinding(),
                entity.getOptionWeight(),
                entity.getCreatedAt());
    };

    /** dto -> entity */
    public ItemOrder toEntity(Item item, UserAccount userAccount) {
        return ItemOrder.of(
                item,
                userAccount,
                itemCount,
                orderStep,
                optionGrinding,
                optionWeight
        );
    }
}
