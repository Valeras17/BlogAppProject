package val.gord.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;
import val.gord.blogproject.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentsController {
    private final CommentService commentService;
    //http://localhost:8080/api/v1/posts/4/comments
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable(name = "id") long postId,
            @RequestBody CommentRequestDto dto,
            UriComponentsBuilder uriBuilder
    ) {
        var saved = commentService.createComment(postId, dto);
        var uri =
                uriBuilder
                        .path("/api/v1/posts/{post_id}/{comment_id}")
                        .buildAndExpand(postId, saved.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    //findCommentByPostId // GET (long postId)
    //http://localhost:8080/api/v1/posts/4/comments
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentsByPostId(@PathVariable("id") long postId){
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    //PUT http://localhost:8080/api/v1/comments/1
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateCommentById(
            @PathVariable("id") long commetId,
            @RequestBody CommentUpdateRequestDto dto
            ){
        return ResponseEntity.ok(commentService.updateComment(commetId, dto));
    }

    @DeleteMapping ("/comments/{id}")
    public ResponseEntity<CommentResponseDto> deleteCommentById(@PathVariable long id){
        return  ResponseEntity.ok(commentService.deleteCommentById(id));

    }
}
