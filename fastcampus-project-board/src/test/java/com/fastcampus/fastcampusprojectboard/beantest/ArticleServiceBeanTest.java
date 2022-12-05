package com.fastcampus.fastcampusprojectboard.beantest;

import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleServiceBeanTest {
    @Autowired ArticleService articleService;
    @Test
    public void beans(){
        System.out.println("articleService = " + articleService);
    }
}
