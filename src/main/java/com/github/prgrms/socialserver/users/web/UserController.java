package com.github.prgrms.socialserver.users.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    protected static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    @Autowired
    private UserService userService;


    @GetMapping(produces = APPLICATION_JSON)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping(value = "{seq}", produces = APPLICATION_JSON)
    public ResponseEntity<?> detail(@PathVariable Long seq) {
        return ResponseEntity.ok(userService.detail(seq));
    }

    @PostMapping(value = "join", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<ApiResponseDTO> join(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.join(dto));
    }

}
