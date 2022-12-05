package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicIntegerFieldUpdater;

// @ActiveProfiles("testdb")
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 이거 2개랑 yml에있는 h2 base 이용해서 테스트 환경에 따라 mysql, h2 바꾸면서 할 수도 있지만,
// 일단 mysql을 쓸거라 주석함 참고할꺼면 패캠 보셈
@Disabled("비활성화")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
// @Transactional @SPringBootTest
// @Rollback 이용해서 해도 되는데, 뭐 자동롤백할꺼면 이렇게 해도 된다는 점
public class JpaRepositoryTest {
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,@Autowired ArticleCommentRepository articleCommentRepository
    ,@Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    private ArticleDto articleDto;
    @BeforeEach
    void sdf() {
        RandomString randomString = new RandomString();
        String title=randomString.randomSizeString(20);
        String content=randomString.randomSizeString(20);
        String hashtag=randomString.randomSizeString(20);

    }
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);
        //when
        Article findArticle = articleRepository.save(article);
        //then
        assertThat(findArticle.getHashtag()).isEqualTo(updateHashtag);
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        //given
        RandomString randomString = new RandomString();
        //when
        for (int i = 0; i < 123; i++) {
            String title=randomString.randomSizeString(20);
            System.out.println("title = " + title);
            String content=randomString.randomSizeString(20);
            System.out.println("content = " + content);
            String hashtag=randomString.randomSizeString(20);
            System.out.println("hashtag = " + hashtag);
//             Article article = Article.of(title,content,hashtag);
            // articleRepository.save(article);
        }
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }
    @DisplayName("select 테스트")
    @Test
    void saveData() {
        RandomString randomString = new RandomString();
        for (int i = 0; i < 100; i++) {
            String title=randomString.randomSizeString(20);
            System.out.println("title = " + title);
            String content=randomString.randomSizeString(20);
            System.out.println("content = " + content);
            String hashtag=randomString.randomSizeString(20);
            System.out.println("hashtag = " + hashtag);
            // Article article = Article.of(title,content,hashtag);
            // articleRepository.save(article);
        }



        // System.out.println("article = " + article.getCreatedAt());
        List<Article> articles = articleRepository.findAll();
        articles.stream().forEach(findArticle-> System.out.println("article = " + findArticle));
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long deletedCommentsSize = article.getArticleComments().size();
        //when
        articleRepository.delete(article);
        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentsSize);
        // article 지워서 거기에 걸려있는 댓글들 다 지워진 갯수가 맞는지 확인
    }

}
