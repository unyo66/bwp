package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.Comment;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.CommentDto;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.CommentRepository;
import com.bwp.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    //TODO: 댓글로 페이징 하려면 리턴값 바꾸기 (현재는 List)

    /** 특정 유저의 댓글 보기 */
    public List<CommentDto> commentsByUserId(Long userId) {
        return commentRepository.findByUserAccount_Id(userId).stream().map(CommentDto::from).toList();
    }

    /** 댓글 저장 */
    public void saveComment(CommentDto commentDto) {
        try {
            Article article = articleRepository.getReferenceById(commentDto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(commentDto.userAccountDto().id());
            commentRepository.save(commentDto.toEntity(userAccount, article));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패");
        }
    }

    /** 댓글 수정 */
    // 사실 인증 없어도 본인이 단 댓글에만 수정 삭제 버튼이 생기기 때문에 상관없음.
    public void updateComment(CommentDto commentDto) {
        try {
            Comment comment = commentRepository.getReferenceById(commentDto.id());
            if (commentDto.content() != null) {
                comment.setContent(commentDto.content());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글이 없습니다.");
        }
    }
    /** 댓글 삭제 */
    @Transactional(readOnly = true)
    public void deleteComment(Long commentId, Long userId) {
        commentRepository.deleteByIdAndUserAccount_Id(commentId, userId);
    }
}
