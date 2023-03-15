package com.bwp.app.dto;

import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountRequest(
        String email,
        String pw,
        String nickname,
        String address,
        String phone
) {
    public static UserAccountRequest of(String email, String pw, String nickname, String address, String phone) {
        return new UserAccountRequest(email, pw, nickname, address, phone);
    }
    public UserAccountDto toDto(String address_detail) {
        return UserAccountDto.of(
                null,
                email,
                "{noob}" + pw,
                nickname,
                address + " " + address_detail,
                phone,
                null
        );
    }
}
