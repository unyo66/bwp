package com.bwp.app.dto;

import com.bwp.app.domain.Item;
import java.time.LocalDateTime;
import java.util.List;

public record ItemWithArticlesDto(
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
        LocalDateTime createdAt,
        List<ArticleDto> articleDtos
) {
    public static ItemWithArticlesDto of(String name, CompanyDto companyDto, Long price, String roastingPoint, String origin, String memo, String thumbnailImg, String infoImg, Boolean stock, List<ArticleDto> articleDtos) {
        return new ItemWithArticlesDto(null, name, companyDto, price, roastingPoint, origin, memo, thumbnailImg, infoImg, stock, null, articleDtos);
    }
    /** entity -> dto */
    public static ItemWithArticlesDto from(Item entity, List<ArticleDto> articleDtos) {
        return new ItemWithArticlesDto(
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
                entity.getCreatedAt(),
                articleDtos
        );
    }
}
