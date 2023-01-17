package com.bwp.app.service;

import com.bwp.app.dto.ItemDto;
import com.bwp.app.repository.ItemRepository;
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
class ItemServiceTest {

    @InjectMocks
    private ItemService sut;

    @Mock
    private ItemRepository itemRepository;

    @DisplayName("[ITEM] 다 뽑기")
    @Test
    void giveNoneReturnItems() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(itemRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ItemDto> itemDtos = sut.searchItems(pageable);

        // Then
        assertThat(itemDtos).isEmpty();
        then(itemRepository).should().findAll(pageable);
    }
}