package com.bwp.app.service;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.type.SearchType;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemOrderRepository itemOrderRepository;
    private final ArticleRepository articleRepository;

    /** 상품 전체 호출 */
    @Transactional(readOnly = true)
    public Page<ItemDto> searchItems(String roastingPoint, String origin, Pageable pageable) {
        if ((roastingPoint == null || roastingPoint.isBlank()) && (origin == null || origin.isBlank())) {
            return itemRepository.findAll(pageable).map(ItemDto::from);
        }
        else if (roastingPoint == null || roastingPoint.isEmpty()) {
            return itemRepository.findByOrigin(origin, pageable).map(ItemDto::from);
        }
        else if (origin == null || origin.isEmpty()) {
            return itemRepository.findByRoastingPoint(roastingPoint, pageable).map(ItemDto::from);
        }
        else {
            return itemRepository.findByRoastingPointAndOrigin(roastingPoint, origin, pageable).map(ItemDto::from);
        }
    }

    /** 상품 하나 호출 */
    @Transactional(readOnly = true)
    public ItemWithArticlesDto getItem(Long itemId) {
        //stream().map : 요소를 특정 조건에 맞게 변환
        List<ArticleDto> articleDtos = articleRepository.findByItemId(itemId).stream().map(ArticleDto::from).toList();
        //Optional 객체 사용법 중 - itemId 에 맞는 item 이 없을 경우 예외 발생
        return ItemWithArticlesDto.from(itemRepository.findById(itemId).orElseThrow(NullPointerException::new), articleDtos);
    }

    public Map getOptions() {
        //TODO: 쓸데없이 트랜잭션이 2번에 코드만 길어짐.
        List<ItemDto> items = itemRepository.findAll().stream().map(ItemDto::from).toList();
        List<String> list;
        String[] rp = new String[items.size()];
        String[] og = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            rp[i] = items.get(i).roastingPoint();
            og[i] = items.get(i).origin();
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(rp));
        rp = linkedHashSet.toArray(new String[]{});
        linkedHashSet = new LinkedHashSet<>(Arrays.asList(og));
        og = linkedHashSet.toArray(new String[]{});
        Map<String, String[]> map= new HashMap<>();
        for (int i = 0; i < og.length; i++) {
            if (og[i].equals("블렌드")) {
                String tmp = og[0];
                og[0] = "블렌드";
                og[i] = tmp;
                break;
            }
        }
        map.put("rp", rp);
        map.put("og", og);
        return map;
    }

    /** 베스트 상품 10개 */
    public List<ItemDto> searchBestItems() {
        return itemOrderRepository.findBestItem().stream().map(ItemDto::from).toList();
    }

    /** 신상품 10개 */
    public List<ItemDto> searchNewItems() {
        return itemRepository.findAllByOrderByCreatedAt().stream().map(ItemDto::from).toList();
    }

}
