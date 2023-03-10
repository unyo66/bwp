package com.bwp.app.dto;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        Long parentId,
        int commentOrder,
        int commentDepth,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static CommentDto of(Long articleId, UserAccountDto userAccountDto, Long parentId, int commentOrder, int commentDepth, String content) {
        return new CommentDto(null, articleId, userAccountDto, parentId, commentOrder, commentDepth, content, null, null);
    }

    public static CommentDto from(Comment entity) {
        return new CommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getParentId(),
                entity.getCommentOrder(),
                entity.getCommentDepth(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    public Comment toEntity(UserAccount userAccount, Article article) {
        return Comment.of(
                userAccount,
                article,
                parentId,
                commentOrder,
                commentDepth,
                content
        );
    }

}
