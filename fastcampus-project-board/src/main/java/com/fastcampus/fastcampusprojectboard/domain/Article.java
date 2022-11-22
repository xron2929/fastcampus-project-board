package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Table;
import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = )
public class Article {
    private Long id;
    private String title;   // 제목
    private ArticleComment comment; // 댓글
    private String hashtag; // 해시태그
    private LocalDateTime createdAt;    // 생성일자
    private String createdBy;   // 생성자
    private LocalDateTime modifiedAt;   // 수정일자
    private String modifiedBy;  // 수정자
}
