package com.github.prgrms.socialserver.users.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.model.UserModelConverter;
import com.github.prgrms.socialserver.users.service.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Qualifier("messageSource")
    @Autowired
    MessageSource msg;

    protected static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    @Autowired
    private UserService userService;


    @GetMapping(produces = APPLICATION_JSON)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = UserModelConverter.getAggregate(userService.getAllUsers());
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{seq}", produces = APPLICATION_JSON)
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable Long seq) {
        UserDTO result = UserModelConverter.convertToDTO(userService.getUserDetail(seq));
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "join", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<ApiResponseDTO> insertUser(@RequestBody String input) throws JSONException {
        int result = userService.insertUser(input);
        return ResponseEntity.ok(new ApiResponseDTO(true, msg.getMessage("api.users.msg.inserted", null, Locale.KOREA)));
    }

}
