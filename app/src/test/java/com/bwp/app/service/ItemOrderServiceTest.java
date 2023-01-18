package com.bwp.app.service;

import com.bwp.app.domain.Company;
import com.bwp.app.domain.Item;
import com.bwp.app.domain.ItemOrder;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.CompanyDto;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.dto.ItemOrderDto;
import com.bwp.app.dto.UserAccountDto;
import com.bwp.app.repository.CompanyRepository;
import com.bwp.app.repository.ItemOrderRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemOrderServiceTest {
    @InjectMocks
    private ItemOrderService sut;
    @Mock
    private ItemOrderRepository itemOrderRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private CompanyRepository companyRepository;
    Pageable pageable = Pageable.ofSize(20);
    @DisplayName("특정 유저의 모든 주문 내역 (유저용)")
    @Test
    void searchOrdersOfUser() {
        // Given
        Long userId = 1L;
        given(itemOrderRepository.findByUserAccount_Id(userId, pageable)).willReturn(Page.empty());
        // When
        Page<ItemOrderDto> itemOrderDtos = sut.searchOrdersOfUser(userId, pageable);
        // Then
        assertThat(itemOrderDtos).isEmpty();
        then(itemOrderRepository).should().findByUserAccount_Id(userId, pageable);
    }

    @DisplayName("특정 유저의 특정 단계 주문 내역 (유저용)")
    @Test
    void searchOrdersOfUserByOrderStep() {
        // Given
        Long userId = 1L;
        int orderStep = 1;
        given(itemOrderRepository.findByOrderStepAndUserAccount_Id(orderStep, userId, pageable)).willReturn(Page.empty());
        // When
        Page<ItemOrderDto> itemOrderDtos = sut.searchOrdersOfUserByOrderStep(orderStep, userId, pageable);
        // Then
        assertThat(itemOrderDtos).isEmpty();
        then(itemOrderRepository).should().findByOrderStepAndUserAccount_Id(orderStep, userId, pageable);
    }

    @DisplayName("특정 업체의 모든 주문 내역 (업체용)")
    @Test
    void searchOrdersOfCompany() {
        // Given
        Long companyId = 1L;
        given(itemOrderRepository.findByItem_Company_Id(companyId, pageable)).willReturn(Page.empty());
        // When
        Page<ItemOrderDto> itemOrderDtos = sut.searchOrdersOfCompany(companyId, pageable);
        // Then
        assertThat(itemOrderDtos).isEmpty();
        then(itemOrderRepository).should().findByItem_Company_Id(companyId, pageable);
    }

    @DisplayName("특정 업체의 특정 단계 주문 내역 (업체용)")
    @Test
    void searchOrdersOfCompanyByOrderStep() {
        // Given
        Long companyId = 1L;
        int orderStep = 1;
        given(itemOrderRepository.findByOrderStepAndItem_Company_Id(orderStep, companyId, pageable)).willReturn(Page.empty());
        // When
        Page<ItemOrderDto> itemOrderDtos = sut.searchOrdersOfCompanyByOrderStep(orderStep, companyId, pageable);
        // Then
        assertThat(itemOrderDtos).isEmpty();
        then(itemOrderRepository).should().findByOrderStepAndItem_Company_Id(orderStep, companyId, pageable);
    }

    @DisplayName("새 주문 만들기 (유저용)")//TODO: 다시
    @Test
    void createOrder() {
        // Given
        ItemOrderDto itemOrderDto = createItemOrderDto();
        given(itemOrderRepository.save(any(ItemOrder.class))).willReturn(createItemOrder());
        given(userAccountRepository.getReferenceById(itemOrderDto.id())).willReturn(createUserAccount());
        // When
        sut.createOrder(itemOrderDto);
        // Then
        then(itemOrderRepository).should().save(any(ItemOrder.class));
    }

    //TODO: 다시
    @DisplayName("orderStep 변경 (유저용)")
    @Test
    void updateOrderForUser() {
        // Given
        Long itemOrderId = 1L;
        ItemOrderDto itemOrderDto = createItemOrderDto(2);
        ItemOrder itemOrder = createItemOrder();
        given(itemOrderRepository.getReferenceById(itemOrderId)).willReturn(itemOrder);
        given(userAccountRepository.getReferenceById(itemOrderDto.userAccountDto().id())).willReturn(any(UserAccount.class));
        // When
        sut.updateOrderForUser(itemOrderId, itemOrderDto);
        // Then
        assertThat(itemOrder)
                .hasFieldOrPropertyWithValue("item", createItem())
                .hasFieldOrPropertyWithValue("userAccount", createUserAccount())
                .hasFieldOrPropertyWithValue("itemCount", itemOrderDto.itemCount())
                .hasFieldOrPropertyWithValue("orderStep", itemOrderDto.orderStep())
                .hasFieldOrPropertyWithValue("optionGrinding", itemOrderDto.optionGrinding());
        then(itemOrderRepository).should().getReferenceById(itemOrderId);
    }

    @DisplayName("orderStep 변경 (업체용)")
    @Test
    void updateOrderForCompany() {
        // Given
        // When
        // Then
    }



    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "bwp@test.com",
                "asdf",
                "bwp",
                "test address",
                "010-xxxx-xxxx",
                "test notice"
        );
    }
    private CompanyDto createCompanyDto() {
        return CompanyDto.of(
                1L,
                createUserAccountDto(),
                "test name"
        );
    }
    private ItemDto createItemDto() {
        return ItemDto.of(
                "test name",
                createCompanyDto(),
                1L,
                "test rp",
                "test og",
                "test memo",
                "test th",
                "test if",
                TRUE
        );
    }
    private ItemDto createItemDto(Long id) {
        return ItemDto.of(
                id,
                "test name",
                createCompanyDto(),
                1L,
                "test rp",
                "test og",
                "test memo",
                "test th",
                "test if",
                TRUE
        );
    }
    private ItemOrderDto createItemOrderDto() {
        return ItemOrderDto.of(
                createItemDto(1L),
                createUserAccountDto(),
                1L,
                1,
                1
        );
    }
    private ItemOrderDto createItemOrderDto(int orderStep) {
        return ItemOrderDto.of(
                createItemDto(1L),
                createUserAccountDto(),
                1L,
                orderStep,
                1
        );
    }

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
    private ItemOrder createItemOrder() {
        return ItemOrder.of(
                createItem(),
                createUserAccount(),
                1L,
                2,
                1
        );
    }
}