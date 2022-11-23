package com.fastcampus.fastcampusprojectboard.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

// required는 @AllArgsConstructor가 있는 느낌이고, A record안에 title 변수있으면
// A.title(); 이게 getter인 상황임 ㅇㅇ...
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList="content"),
        @Index(columnList ="createdAt"),
        @Index(columnList = "createdBy")
},name = "aritcle_comment"
)
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false)
    private Article article;    // 제목
    @Setter @Column(nullable = false,length = 500) private String content;     // 본문
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) // 타임 포매팅 쉽게 하려고 함
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;    // 생성일자

    @CreatedBy
    @Column(nullable = false)
    private String createdBy;   // 생성자

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;   // 수정일자

    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;  // 수정자
    public ArticleComment(Article article,String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article,String content) {
        return new ArticleComment(article,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}
