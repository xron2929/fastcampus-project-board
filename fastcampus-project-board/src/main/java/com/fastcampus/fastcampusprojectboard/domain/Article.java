package com.fastcampus.fastcampusprojectboard.domain;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList="content"),
        @Index(columnList = "hashtag"),
        @Index(columnList ="createdAt"),
        @Index(columnList = "createdBy")
},name = "article"
)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Identity 전략은 쓰기지연이 없이 persist하면 바로 불러와서
    // 벌크 연산이 필요없을듯
    // mysql, postgre는 시퀀스를 지원안해줘서 이렇게 해야되는듯
    @Column(name = "article_id")
    private Long id;
    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false) private String content; // 댓글
    @Setter @Column(nullable = false) private String hashtag; // 해시태그

    @ToString.Exclude // 무한 참조를 막으려고 Exclude 설정함
    // article에 Comment가 없어서 그런듯
    @OneToMany(mappedBy = "article")

    private final Set<ArticleComment> articleComments = new LinkedHashSet<ArticleComment>();

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)


    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    // 생성일자
    @CreatedBy @Column(nullable = false) private String createdBy;   // 생성자
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   // 수정일자
    @LastModifiedBy @Column(nullable = false) private String modifiedBy;  // 수정자


    protected Article(){}
    // @Transient private static Article article = new Article();
    // @Transient는 컬럼 등록 막아줌
    public static Article of(UserAccount userAccount,String title, String content, String hashtag) {
        return new Article(userAccount,title,content,hashtag);
    }
    private Article(UserAccount userAccount,String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
