package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,
        String hashtag

) implements Serializable {

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
    public static ArticleDto of(Article article) {
        return new ArticleDto
                (article.getCreatedAt(),
                article.getCreatedBy(),
                article.getTitle(),
                article.getContent(),
                article.getHashtag());
    }
}
