package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        String content,
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {
    public static ArticleCommentDto of(
            Long id, String content, UserAccountDto userAccountDto,LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt,
                                       String modifiedBy) {
        return new ArticleCommentDto(id,content,userAccountDto,createdAt,createdBy,modifiedAt,modifiedBy);
    }
    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getContent(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }
}
