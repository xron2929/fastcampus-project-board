package com.fastcampus.fastcampusprojectboard.random.MvcTest;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// @Disabled("ignore 무시);
// 이렇게 하면 ignore가 되기는 한데, 안할 꺼라서 주석처리
// @WebMvcTest
// 이렇게 하면 DataRest가 똑바로 처리가 안되서 아래 2개를 추가
@AutoConfigureMockMvc
@SpringBootTest
// 이거 2개 순서바뀌면 안됨
// 그리고,스프링 부트는 기다려주고, AutoConfigure는 MockMvc 의존성 주입인듯
public class DataRestTest {
    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mockMvc) {
        this.mvc = mockMvc;
    }
    // 다른 방법으로 하면 주입이 제대로 안되서 이렇게 해줘야 됨
    // option+enter로 static import, control+space로 자동완성에 안 뜨는 앞에 뭐 있는 것들도
    // 다 뜨게 해줌 먼저 치고 안 떳을 떄는 ctrl+space 2번하면 뜨는 거 확인 O
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleCommentRepository articleCommentRepository;
    @PostConstruct
    void preInput() {
        int maxLongArticleId = 100;
        Random random = new Random();
        RandomString randomString = new RandomString();
        for(int i = 0; i < maxLongArticleId; i++) {
            String title = randomString.randomSizeString(20);
            String content = randomString.randomSizeString(20);
            String hashtag = randomString.randomSizeString(20);
            Article article = Article.of(title, content, hashtag);
            articleRepository.save(article);

        }
        for(int i = 0; i < maxLongArticleId; i++) {
            Long articleId = randomString.randomLongLength(maxLongArticleId);
            System.out.println("articleId = " + articleId);
            Article findArticle = articleRepository.findById(articleId).orElseThrow();
            String content2 = randomString.randomSizeString(20);
            ArticleComment articleComment = ArticleComment.of(findArticle,content2);
            articleCommentRepository.save(articleComment);
        }
    }
    @Test
    void test() throws Exception {
        mvc.perform(get("/api/articles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
        // application/json이 없어서 hal은 이렇게 valueOf로 직접 추가해서 확인하면 됨
    }
    @DisplayName("API 게시글 -> 댓글 리스트 조회")
    @Test
    void giveNothing_whenRequestingArticle_thenReturnArticleCommentsJsonResponse() throws Exception {
        mvc.perform(get("/api/articles/1/articleCommentsets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
    @DisplayName("API 게시글 한건 조회")
    @Test
    void giveNothing_whenRequestingArticles_thenReturnArticlesJsonResponse() throws Exception {
        mvc.perform(get("/api/articles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
    @DisplayName("조건 테스트 조회")
    @Test
    void conditionSearch() throws Exception {
        mvc.perform("/api/articles/1").andReturn();
    }

}
