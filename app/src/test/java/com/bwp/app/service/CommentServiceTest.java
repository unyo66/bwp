package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.CommentDto;
import com.bwp.app.dto.CommentRequest;
import com.bwp.app.dto.UserAccountDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.CommentRepository;
import com.bwp.app.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService sut;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;

    @DisplayName("유저에 따라 댓글 조회")
    @Test
    void commentsByUserId() {
        // Given
        Comment expected = createComment();
        Long userId = 1L;
        given(commentRepository.findByUserAccount_Id(userId)).willReturn(List.of(expected));
        // When
        List<CommentDto> actual = sut.commentsByUserId(userId);
        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(commentRepository).should().findByUserAccount_Id(userId);
    }

    @DisplayName("댓글 생성")
    @Test
    void saveComment() {
        // Given
        CommentRequest commentRequest = createCommentRequest();
        UserAccountDto userAccountDto = createUserAccountDto();
        given(articleRepository.getReferenceById(commentRequest.articleId())).willReturn(createArticle());
        given(commentRepository.save(any(Comment.class))).willReturn(null);
        // When
//        sut.saveComment(commentRequest, userAccountDto);
        // Then
        then(articleRepository).should().getReferenceById(commentRequest.articleId());
        then(commentRepository).should().save(any(Comment.class));
    }

    @DisplayName("댓글 수정")
    @Test
    void updateComment() {
        // Given
        String oldContent = "old content";
        String updateContent = "new content";
        Comment comment = createComment(oldContent);
        CommentDto commentDto = createCommentDto(updateContent);
        given(commentRepository.getReferenceById(commentDto.id())).willReturn(comment);
        // When
        sut.updateComment(commentDto);
        // Then
        assertThat(comment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updateContent);
        then(commentRepository).should().getReferenceById(commentDto.id());
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() {
        // Given
        Long commentId = 1L;
        Long userId = 1L;
        willDoNothing().given(commentRepository).deleteByIdAndUserAccount_Id(commentId, userId);
        // When
        sut.deleteComment(commentId, userId);
        // Then
        then(commentRepository).should().deleteByIdAndUserAccount_Id(commentId, userId);
    }


    /////

    private CommentRequest createCommentRequest() {
        return CommentRequest.of(
                1L,
                1L,
                1,
                1,
                "content"
        );
    }

    private CommentDto createCommentDto() {
        return CommentDto.of(
                1L,
                createUserAccountDto(),
                1L,
                1,
                1,
                "test content"
        );
    }

    private CommentDto createCommentDto(String content) {
        return CommentDto.of(
                1L,
                createUserAccountDto(),
                1L,
                1,
                1,
                content
        );
    }
    private Comment createComment() {
        return Comment.of(
                createUserAccount(),
                createArticle(),
                1L,
                1,
                1,
                "test content"
        );
    }
    private Comment createComment(String content) {
        return Comment.of(
                createUserAccount(),
                createArticle(),
                1L,
                1,
                1,
                content
        );
    }
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