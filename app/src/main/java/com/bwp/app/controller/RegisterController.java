package com.bwp.app.controller;

import com.bwp.app.dto.UserAccountRequest;
import com.bwp.app.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
@RequiredArgsConstructor
@Controller
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
        return "/register_success";
    }
}
