package com.fastcampus.fastcampusprojectboard.type;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

// @TestConfiguration
// 이거 등록하고 @Import COnfig파일하고
// Config 파일에 ArticleService articleService; 해도 도기는 할 텐데
// 아니면 @ComponentScan에 추가해줘도 되고
// @SpringBootTest
@DisplayName("비즈니스 로직 - 게시글 ")

// @ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@Import(JpaConfig.class)
@DataJpaTest
class ArticleServiceTest {




    // @Autowired ArticleRepository articleRepository;
    ArticleCommentRepository articleCommentRepository;
    UserAccountRepository userAccountRepository;
    ArticleRepository articleRepository;
    ArticleService sut;
    public ArticleServiceTest(@Autowired ArticleCommentRepository articleCommentRepository,
                              @Autowired UserAccountRepository userAccountRepository,
                              @Autowired ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
        this.articleRepository = articleRepository;
        this.sut = new ArticleService(articleRepository);
    }
    UserAccount userAccount;
    private ArticleDto articleDto;
    @BeforeEach
    void before() {
            sut = new ArticleService(articleRepository);
            int maxLongArticleId = 30;
            Random random = new Random();
            RandomString randomString = new RandomString();

            System.out.println("userAccountRepository.count() = " + userAccountRepository.count());
            for(int i = 0; i < maxLongArticleId; i++) {
                String userId = randomString.randomSizeString(49);
                String userPassword = randomString.randomSizeString(20);
                String email = randomString.randomSizeString(40);
                String nickname = randomString.randomSizeString(40);
                String memo = randomString.randomSizeString(40);
                String createdAt = randomString.randomSizeString(40);
                System.out.println("email = " + email);
                System.out.println("memo = " + memo);
                userAccount = UserAccount.of(userId,userPassword, email, nickname, memo);
                // 	System.out.println("userAccount = " + userAccount);
                userAccountRepository.save(userAccount);

            }
        System.out.println("??Dfs");
            for(int i = 0; i < maxLongArticleId; i++) {
                String title = randomString.randomSizeString(40);
                String content = randomString.randomSizeString(40);
                String hashtag = randomString.randomSizeString(40);
                System.out.println(" asdfgh ");
                Article article = Article.of(userAccount,title, content, hashtag);
                articleRepository.save(article);

            }
            for(int i = 0; i < maxLongArticleId; i++) {
                Long articleId = randomString.randomLongLength(maxLongArticleId);
                System.out.println("articleId = " + articleId);
                Article findArticle = articleRepository.findById(articleId).orElseThrow();
                String content2 = randomString.randomSizeString(20);
                ArticleComment articleComment = ArticleComment.of(findArticle,content2,userAccount);
                articleCommentRepository.save(articleComment);
            }

            System.out.println("articleRepository.count() = " + articleRepository.count());
            Article article = articleRepository.findAll().get(0);
            System.out.println("article = " + article);
            articleDto = ArticleDto.of(article);
        System.out.println("article = " + article);
        }
    @Test
    void sdfd () {
        long count = userAccountRepository.count();
        System.out.println("count = " + count);

    }
    @DisplayName("게시글 검색하면, 게시글 리스트를 반환")
    @Test
    void givenSearchParameters_whenSerachingArticles_thenReturnArticleList() {

            // Given
            Long articleId = 1L;
            Article article = articleRepository.findById(articleId).orElse(null);

            // When
            ArticleWithCommentsDto dto = sut.getArticle(articleId);
            // Then

            assertThat(dto)
                    .hasFieldOrPropertyWithValue("title", article.getTitle())
                    .hasFieldOrPropertyWithValue("content", article.getContent())
                    .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());

    }

        // 스프링 부트 테스트는 기존에 있는 데이터가 날라가고
    // 일반 데스트는 새로 업로딩 해야된다는 점
    // 그리고 서비스안에 repository 의존성이 있는 것 같은 경우는
    // 둘 중 하나를 가져와서 다른 하나에 의존성 주입으로 해야된다는 점
    private UserAccount createUserAccount(UserAccountDto userAccountDto) {
        return UserAccount.of(
                userAccountDto.userId(),
                userAccountDto.userPassword(),
                userAccountDto.email(),
                userAccountDto.nickname(),
                userAccountDto.memo()
        );
    }
    private Article createArticle () {
        return Article.of(
                createUserAccount(articleDto.userAccountDto()),
                articleDto.title(),
                articleDto.content(),
                articleDto.hashtag()
        );
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
    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() {
        System.out.println("articleDto = " + articleDto);
        System.out.println("articleDto = " + articleDto.userAccountDto());
        // Given
        UserAccount userAccount = createUserAccount(articleDto.userAccountDto());
        System.out.println("fefwe");
        Article article = createArticle();

        ArticleDto dto = createArticleDto("title", "content", "hashtag");
        // given(articleRepository.getReferenceById(dto.id())).willReturn(article);
        // When
        sut.updateArticle(dto);

        // Then
        //Article article1 = articleRepository.findAll().get(0);
        Article article1 = articleRepository.findById(1L).orElseThrow();
        System.out.println("article1 = " + article1);
        assertThat(article1)
                .hasFieldOrPropertyWithValue("title", dto.title())
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
        // then(articleRepository).should().getReferenceById(dto.id());


    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(articleDto.id(),articleDto.userAccountDto(),title,content,hashtag,articleDto.createdAt(),articleDto.createdBy(),articleDto.modifiedAt(),articleDto.modifiedBy());
    }


}