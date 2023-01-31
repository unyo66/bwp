package com.bwp.app.controller;

import com.bwp.app.domain.Item;
import com.bwp.app.domain.type.SearchType;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemOrderDto;
import com.bwp.app.dto.ItemOrderRequest;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.serucity.BoardPrincipal;
import com.bwp.app.service.ItemOrderService;
import com.bwp.app.service.ItemService;
import com.bwp.app.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final PaginationService paginationService;
    private final ItemOrderRepository itemOrderRepository;

    @GetMapping
    public String items(
            @RequestParam(required = false) String roastingPoint,
            @RequestParam(required = false) String origin,
            @PageableDefault(size = 16, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {
        map.addAttribute("searchTypes", SearchType.values());

        Page<ItemDto> items = itemService.searchItems(roastingPoint, origin, pageable);
        Map options = itemService.getOptions();
        map.addAttribute("items", items);
        map.addAttribute("options", options);
        Boolean noResult = items.isEmpty();
        map.addAttribute("noResult", noResult);

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), items.getTotalPages());
        map.addAttribute("paginationBarNumbers", barNumbers);
        return "item/index";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, ModelMap map) {
        ItemWithArticlesDto itemWithArticlesDto = itemService.getItem(itemId);
        map.addAttribute("itemDetail", itemWithArticlesDto);
        return "item/detail";
    }
}
