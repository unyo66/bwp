package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ArticleWithCommentsDto;
import com.bwp.app.dto.UserAccountDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    Pageable pageable = Pageable.ofSize(20);

    @DisplayName("게시판 타입에 따라 게시글리스트 조회")
    @Test
    void articlesByType() {
        // Given
        int type = 1;
        given(articleRepository.findByType(type, pageable)).willReturn(Page.empty());
        // When
        Page<ArticleDto> articleDtos = sut.articlesByType(type, pageable);
        // Then
        assertThat(articleDtos).isEmpty();
        then(articleRepository).should().findByType(type, pageable);
    }

    @DisplayName("유저 아이디에 따라 게시글리스트 조회")
    @Test
    void articlesByUserId() {
        // Given
        Long userId = 1L;
        given(articleRepository.findByUserAccount_Id(userId, pageable)).willReturn(Page.empty());;
        // When
        Page<ArticleDto> articleDtos = sut.articlesByUserId(userId, pageable);
        // Then
        assertThat(articleDtos).isEmpty();
        then(articleRepository).should().findByUserAccount_Id(userId, pageable);
    }

    @DisplayName("게시글 하나 조회 댓글까지")
    @Test
    void articleWithComment() {
        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        // When
        ArticleWithCommentsDto articleWithCommentsDto = sut.articleWithComment(articleId);
        // Then
        assertThat(articleWithCommentsDto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 생성")
    @Test
    void saveArticle() {
        // Given
        ArticleDto articleDto = createArticleDto();
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(articleDto.userAccountDto().id())).willReturn(createUserAccount());
        // When
        sut.saveArticle(articleDto);
        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 수정")
    @Test
    void updateArticle() {
        // Given
        ArticleDto articleDto = createArticleDto("test title", "test content");
        Article article = createArticle();
        given(articleRepository.getReferenceById(articleDto.id())).willReturn(article);
        // When
        sut.updateArticle(articleDto.id(), articleDto);
        // Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", articleDto.title())
                .hasFieldOrPropertyWithValue("content", articleDto.content());
        then(articleRepository).should().getReferenceById(articleDto.id());
    }

    @DisplayName("게시글 삭제")
    @Test
    void deleteArticle() {
        // Given
        Long articleId = 1L;
        Long userId = 1L;
        willDoNothing().given(articleRepository).deleteByIdAndUserAccount_Id(articleId, userId);
        // When
        sut.deleteArticle(1L, userId);
        // Then
        then(articleRepository).should().deleteByIdAndUserAccount_Id(articleId, userId);
    }

    @DisplayName("게시글 개수 구하기")
    @Test
    void getArticleCount() {
        // Given
        Long expected = 0L;
        given(articleRepository.count()).willReturn(expected);
        // When
        Long actual = sut.getArticleCount();
        // Then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

    /////

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "test@test.com",
                "asdf",
                "testnickname",
                "test address",
                "010-xxxx-xxxx",
                "test notice"
        );
    }
    private ArticleDto createArticleDto() {
        return ArticleDto.of(
                createUserAccountDto(),
                1L,
                1,
                "test title",
                "test content"
        );
    }

    private ArticleDto createArticleDto(String title, String content) {
        return ArticleDto.of(
                createUserAccountDto(),
                1L,
                1,
                title,
                content
        );
    }
    private UserAccount createUserAccount() {
        return UserAccount.of(
                "bwp@test.com",
                "asdf",
                "bwp",
                "test address",
                "010-xxxx-xxxx",
                "test notice"
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                1L,
                1,
                "test title",
                "test content"
        );
    }
}