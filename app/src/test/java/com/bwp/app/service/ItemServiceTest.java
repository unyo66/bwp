package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Company;
import com.bwp.app.domain.Item;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.domain.type.SearchType;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.dto.UserAccountDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @InjectMocks
    private ItemService sut;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ItemOrderRepository itemOrderRepository;

    Pageable pageable = Pageable.ofSize(20);
    @DisplayName("상품 전체 호출")
    @Test
    void searchItems() {
        // Given
        String roastingPoint = "라이트 로스팅";
        String origin = "";
        given(itemRepository.findByRoastingPoint(roastingPoint, pageable)).willReturn(Page.empty());
        // When
        Page<ItemDto> itemDtos = sut.searchItems(roastingPoint, origin, pageable);
        // Then
        assertThat(itemDtos).isEmpty();
        then(itemRepository).should().findByRoastingPoint(roastingPoint, pageable);
    }

    @DisplayName("상품 하나 호출")
    @Test
    void getItem() {
        // Given
        Long itemId = 1L;
        Item item = createItem();
        List<ArticleDto> articleDtos = articleRepository.findByItemId(itemId).stream().map(ArticleDto::from).toList();
        given(itemRepository.findById(itemId)).willReturn(Optional.of(item));
        // When
        ItemWithArticlesDto itemWithArticlesDto = sut.getItem(itemId);
        // Then
        assertThat(itemWithArticlesDto)
                .hasFieldOrPropertyWithValue("name", item.getName())
                .hasFieldOrPropertyWithValue("articleDtos", articleDtos)
                .hasFieldOrPropertyWithValue("price", item.getPrice())
                .hasFieldOrPropertyWithValue("roastingPoint", item.getRoastingPoint())
                .hasFieldOrPropertyWithValue("origin", item.getOrigin())
                .hasFieldOrPropertyWithValue("memo", item.getMemo())
                .hasFieldOrPropertyWithValue("thumbnailImg", item.getThumbnailImg())
                .hasFieldOrPropertyWithValue("infoImg", item.getInfoImg())
                .hasFieldOrPropertyWithValue("stock", item.getStock());
        then(itemRepository).should().findById(itemId);
    }
    @DisplayName("BEST ITEM")
    @Test
    void searchBestItems() {
        // Given
        List<Item> bestItems = itemOrderRepository.findBestItem();
        // When
        // Then
        assertThat(bestItems).isEmpty();
    }
    ///////

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "bwp@test.com",
                "asdf",
                "bwp",
                "test address",
                "010-xxxx-xxxx",
                "test notice"
        );
    }
    private Company createCompany() {
        return Company.of(
                "test name",
                createUserAccount()
        );
    }
    private Item createItem() {
        return Item.of(
                "test name",
                createCompany(),
                1L,
                "test rp",
                "test og",
                "test memo",
                "test th",
                "test if",
                TRUE
        );
    }



}