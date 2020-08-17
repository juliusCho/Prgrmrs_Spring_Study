package com.github.prgrms.socialserver.users.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.model.UserModelConverter;
import com.github.prgrms.socialserver.users.model.UserModelConverterTest;
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


    @Test
    public void userList_existingUsers_shouldReturnJsonArray() throws Exception {
        UserDTO random = UserModelConverter.convertToDTO(UserModelConverterTest.getRandomEntity(1L));
        List<UserDTO> userList = Arrays.asList(random);

        when(userService.list()).thenReturn(userList);

        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(random.getEmail())));
    }

    @Test
    public void userDetail_existingUser_shouldReturnJson() throws Exception {
        Long seq = 1L;
        UserDTO random = UserModelConverter.convertToDTO(UserModelConverterTest.getRandomEntity(seq));

        when(userService.detail(seq)).thenReturn(random);

        mvc.perform(get("/api/users/{seq}", seq).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(random.getEmail())));
    }

    @Test
    public void userJoin_randomData_shouldReturnApiResponseJson() throws Exception {
        UserDTO random = UserModelConverter.convertToDTO(UserModelConverterTest.getRandomEntity(1L));
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(true, "SUCCEEDED");

        when(userService.join(random)).thenReturn(apiResponseDTO);

        mvc.perform(post("/api/users/join").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(random)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(apiResponseDTO.isSuccess())))
                .andExpect(jsonPath("$.response", is(apiResponseDTO.getResponse())));
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
