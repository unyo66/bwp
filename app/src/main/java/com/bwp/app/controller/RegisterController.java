package com.bwp.app.controller;

import com.bwp.app.domain.type.SearchType;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemWithArticlesDto;
import com.bwp.app.dto.UserAccountRequest;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.service.ItemService;
import com.bwp.app.service.PaginationService;
import com.bwp.app.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserAccountService userAccountService;
    @GetMapping
    public String register() {
        return "register";
    }
    @PostMapping("/add")
    public String add(UserAccountRequest userAccountRequest, String address_detail) {
        System.out.println(userAccountRequest);
        System.out.println(address_detail);
        userAccountService.addUserAccount(userAccountRequest, address_detail);
        return "redirect:/";
    }
}
