package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 게시글")
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
// @Autowired 스프링부트 테스트 주입 안되면 이러헥 Import 쓰는 것도 하나의 방법이라는 점
public class AuthControllerTest {
    private final MockMvc mvc;

    // test에 있는 애들은 직접 파라미터 안에서 명시해야됨 기본 스프링은 생략해도 되지만 반대임
    AuthControllerTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }
    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenTryingToLogIn_thanReturnsLogInView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

    }

}
