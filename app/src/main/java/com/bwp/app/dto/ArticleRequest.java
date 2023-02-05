package com.bwp.app.dto;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleRequest(
        Long itemId,
        int type,
        String title,
        String content
) {
    public static ArticleRequest of(Long itemId, int type, String title, String content) {
        return new ArticleRequest(itemId, type, title, content);
    }

    public static ArticleRequest of(int type, String title, String content) {
        return new ArticleRequest(null, type, title, content);
    }
    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                itemId,
                type,
                title,
                content
        );
    }
}
