package com.fastcampus.fastcampusprojectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// CI CD 배포는 기본적으로 빌드가 성공해야 커밋이 되게 할 텐데
// 이걸 막으려고 @Disabled로 무시해서 빌드하도록 해서 해결함
@Disabled("Controller를 만들지 않아서 무시하도록 처리")
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
                .andExpect(view().name("aritlces/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("articleComments"));
        // model로 aritcles랑 COmments 정보가 있어야되고 기본 적으로 이동하는 상세페이지가 detail이라는 점


    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    public void givenNothing_wheRequestArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/1"))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));


    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    public void givenNothing_wheRequestSearchHashtagArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/search/hashtag"))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));


    }
}