package com.bwp.app.controller;

import com.bwp.app.domain.Comment;
import com.bwp.app.dto.CommentDto;
import com.bwp.app.dto.CommentRequest;
import com.bwp.app.dto.CommentResponse;
import com.bwp.app.dto.ItemDto;
import com.bwp.app.repository.CommentRepository;
import com.bwp.app.serucity.BoardPrincipal;
import com.bwp.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    @PostMapping("/create")
    public String createComment(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                                CommentRequest commentRequest) {
        System.out.println(commentRequest);
        List<Comment> comments = commentRepository.findByArticle_IdOrderByCommentOrder(commentRequest.articleId());
        for (int i = 0; i < comments.size(); i++) {
            int commentOrder = comments.get(i).getCommentOrder();
            if (commentOrder >= commentRequest.commentOrder()) {
                comments.get(i).setCommentOrder(commentOrder + 1);
            }
        }
        commentService.saveComment(commentRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles/detail/" + commentRequest.articleId();
    }

    @ResponseBody
    @PostMapping("/getParent")
    public CommentResponse getCompanySendItems(@RequestBody Long commentId) {
        CommentResponse commentResponse = commentRepository.findById(commentId).map(CommentResponse::from).orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        return commentResponse;
    }

    @PostMapping ("/{articleId}/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        commentService.deleteComment(commentId, boardPrincipal.userId());
        return "redirect:/articles/detail/" + articleId;
    }
}
