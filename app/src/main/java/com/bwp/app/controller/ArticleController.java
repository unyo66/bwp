package com.bwp.app.controller;

import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ArticleWithCommentsDto;
import com.bwp.app.dto.CommentDto;
import com.bwp.app.serucity.BoardPrincipal;
import com.bwp.app.service.ArticleService;
import com.bwp.app.service.CommentService;
import com.bwp.app.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;
    @GetMapping
    public String articles(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map) {
        Page<ArticleDto> freeArticles = articleService.fiveArticlesByType(1, pageable);
        Page<ArticleDto> tipArticles = articleService.fiveArticlesByType(2, pageable);
        Page<ArticleDto> reviewArticles = articleService.fiveArticlesByType(3, pageable);

        map.addAttribute("freeArticles", freeArticles);
        map.addAttribute("tipArticles", tipArticles);
        map.addAttribute("reviewArticles", reviewArticles);
        return "article/index";
    }
    @GetMapping("/{type}")
    public String typeArticles(@PathVariable int type,  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map) {
        Page<ArticleDto> articles = articleService.articlesByType(type, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        map.addAttribute("type", type);
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("totalCount", articleService.getArticleCount());
        return "article/list";
    }

    @GetMapping("/detail/{articleId}")
    public String articleWithComments(@PathVariable Long articleId, ModelMap map, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        ArticleWithCommentsDto article = articleService.articleWithComment(articleId);
        map.addAttribute("article", article);
        List<CommentDto> comments = articleService.commentsForArticle(articleId);
        map.addAttribute("comments", comments);
        return "article/detail";
    }

    @GetMapping("/articles/form")
    public String createNewArticle() {
        return "article/form";
    }
}
