package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.Random;



// ConfigurationProperties
// 등록 떄문에 이거 추가함
@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication

public class FastCampusProjectBoardApplication {
	public FastCampusProjectBoardApplication(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository,
											 @Autowired UserAccountRepository userAccountRepository) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
		this.userAccountRepository = userAccountRepository;
		this.articleService = new ArticleService(articleRepository);
	}

	ArticleRepository articleRepository;
	ArticleCommentRepository articleCommentRepository;
	UserAccountRepository userAccountRepository;
	ArticleService articleService;
	// @EventListener(ApplicationReadyEvent.class)
	@PostConstruct
	void sdf() {
		int maxLongArticleId = 30;
		Random random = new Random();
		RandomString randomString = new RandomString();
		System.out.println("userAccountRepository.count() = " + userAccountRepository.count());
		for(int i = 0; i < maxLongArticleId; i++) {
			String userId = randomString.randomSizeString(49);
			String userPassword = randomString.randomSizeString(20);
			String  email = randomString.randomSizeString(20);
			String nickname = randomString.randomSizeString(20);
			String memo = randomString.randomSizeString(20);
			String createdAt = randomString.randomSizeString(20);
			UserAccount userAccount = UserAccount.of(userId,userPassword, email, nickname, memo);
		// 	System.out.println("userAccount = " + userAccount);
			userAccountRepository.save(userAccount);

		}


		for(int i = 0; i < maxLongArticleId; i++) {
			String title = randomString.randomSizeString(20);
			String content = randomString.randomSizeString(20);
			String hashtag = randomString.randomSizeString(20);
			UserAccount userAccount = userAccountRepository.findAll().get((int)userAccountRepository.count() - 1);
			Article article = Article.of(userAccount,title, content, hashtag);
			articleRepository.save(article);

		}
		for(int i = 0; i < maxLongArticleId; i++) {
			Long articleId = randomString.randomLongLength(maxLongArticleId);
			System.out.println("articleId = " + articleId);
			Article findArticle = articleRepository.findById(articleId).orElseThrow();
			String content2 = randomString.randomSizeString(20);
			UserAccount userAccount = userAccountRepository.findAll().get((int)userAccountRepository.count()-1);
			ArticleComment articleComment = ArticleComment.of(findArticle,content2,userAccount);
			articleCommentRepository.save(articleComment);
		}

		System.out.println("articleRepository.count() = " + articleRepository.count());
		Article article = articleRepository.findAll().get(0);
		System.out.println("article = " + article);
	}
	public static void main(String[] args) {
		SpringApplication.run(FastCampusProjectBoardApplication.class, args);

	}

}
