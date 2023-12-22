package val.gord.blogproject.service;

import org.springframework.security.core.Authentication;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto dto, Authentication authentication);
    List<CommentResponseDto> findCommentsByPostId(long postId);
    CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto, Authentication authentication);
    CommentResponseDto deleteCommentById(long CommentID, Authentication authentication);



}
