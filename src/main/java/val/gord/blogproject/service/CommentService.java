package val.gord.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto dto);
    List<CommentResponseDto> findCommentsByPostId(long postId);

    CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto);

    CommentResponseDto deleteCommentById(long CommentID);



}
