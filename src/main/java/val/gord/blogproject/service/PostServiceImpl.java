package val.gord.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import val.gord.blogproject.dto.PostPageResponseDto;
import val.gord.blogproject.dto.PostRequestDto;
import val.gord.blogproject.dto.PostResponseDto;
import val.gord.blogproject.dto.PostWithCommentsDto;
import val.gord.blogproject.entity.Post;
import val.gord.blogproject.error.ResourceNotFoundException;
import val.gord.blogproject.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        //convert requestDTO to Entity
        var entity = modelMapper.map(postRequestDto, Post.class);
        //save the entity
        var saved = postRepository.save(entity);
        //convert saved entity to ResponseDto
        return modelMapper.map(saved, PostResponseDto.class);
    }
    @Override
    public List<PostResponseDto> getAllPosts() {

        return postRepository
                .findAll()
                .stream()
                .map(p->modelMapper.map(p,PostResponseDto.class))
                .toList();//List<Post>-->List<PostDto>
    }

    @Override
    public PostResponseDto getPostById(long id) {
        return modelMapper.map(getPostEntity(id),PostResponseDto.class);
    }

// helper method-dont copy past ->reuse
    private Post getPostEntity(long id) {
        //check if optional havs a value -> if not exists -> throw an exception
        return postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", id)
        );
    }
//update by id
    @Override
    public PostResponseDto updatePostById(PostRequestDto dto, long id) {
        Post postFromDb = getPostEntity(id);
        //update =>copy new props from dto,save
        postFromDb.setContent(dto.getContent()); //assert that id exists
        postFromDb.setTitle(dto.getTitle());
        postFromDb.setDescription(dto.getDescription());
        //save
        var saved = postRepository.save(postFromDb);
        //return response dto
        return modelMapper.map(saved,PostResponseDto.class);
    }
// delete by id
    @Override
    public PostResponseDto deletePostById(long id) {
        //if the post does not exist throw exception -> 404 resource not found
        Post post = getPostEntity(id);
        postRepository.deleteById(id);
        return modelMapper.map(post, PostResponseDto.class);
    }

//second way do the same ,by boolean
//    @Override
//    public Boolean deletePostByIdWithBoolean(long id) {
//        var exists = postRepository.existsById(id);
//        postRepository.deleteById(id);
//        return exists;
//    }


    @Override
    public PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //pageable import org.springframework.data.domain.Pageable;
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.fromString(sortDir),sortBy);
        Page<Post> page =  postRepository.findAll(pageable);

        return PostPageResponseDto.builder()
                .results(page.stream().map(post -> modelMapper.map(post, PostWithCommentsDto.class)).toList())
                .pageSize(page.getSize())
                .pageNo(page.getNumber())
                .totalPosts(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .build();

    }
}
