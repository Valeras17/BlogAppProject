package val.gord.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;
import val.gord.blogproject.entity.Comment;
import val.gord.blogproject.error.BadRequestException;
import val.gord.blogproject.error.ResourceNotFoundException;
import val.gord.blogproject.repository.CommentRepository;
import val.gord.blogproject.repository.PostRepository;
import val.gord.blogproject.repository.RoleRepository;
import val.gord.blogproject.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    //props:
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    @Override
    public CommentResponseDto createComment(long postId, CommentRequestDto dto, Authentication authentication) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", postId)
        );
        var user = userRepository.findByUsernameIgnoreCase(authentication.getName()).orElseThrow();
        Comment comment = modelMapper.map(dto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
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
    @Transactional//keep the connection open since we are running multiple queries
    public CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto, Authentication authentication) {
        //var fromDb = fetch commet from database with commentId=>orElseThrow
        var commentFromDb =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(()-> new ResourceNotFoundException("comment",commentId));

        userHasPermissionToEditComment(authentication, commentFromDb);

        //copy fields to update:
        //fromDb.title = dto.title(copy)
        commentFromDb.setComment(dto.getComment());
        //....body
        //save fromDb again...
        var saved = commentRepository.save(commentFromDb);
        //return a dto
        return modelMapper.map(saved,CommentResponseDto.class);
    }

    private void userHasPermissionToEditComment(Authentication authentication, Comment commentFromDb) {
        //the user from the database comment:
        var user = commentFromDb.getUser();

        //get the admin role from database:
        var adminRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN").orElseThrow();

        //check if  the user role is admin:
        var isAdmin = user.getRoles().contains(adminRole);
        //check if the user owns the comment:
        var userOwnsComment = user.getUsername().equalsIgnoreCase(authentication.getName());

        if (!isAdmin && !userOwnsComment){
            throw new BadRequestException("user","Comment must be belong the editing user");
        }
    }

    @Override
    @Transactional//keep the connection open since we are running multiple queries
    public CommentResponseDto deleteCommentById(long commentId, Authentication authentication) {
        //var saved = find the comment -> orElseThrow
        var saved =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(()->new ResourceNotFoundException("Comment",commentId));
        //validation: userHasPermissionToEditComment
        userHasPermissionToEditComment(authentication,saved);
        //delte by id
        commentRepository.deleteById(commentId);
        //return the deleted post as response
        return modelMapper.map(saved,CommentResponseDto.class);
    }
}

