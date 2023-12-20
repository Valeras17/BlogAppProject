package val.gord.blogproject.service;

import val.gord.blogproject.dto.PostPageResponseDto;
import val.gord.blogproject.dto.PostRequestDto;
import val.gord.blogproject.dto.PostResponseDto;

import java.util.List;

//SOLID Principles: prefer abstraction to implements
public interface PostService {
    //create post
    PostResponseDto createPost(PostRequestDto postRequestDto);
    // get all posts
    List<PostResponseDto> getAllPosts();
    //get posts by id
    PostResponseDto getPostById(long id);
    //update by id
    PostResponseDto updatePostById(PostRequestDto dto,long id);

    //delete post
    //deletedObject throw if not exist
    //boolean deletedObject
    PostResponseDto deletePostById(long id);
//    Boolean deletePostByIdWithBoolean(long id);

    PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


}
