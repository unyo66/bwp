package com.bwp.app.controller;

import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemOrderDto;
import com.bwp.app.dto.ItemOrderRequest;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.serucity.BoardPrincipal;
import com.bwp.app.service.ItemOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/itemOrders")
public class ItemOrderController {
    private final ItemRepository itemRepository;
    private final ItemOrderRepository itemOrderRepository;
    private final ItemOrderService itemOrderService;

    @GetMapping("/orderList")//
    public String pay(@AuthenticationPrincipal BoardPrincipal boardPrincipal, ModelMap map, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemOrderDto> itemOrderDto = itemOrderRepository.findByUserAccount_Id(boardPrincipal.userId(), pageable).map(ItemOrderDto::from);
        map.addAttribute("itemOrders", itemOrderDto);
        return "itemOrder/orderList";
    }
    @PostMapping("/order")
    public String createOrder(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                              ItemOrderRequest itemOrderRequest) {
        ItemDto itemDto = itemRepository.findById(itemOrderRequest.itemId()).map(ItemDto::from).orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        itemOrderService.createOrder(itemOrderRequest.toDto(itemDto, boardPrincipal.toDto()));
        System.out.println(itemOrderRequest);
        System.out.println(itemDto);
        return "redirect:/itemOrders/basket";
    }
    @GetMapping("/basket")
    public String toBasket(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                           @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map) {
        Page<ItemOrderDto> itemOrderDto = itemOrderRepository.findByOrderStepAndUserAccount_Id(1, boardPrincipal.userId(), pageable).map(ItemOrderDto::from);
        map.addAttribute("itemOrders", itemOrderDto);
        return "itemOrder/basket";
    }
    @PostMapping("/basket")
    @ResponseBody
    public String createBasket(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                               @RequestBody ItemOrderRequest itemOrderRequest) {
        ItemDto itemDto = itemRepository.findById(itemOrderRequest.itemId()).map(ItemDto::from).orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        itemOrderService.createOrder(itemOrderRequest.toDto(itemDto, boardPrincipal.toDto()));
        System.out.println(itemOrderRequest);
        System.out.println(itemDto);
        return "done";
    }

    @PostMapping("/basket/delete")
    @ResponseBody
    public String deleteBasket(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                               @RequestBody Long itemOrderId) {
        System.out.println(itemOrderId);
        itemOrderService.deleteOrder(itemOrderId);
        return "done";
    }
}
