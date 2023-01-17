package com.bwp.app.dto;

import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String email,
        String pw,
        String nickname,
        String address,
        String phone,
        String notice,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserAccountDto of(Long id, String email, String pw, String nickname, String address, String phone, String notice) {
        return new UserAccountDto(id, email, pw, nickname, address, phone, notice, null, null);
    }

    /** entity -> dto */
    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPw(),
                entity.getNickname(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getNotice(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    /** dto -> entity */
    public UserAccount toEntity() {
        return UserAccount.of(
                email,
                pw,
                nickname,
                address,
                phone,
                notice
        );
    }
}
