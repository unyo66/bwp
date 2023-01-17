package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Item;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ArticleWithCommentsDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.ItemRepository;
import com.bwp.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final ItemRepository itemRepository;

    /** 모든 게시글 조회 (사용x) */

    /** 게시판 타입에 따라 게시글리스트 조회 (게시판 메인, 각 게시판 리스트) */
    @Transactional(readOnly = true)
    public Page<ArticleDto> articlesByType(int type, Pageable pageable) {
        return articleRepository.findByType(type, pageable).map(ArticleDto::from);
    }

    /** 유저에 따라 게시글리스트 조회 */
    @Transactional(readOnly = true)
    public Page<ArticleDto> articlesByUserId(Long userId, Pageable pageable) {
        return articleRepository.findByUserAccount_Id(userId, pageable).map(ArticleDto::from);
    }

    /** 게시글 댓글 포함 호출 */
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto articleWithComment(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
    }

    /** 게시글 생성 */
    public void saveArticle(ArticleDto articleDto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(articleDto.userAccountDto().id());
        articleRepository.save(articleDto.toEntity(userAccount));
    }

    /** 게시글 수정 */
    //TODO: articleId가 필요한가? 어차피 id는 안 바뀌는데? CommentService 수정부분 참고
    public void updateArticle(Long articleId, ArticleDto newArticleDto) {
        try {
            Article oldArticle = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(newArticleDto.id());
            if (oldArticle.getUserAccount().equals(userAccount)) {
                if (newArticleDto.title() != null) {
                    oldArticle.setTitle(newArticleDto.title());
                }
                if (newArticleDto.content() != null) {
                    oldArticle.setContent(newArticleDto.content());
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글을 찾을 수 없습니다.");
        }
    }

    /** 게시글 삭제 */
    public void deleteArticle(Long articleId, Long userId) {
        articleRepository.deleteByIdAndUserAccount_Id(articleId, userId);
    }

    /** 게시글 전체 개수 구하기 : 이전, 다음 버튼 비활성화용 */
    public Long getArticleCount() {
        return articleRepository.count();
    }
}
