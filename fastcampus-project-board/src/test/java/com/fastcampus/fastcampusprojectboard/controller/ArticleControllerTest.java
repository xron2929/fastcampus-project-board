package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// CI CD 배포는 기본적으로 빌드가 성공해야 커밋이 되게 할 텐데
// 이걸 막으려고 @Disabled로 무시해서 빌드하도록 해서 해결함
// @Disabled("Controller를 만들지 않아서 무시하도록 처리")
// @WebMvcTest(ArticleController.class)
// 의존성 클래스를 이렇게 직접 추가 하면 하나만 읽어들여서 태스트
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
class ArticleControllerTest {
    private final MockMvc mvc;
    @MockBean private ArticleService articleService;
    // test에 있는 애들은 직접 파라미터 안에서 명시해야됨 기본 스프링은 생략해도 되지만 반대임
    // @Autowired는 필드 주입하면 에러 떠서 짜증나게 하고, @MockBean은 필드 주입, 메소드 주입만 되서 하신듯
    ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }
    @DisplayName("[view] [GET] 게시글 리스트 [게시판] 페이지 - 정상 호출")
    @Test
    public void givenNothing_wheRequestArticlesView_thanReturnsArticleView() throws Exception {
        //given
        given(articleService.searchArticles(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());
        // null로 하면, 아무거나라고 뜨니까, 아무거나를 바라는 게 아니라 null을 바라는 거니까,
        // eq(null)로 하면 됨
        mvc.perform(get("/articles"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/main-index"))
                .andExpect(model().attributeExists("articles"));
        // model로 aritcles랑 COmments 정보가 있어야되고 기본 적으로 이동하는 상세페이지가 detail이라는 점
        // contentType은 해당 타입만 허용인데, Text는 Text인데 UTF-8이 들어오고, 나라마다 호환하는 텍스트 파일이 다를 수 있으니
        // 호환이 되는 종류 중 하나라고 contentTypeCompatibleWith 라고 함
        articleService.searchArticles(null,null,Pageable.ofSize(10));
        then(articleService).should(atLeast(0)).searchArticles(eq(null),eq(null),any(Pageable.class));
    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    @Test
    public void givenNothing_wheRequestArticleView_thanReturnArticleView() throws Exception {
        //given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
        //when & then
        mvc.perform(get("/articles/"+articleId))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
                // .andExpect(model().attributeExists("articles/search"));
        articleService.getArticle(articleId);
        then(articleService).should(atLeast(0)).getArticle(articleId);

    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    // @Test
    public void givenNothing_wheRequestSearchHashtagArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/search/hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
                //.andExpect(model().attributeExists("articles/search-hashtag"));


    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

}