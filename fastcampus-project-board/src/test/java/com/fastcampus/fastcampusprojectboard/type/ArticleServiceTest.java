package com.fastcampus.fastcampusprojectboard.type;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
// @TestConfiguration
// 이거 등록하고 @Import COnfig파일하고
// Config 파일에 ArticleService articleService; 해도 도기는 할 텐데
// 아니면 @ComponentScan에 추가해줘도 되고
// @SpringBootTest
@DisplayName("비즈니스 로직 - 게시글 ")
@ExtendWith({MockitoExtension.class})
@SpringBootTest
class ArticleServiceTest {
    @Autowired ArticleRepository articleRepository;
    @Autowired private ArticleService sut;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    @BeforeEach
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
            System.out.println("articleRepository.count() = " + articleRepository.count());
            Article findArticle = articleRepository.findById(articleId).orElseThrow();
            String content2 = randomString.randomSizeString(20);
            ArticleComment articleComment = ArticleComment.of(findArticle,content2);
            articleCommentRepository.save(articleComment);
            System.out.println("articleRepository.count() = " + articleRepository.count());
        }
    }
    @DisplayName("게시글 검색하면, 게시글 리스트를 반환")
    @Test
    void givenSearchParameters_whenSerachingArticles_thenReturnArticleList() {

        //given
        //when
        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE,"search keyword");
        //then
        assertThat(articles).isNotNull();
    }
    @DisplayName("게시글 검색하면, 게시글을 반환")
    @Test
    void givenSearchParameters_whenSerachingArticles_thenReturnArticleList3() {

        //given
        //when
        ArticleDto articles = sut.searchArticle(1L);
        //then
        assertThat(articles).isNotNull();
    }
    @DisplayName("게시글 검색하면, 게시글 리스트를 반환")
    @Test
    void givenArticleId_whenSerachingArticle_thenReturnArticle() {

        //given
        //when
        ArticleDto articles = sut.searchArticle(1L);
        //then
        assertThat(articles).isNotNull();
    }

}