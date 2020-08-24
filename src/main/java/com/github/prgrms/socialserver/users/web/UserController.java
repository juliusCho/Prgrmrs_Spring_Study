package com.github.prgrms.socialserver.users.web;

import com.github.prgrms.socialserver.global.exceptions.NotFoundException;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.global.security.Jwt;
import com.github.prgrms.socialserver.global.security.JwtAuthenticationVO;
import com.github.prgrms.socialserver.global.types.Role;
import com.github.prgrms.socialserver.global.web.GlobalController;
import com.github.prgrms.socialserver.users.model.*;
import com.github.prgrms.socialserver.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/users")
public class UserController extends GlobalController {

    private final Jwt jwt;
    private final UserService userService;

    public UserController(Jwt jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @PostMapping(path = "exists")
    public ApiResponseDTO<Boolean> checkEmail() {
        // TODO 이메일 중복 확인 로직 구현
        // BODY 예시: {
        //	"email": "iyboklee@gmail.com"
        //}
        return new ApiResponseDTO(false, new Exception("Development In Progress"));
    }

    @GetMapping(produces = APPLICATION_JSON)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = UserModelConverter.getAggregate(userService.getAllUsers());
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "me")
    public ApiResponseDTO<UserDTO> me(@AuthenticationPrincipal JwtAuthenticationVO authentication) {
        return new ApiResponseDTO(
                true,
                UserModelConverter.convertToDTO(Optional.ofNullable(userService.findById(authentication.id))
                        .orElseThrow(() -> new NotFoundException(UserEntity.class, authentication.id)))
        );
    }

    @GetMapping(path = "connections")
    public ApiResponseDTO<List<ConnectedUserDTO>> connections(@AuthenticationPrincipal JwtAuthenticationVO authentication) {
        return new ApiResponseDTO(
                true,
                userService.findAllConnectedUser(authentication.id).stream()
                        .map(ConnectedUserDTO::new)
                        .collect(toList())
        );
    }

    @PostMapping(path = "user/join")
    public ApiResponseDTO<JoinResultDTO> join(@RequestBody JoinRequestDTO joinRequest) {
        UserEntity user = userService.join(new EmailVO(joinRequest.getPrincipal()), joinRequest.getCredentials());
        String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
        return new ApiResponseDTO(
                true,
                new JoinResultDTO(apiToken, new UserDTO(user))
        );
    }

}
