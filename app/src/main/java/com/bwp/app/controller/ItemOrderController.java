package com.bwp.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//TODO: 시큐리티 하고나서 하기
@RequiredArgsConstructor
@Controller
@RequestMapping("/itemOrders")
public class ItemOrderController {
    @GetMapping
    public String basket(@PathVariable Long userId) {
        return "/itemOrder/basket";
    }
}
