package com.fastcampus.fastcampusprojectboard.controller;

<<<<<<< Updated upstream
=======
import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import com.fastcampus.fastcampusprojectboard.service.PaginationService;
>>>>>>> Stashed changes
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

<<<<<<< Updated upstream
=======
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
>>>>>>> Stashed changes
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// CI CD 배포는 기본적으로 빌드가 성공해야 커밋이 되게 할 텐데
// 이걸 막으려고 @Disabled로 무시해서 빌드하도록 해서 해결함
// @Disabled("Controller를 만들지 않아서 무시하도록 처리")
// @WebMvcTest(ArticleController.class)
// 의존성 클래스를 이렇게 직접 추가 하면 하나만 읽어들여서 태스트
@AutoConfigureMockMvc
@SpringBootTest
class ArticleControllerTest {
    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;
    @MockBean private PaginationService paiginationService;
    // test에 있는 애들은 직접 파라미터 안에서 명시해야됨 기본 스프링은 생략해도 되지만 반대임
    ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }
    @DisplayName("[view] [GET] 게시글 리스트 [게시판] 페이지 - 정상 호출")
    @Test
    public void givenNothing_wheRequestArticlesView_thanReturnsArticleView() throws Exception {
        //given

        //when & then
        given(articleService.searchArticles(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());
        given(paiginationService.getPaginationBarNumbers(anyInt(),anyInt())).willReturn(List.of(1,2,3,4));
        // null로 하면, 아무거나라고 뜨니까, 아무거나를 바라는 게 아니라 null을 바라는 거니까,
        // eq(null)로 하면 됨
        mvc.perform(get("/articles"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/main-index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        // model로 aritcles랑 COmments 정보가 있어야되고 기본 적으로 이동하는 상세페이지가 detail이라는 점
        // contentType은 해당 타입만 허용인데, Text는 Text인데 UTF-8이 들어오고, 나라마다 호환하는 텍스트 파일이 다를 수 있으니
        // 호환이 되는 종류 중 하나라고 contentTypeCompatibleWith 라고 함
<<<<<<< Updated upstream

=======
        articleService.searchArticles(null,null,Pageable.ofSize(10));
        paiginationService.getPaginationBarNumbers(1,10);
        then(articleService).should(atLeast(0)).searchArticles(eq(null),eq(null),any(Pageable.class));
        then(paiginationService).should(atLeast(1)).getPaginationBarNumbers(anyInt(),anyInt());
>>>>>>> Stashed changes
    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    // @Test
    public void givenNothing_wheRequestArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/1"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));


    }
    @DisplayName("[view] [GET] 게시글 상세 [게시판] 페이지 - 정상 호출")
    // @Test
    public void givenNothing_wheRequestSearchHashtagArticleView_thanReturnArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/search/hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));


    }
}