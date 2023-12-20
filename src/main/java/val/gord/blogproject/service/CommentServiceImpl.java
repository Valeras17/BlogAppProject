package val.gord.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;
import val.gord.blogproject.entity.Comment;
import val.gord.blogproject.error.ResourceNotFoundException;
import val.gord.blogproject.repository.CommentRepository;
import val.gord.blogproject.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    //props:
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Override
    public CommentResponseDto createComment(long postId, CommentRequestDto dto) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", postId)
        );
        var comment = modelMapper.map(dto, Comment.class);
        comment.setPost(post);
        var saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentResponseDto.class);
    }

    @Override
    public List<CommentResponseDto> findCommentsByPostId(long postId) {
        //CHECK that the poast exist:
        if (!postRepository.existsById(postId)){
            throw new ResourceNotFoundException("post", postId);
        }
        //return the comments for the post
        return  commentRepository
                .findCommentsByPostId(postId)
                .stream()
                .map(
                c->modelMapper.map(c,CommentResponseDto.class))
                .toList();
    }

    @Override
    public CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto) {
        //var fromDb = fetch commet from database with commentId=>orElseThrow
        var commentFromDb =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(()-> new ResourceNotFoundException("comment",commentId));
        //copy fields to update:
        //fromDb.title = dto.title(copy)
        commentFromDb.setComment(dto.getComment());
        //....body
        //save fromDb again...
        var saved = commentRepository.save(commentFromDb);
        //return a dto
        return modelMapper.map(saved,CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto deleteCommentById(long commentId) {
        //var saved = find the comment -> orElseThrow
        var saved =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(()->new ResourceNotFoundException("Comment",commentId));
        //delte by id
        commentRepository.deleteById(commentId);
        //return the deleted post as response
        return modelMapper.map(saved,CommentResponseDto.class);
    }
}

