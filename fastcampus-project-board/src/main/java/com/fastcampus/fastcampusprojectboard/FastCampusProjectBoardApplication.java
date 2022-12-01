package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.random.RandomString;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
<<<<<<< HEAD
import org.springframework.context.event.EventListener;
=======
>>>>>>> main
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.Random;

@EnableJpaAuditing
@ConfigurationPropertiesScan
// ConfigurationProperties
// 등록 떄문에 이거 추가함
@SpringBootApplication
public class FastCampusProjectBoardApplication {
	public FastCampusProjectBoardApplication(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}

	ArticleRepository articleRepository;
	ArticleCommentRepository articleCommentRepository;
	// @EventListener(ApplicationReadyEvent.class)
	@PostConstruct
	void sdf() {
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
	public static void main(String[] args) {
		SpringApplication.run(FastCampusProjectBoardApplication.class, args);

	}

}
