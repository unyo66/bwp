package com.bwp.app.repository;
import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserAccount_Id(Long userId);
    List<Comment> findByArticle_IdOrderByCommentOrder(Long articleId);
    void deleteByIdAndUserAccount_Id(Long commentId, Long userid);

}
