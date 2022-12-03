package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static ArticleDto of(Long id,UserAccountDto userAccountDto
            ,String title, String content, String hashtag
            ,LocalDateTime createdAt, String createdBy
            ,LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }
    public static ArticleDto of(Article article) {
        return new ArticleDto(
                article.getId(),
                UserAccountDto.of(article.getUserAccount()),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto
                (entity.getId(),
                        UserAccountDto.from(entity.getUserAccount()),
                        entity.getTitle(),
                        entity.getContent(),
                        entity.getHashtag(),
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getModifiedAt(),
                        entity.getModifiedBy()
                );
    }

}
