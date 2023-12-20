package val.gord.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import val.gord.blogproject.dto.CommentRequestDto;
import val.gord.blogproject.dto.CommentResponseDto;
import val.gord.blogproject.dto.CommentUpdateRequestDto;
import val.gord.blogproject.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docs")
public class DocsController {
    @GetMapping
    ResponseEntity<Object> getDocs(){
        return ResponseEntity.ok(Map.of("message","docs"));
    }



}
