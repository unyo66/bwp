package com.bwp.app.dto;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Item;
import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        Long itemId,
        int type,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleDto of(UserAccountDto userAccountDto, Long itemId, int type, String title, String content) {
        return new ArticleDto(null, userAccountDto, itemId, type, title, content, null, null);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getItemId(),
                entity.getType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                itemId,
                type,
                title,
                content
        );
    }
}
