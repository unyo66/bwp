package com.bwp.app.dto;

import com.bwp.app.domain.Article;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsDto(
        Long id,
        UserAccountDto userAccountDto,
        Long itemId,
        int type,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Set<CommentDto> commentDtos
) {
    public static ArticleWithCommentsDto of(UserAccountDto userAccountDto, Long itemId, int type, String title, String content, Set<CommentDto> commentDtos) {
        return new ArticleWithCommentsDto(null, userAccountDto, itemId, type, title, content, null, null, commentDtos);
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return new ArticleWithCommentsDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getItemId(),
                entity.getType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getComments().stream()
                        .map(CommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
                //TODO: .collect 부분 잘 이해가 안감
        );
    }
}
