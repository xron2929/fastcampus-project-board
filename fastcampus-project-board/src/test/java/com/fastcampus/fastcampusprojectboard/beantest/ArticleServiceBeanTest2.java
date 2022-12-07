package com.fastcampus.fastcampusprojectboard.beantest;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

@SpringBootTest
public class ArticleServiceBeanTest2 {

    @Autowired ArticleRepository articleRepository;
    ArticleService articleService;
    @BeforeEach
    void preInput() {
        articleService = new ArticleService(articleRepository);
        System.out.println("articleService = " + articleService);
        System.out.println("???dfs ");
    }
    @Test
    public void beans(){
        System.out.println("articleService = " + articleService);
        Article article = articleRepository.findAll().get(0);
        System.out.println("article = " + article);
    }
}
