package com.fastcampus.fastcampusprojectboard.controller;

import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
// @Import({MainController.class, SecurityConfig.class})
class MainControllerTest {
    private final MockMvc mvc;

    public MainControllerTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성")
    @Test
    void giveNothing_whereRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
        // 3oo번대 리다이렉트
    }
}