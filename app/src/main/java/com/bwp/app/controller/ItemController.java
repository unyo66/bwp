package com.bwp.app.controller;

import com.bwp.app.domain.Item;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.service.ItemService;
import com.bwp.app.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final PaginationService paginationService;
    @GetMapping
    public String items(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {

        Page<ItemDto> items = itemService.searchItems(pageable);
        map.addAttribute("items", items);
        return "item/index";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, ModelMap map) {
        ItemWithArticlesDto itemWithArticlesDto = itemService.getItem(itemId);
        map.addAttribute("item_detail", itemWithArticlesDto);
        return "item/detail";
    }
}
