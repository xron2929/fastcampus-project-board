package com.fastcampus.fastcampusprojectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ArticleController.class)
// 의존성 클래스를 이렇게 직접 추가 하면 하나만 읽어들여서 태스트 함
class ArticleControllerTest {
    private final MockMvc mvc;

    // test에 있는 애들은 직접 파라미터 안에서 명시해야됨 기본 스프링은 생략해도 되지만 반대임
    ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }
    @DisplayName("[view] [GET] 게시글 리스트 [게시판] 페이지 - 정상 호출")
    public void givenNothing_wheRequestArticlesView_thanReturnsArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles"))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));


    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    public void givenNothing_wheRequestArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/1"))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article"));


    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    public void givenNothing_wheRequestSearchHashtagArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/search/hashtag"))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article"));


    }
}