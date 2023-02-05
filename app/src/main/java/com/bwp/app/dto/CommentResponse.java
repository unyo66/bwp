package com.bwp.app.dto;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import com.bwp.app.domain.UserAccount;

public record CommentResponse(
        int commentOrder,
        int commentDepth
) {
    public static CommentResponse of(int commentOrder, int commentDepth) {
        return new CommentResponse(commentOrder, commentDepth);
    }

    public static CommentResponse from(Comment entity) {
        return new CommentResponse(
                entity.getCommentOrder(),
                entity.getCommentDepth()
        );
    }
}
