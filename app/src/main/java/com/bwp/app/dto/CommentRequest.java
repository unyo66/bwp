package com.bwp.app.dto;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import com.bwp.app.domain.UserAccount;

import java.time.LocalDateTime;

public record CommentRequest(
        Long articleId,
        Long parentId,
        Integer commentOrder,
        Integer commentDepth,
        String content
) {
    public static CommentRequest of(Long articleId, Long parentId, Integer commentOrder, Integer commentDepth, String content) {
        return new CommentRequest(articleId, parentId, commentOrder, commentDepth, content);
    }

    public static CommentRequest from(Comment entity) {
        return new CommentRequest(
                entity.getArticle().getId(),
                entity.getParentId(),
                entity.getCommentOrder(),
                entity.getCommentDepth(),
                entity.getContent()
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

    public CommentDto toDto(UserAccountDto userAccountDto) {
        return CommentDto.of(
                articleId,
                userAccountDto,
                parentId,
                commentOrder,
                commentDepth,
                content
        );
    }
}
