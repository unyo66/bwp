package com.bwp.app.dto;

import com.bwp.app.domain.Company;
import com.bwp.app.domain.UserAccount;

public record CompanyDto(
        Long id,
        UserAccountDto userAccountDto,
        String name
) {
    public static CompanyDto of(Long id, UserAccountDto userAccountDto, String name) {
        return new CompanyDto(id, userAccountDto, name);
    }

    /** entity -> dto */
    public static CompanyDto from(Company entity) {
        return new CompanyDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getName()
        );
    }

    /** dto -> entity */
    public Company toEntity(UserAccount userAccount) {
        return Company.of(
                name,
                userAccount
        );
    }
}
