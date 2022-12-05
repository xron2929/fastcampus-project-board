package com.fastcampus.fastcampusprojectboard.random.MvcTest;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    UserAccountRepository userAccountRepository;
    @PostConstruct
    void preInput() {
        int maxLongArticleId = 100;
        Random random = new Random();
        RandomString randomString = new RandomString();
        for(int i = 0; i < maxLongArticleId; i++) {
            String userId = randomString.randomSizeString(20);
            String userPassword = randomString.randomSizeString(20);
            String  email = randomString.randomSizeString(20);
            String nickname = randomString.randomSizeString(20);
            String memo = randomString.randomSizeString(20);
            String createdAt = randomString.randomSizeString(20);
            UserAccount userAccount = UserAccount.of(userId,userPassword, email, nickname, memo);
            userAccountRepository.save(userAccount);
            LocalDateTime createdAt1 = userAccountRepository.findAll().get(0).getCreatedAt();
            System.out.println("createdAt1 = " + createdAt1);
        }

        for(int i = 0; i < maxLongArticleId; i++) {
            String title = randomString.randomSizeString(20);
            String content = randomString.randomSizeString(20);
            String hashtag = randomString.randomSizeString(20);
            UserAccount userAccount = userAccountRepository.findAll().get(userAccountRepository.findAll().size() - 1);
            Article article = Article.of(userAccount,title, content, hashtag);
            articleRepository.save(article);

        }
        for(int i = 0; i < maxLongArticleId; i++) {
            Long articleId = randomString.randomLongLength(maxLongArticleId);
            System.out.println("articleId = " + articleId);
            Article findArticle = articleRepository.findById(articleId).orElseThrow();
            String content2 = randomString.randomSizeString(20);
            UserAccount userAccount = userAccountRepository.findAll().get(userAccountRepository.findAll().size() - 1);
             ArticleComment articleComment = ArticleComment.of(findArticle,content2,userAccount);
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
    @DisplayName("json 테스트 조회")
    @Test
    void findJson() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/articles/1")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println("contentAsString = " + contentAsString);
        JSONObject jsonObject = new JSONObject(contentAsString);
        Object title = jsonObject.get("title");
        System.out.println("title = " + (String)title);
        // title을 보면 JsonObject로 title을 꺼낼 수 있고, 상황에 따라 원하는 처리 O
    }
    @DisplayName("API HAL 테스트 조회")
    @Test
    void conditionSearch() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/articles/1")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        JSONObject jsonObject = new JSONObject(contentAsString);
        Object title = jsonObject.get("title");
        String findContentAsString = mvc.perform(get("/api/articles?title=" + (String) title)).andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + contentAsString);
        JSONObject jsonObject2 = new JSONObject(findContentAsString);
        Object title1 = jsonObject2.getJSONObject("_embedded").get("articles");
        System.out.println("title1 = " + title1);
        JSONArray jsonArray = jsonObject2.getJSONObject("_embedded").getJSONArray("articles");
        String title2 = jsonArray.getString(0);
        JSONObject jsonObject3 = new JSONObject(title2);
        Object title3 = jsonObject3.get("title");
        System.out.println("title = " + title3);
        Assertions.assertThat(title3).isEqualTo(title);
    }
    // 대충 이렇게 다 가져오려면 힘들긴한데 가져는 올 수 있다는 점



}
