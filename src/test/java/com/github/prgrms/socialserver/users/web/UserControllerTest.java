package com.github.prgrms.socialserver.users.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.*;
import com.github.prgrms.socialserver.users.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserModelConverter userModelConverter = new UserModelConverter();


    @Test
    public void userList_existingUsers_shouldReturnJsonArray() throws Exception {
        UserEntity random = UserModelConverterTest.getRandomEntity(1L);
        UserDTO result = userModelConverter.convertToDTO(random);
        List<UserEntity> userList = Arrays.asList(random);

        when(userService.getAllUsers()).thenReturn(userList);

        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(result.getEmail())));
    }

    @Test
    public void userDetail_existingUser_shouldReturnJson() throws Exception {
        Long seq = 1L;
        UserEntity random = UserModelConverterTest.getRandomEntity(seq);
        UserDTO result = userModelConverter.convertToDTO(random);

        when(userService.findById(IdVO.of(UserEntity.class, seq))).thenReturn(random);

        mvc.perform(get("/api/users/{seq}", seq).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(result.getEmail())));
    }

    @Test
    public void userJoin_randomData_shouldReturnApiResponseJson() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("principal", "user1@prgrmrs6.com");
        map.put("credentials", "1");
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(true, "1");

        when(userService.join(new EmailVO("user1@prgrmrs6.com"), "1")).thenReturn(new UserEntity.Builder("user1@prgrmrs6.com", "1").build());

        mvc.perform(post("/api/users/join").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(apiResponseDTO.isSuccess())));
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            StackTraceElement[] ste = e.getStackTrace();
            log.error(String.valueOf(ste[ste.length - 1]));
            return null;
        }
    }

}
