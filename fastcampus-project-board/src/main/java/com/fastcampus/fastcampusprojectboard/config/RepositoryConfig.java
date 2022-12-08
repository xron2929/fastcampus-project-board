package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.repository.querydsl.ArticleRepositoryCustom;
import com.fastcampus.fastcampusprojectboard.repository.querydsl.ArticleRepositoryCustomImpl;
import com.querydsl.core.annotations.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class RepositoryConfig {
   //  @Bean
    public ArticleRepositoryCustom articleRepositoryCustom() {
        return new ArticleRepositoryCustomImpl();
    }
}
