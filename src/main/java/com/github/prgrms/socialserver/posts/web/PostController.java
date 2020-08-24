package com.github.prgrms.socialserver.posts.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.global.security.JwtAuthenticationVO;
import com.github.prgrms.socialserver.posts.model.PostDTO;
import com.github.prgrms.socialserver.posts.model.PostingRequestVO;
import com.github.prgrms.socialserver.posts.model.WriterVO;
import com.github.prgrms.socialserver.posts.service.PostService;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "post")
    public ApiResponseDTO<PostDTO> posting(
            @AuthenticationPrincipal JwtAuthenticationVO authentication,
            @RequestBody PostingRequestVO request
    ) {
        return new ApiResponseDTO(
                true,
                new PostDTO(
                        postService.write(
                                request.newPost(authentication.id, new WriterVO(authentication.email))
                        )
                )
        );
    }

    @GetMapping(path = "user/{userId}/post/list")
    public ApiResponseDTO<List<PostDTO>> posts(@PathVariable Long userId) {
        return new ApiResponseDTO(
                true,
                postService.findAll(IdVO.of(UserEntity.class, userId)).stream()
                        .map(PostDTO::new)
                        .collect(toList())
        );
    }

}