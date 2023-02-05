package com.bwp.app.controller;

import com.bwp.app.domain.Company;
import com.bwp.app.domain.type.FormStatus;
import com.bwp.app.dto.*;
import com.bwp.app.repository.CompanyRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.serucity.BoardPrincipal;
import com.bwp.app.service.ArticleService;
import com.bwp.app.service.CommentService;
import com.bwp.app.service.ItemService;
import com.bwp.app.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;
    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;
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
        if (boardPrincipal != null) {
            map.addAttribute("userAccount", boardPrincipal);
        }
        ArticleWithCommentsDto article = articleService.articleWithComment(articleId);
        map.addAttribute("article", article);
        List<CommentDto> comments = articleService.commentsForArticle(articleId);
        map.addAttribute("comments", comments);
        return "article/detail";
    }

    @GetMapping("/form")
    public String createNewArticle(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);
        List<CompanyDto> companies = companyRepository.findAll().stream().map(CompanyDto::from).toList();
        map.addAttribute("companies", companies);
        //TODO: CompanyResponse 로 새로 만들어 필요한 데이터만 보내기
        return "article/form";
    }
    @PostMapping("/form")
    public String insertArticle(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest) {
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles";
    }

    /* 게시글 수정 페이지로 이동 */
    @GetMapping("/{articleId}/form")
    public String gotoUpdateForm(@PathVariable Long articleId, ModelMap map) {
        ArticleDto articleDto = articleService.getArticleById(articleId);
        System.out.println(articleDto);
        map.addAttribute("article", articleDto);
        map.addAttribute("formStatus", FormStatus.UPDATE);
        List<CompanyDto> companies = companyRepository.findAll().stream().map(CompanyDto::from).toList();
        map.addAttribute("companies", companies);
        return "article/form";
    }

    /* 게시글 수정 완료 */
    @PostMapping("{articleId}/form")
    public String updateArticle(@PathVariable Long articleId,
                                ArticleRequest articleRequest,
                                @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles";
    }

    /* form 에서 companyId 로 item 가져와서 보내기 ajax */
    @ResponseBody
    @PostMapping("/form/items")
    public List<ItemDto> getCompanySendItems(@RequestBody Long companyId) {
        List<ItemDto> itemDto = itemRepository.findAllByCompany_Id(companyId).stream().map(ItemDto::from).toList();
        return itemDto;
    }

    /* 게시글 삭제하기 */
    @GetMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        if (boardPrincipal == null) {
            return "redirect:/login";
        }
        else if (articleId != boardPrincipal.userId()) {
            return "noAuthorization";
        }
        articleService.deleteArticle(articleId, boardPrincipal.userId());
        return "redirect:/articles";
    }
}
