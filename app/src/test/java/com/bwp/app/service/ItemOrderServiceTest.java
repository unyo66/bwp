package com.bwp.app.service;

import com.bwp.app.dto.ItemOrderDto;
import com.bwp.app.repository.ItemOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ItemOrderServiceTest {
    @InjectMocks
    private ItemOrderService sut;

    @Mock
    private ItemOrderRepository itemOrderRepository;

    @DisplayName("[ItemOrder] 유저에 따라 다 뽑기")
    @Test
    void searchItemOrders() {
        // Given
        Long userId = 1L;
        Pageable pageable = Pageable.ofSize(20);
        given(itemOrderRepository.findByUserAccount_Id(userId, pageable)).willReturn(Page.empty());

        // When
        Page<ItemOrderDto> itemOrderDtos = sut.searchOrdersOfUser(userId, pageable);

        // Then
        assertThat(itemOrderDtos).isEmpty();
        then(itemOrderRepository).should().findByUserAccount_Id(userId, pageable);
    }
}