package com.bwp.app.controller;

import com.bwp.app.dto.ItemDto;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {
    private final ItemService itemService;
    @GetMapping
    public String main(ModelMap map) {
        List<ItemDto> newItems = itemService.searchNewItems();
        List<ItemDto> bestItems = itemService.searchBestItems();
        map.addAttribute("bestItems", bestItems);
        map.addAttribute("newItems", newItems);
        System.out.println("bestItems : " + bestItems);
        return "index";
    }
}
