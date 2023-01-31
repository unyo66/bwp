package com.bwp.app.dto;

public record ItemOrderRequest(
        Long itemId,
        Long itemCount,
        Integer orderStep,
        Integer optionGrinding,
        Integer optionWeight
) {
    public static ItemOrderRequest of (Long itemId,
                                       Long itemCount,
                                       int orderStep,
                                       int optionGrinding,
                                       int optionWeight) {
        return new ItemOrderRequest(itemId, itemCount, orderStep, optionGrinding, optionWeight);
    }

    public ItemOrderDto toDto (ItemDto itemDto, UserAccountDto userAccountDto) {
        return ItemOrderDto.of(
                itemDto,
                userAccountDto,
                itemCount,
                orderStep,
                optionGrinding,
                optionWeight
        );
    }
}
