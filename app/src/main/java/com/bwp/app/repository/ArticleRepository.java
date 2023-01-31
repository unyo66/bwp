package com.bwp.app.repository;

import com.bwp.app.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByItemId(Long itemId);
    Page<Article> findTop5ByType(int type, Pageable pageable);
    Page<Article> findByType(int type, Pageable pageable);
    Page<Article> findByUserAccount_Id(Long userId, Pageable pageable);
    void deleteByIdAndUserAccount_Id(Long articleId, Long userId);
}
