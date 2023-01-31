package com.bwp.app.dto;

import com.bwp.app.domain.Company;
import com.bwp.app.domain.Item;

import java.time.LocalDateTime;

public record ItemResponse(
        Long id,
        String name,
        CompanyDto companyDto,
        Long price,
        String roastingPoint,
        String origin,
        String memo,
        String thumbnailImg,
        String infoImg,
        Boolean stock,
        LocalDateTime createdAt
) {
    public static ItemResponse of(String name, CompanyDto companyDto, Long price, String roastingPoint, String origin, String memo, String thumbnailImg, String infoImg, Boolean stock) {
        return new ItemResponse(null, name, companyDto, price, roastingPoint, origin, memo, thumbnailImg, infoImg, stock, null);
    }

    public static ItemResponse of(Long id, String name, CompanyDto companyDto, Long price, String roastingPoint, String origin, String memo, String thumbnailImg, String infoImg, Boolean stock) {
        return new ItemResponse(id, name, companyDto, price, roastingPoint, origin, memo, thumbnailImg, infoImg, stock, null);
    }
    /** entity -> dto */
    public static ItemResponse from(Item entity) {
        return new ItemResponse(
                entity.getId(),
                entity.getName(),
                CompanyDto.from(entity.getCompany()),
                entity.getPrice(),
                entity.getRoastingPoint(),
                entity.getOrigin(),
                entity.getMemo(),
                entity.getThumbnailImg(),
                entity.getInfoImg(),
                entity.getStock(),
                entity.getCreatedAt()
        );
    }

    /** dto -> entity */
    public Item toEntity(Company company) {
        return Item.of(
                name,
                company,
                price,
                roastingPoint,
                origin,
                memo,
                thumbnailImg,
                infoImg,
                stock
        );
    }
}
