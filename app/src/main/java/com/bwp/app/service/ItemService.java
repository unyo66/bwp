package com.bwp.app.service;

import com.bwp.app.domain.Item;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ArticleRepository articleRepository;

    /** 상품 전체 호출 */
    @Transactional(readOnly = true)
    public Page<ItemDto> searchItems(Pageable pageable) {
        return itemRepository.findAll(pageable).map(ItemDto::from);
    }

    /** 상품 하나 호출 */
    @Transactional(readOnly = true)
    public ItemWithArticlesDto getItem(Long itemId) {
        //stream().map : 요소를 특정 조건에 맞게 변환
        List<ArticleDto> articleDtos = articleRepository.findByItemId(itemId).stream().map(ArticleDto::from).toList();
        //Optional 객체 사용법 중 - itemId 에 맞는 item 이 없을 경우 예외 발생
        return ItemWithArticlesDto.from(itemRepository.findById(itemId).orElseThrow(NullPointerException::new), articleDtos);
    }
}
