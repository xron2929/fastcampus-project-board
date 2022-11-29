package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "email",unique = true),
        @Index(columnList = "createdAt")
})
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false,length = 50,name = "userId") private String userId;
    @Column(nullable = false) private String userPassword;

    @Column(length = 100) private String email;
    @Column(length = 100) private String nickname;
    @Setter private  String memo;
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
    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return UserAccount.of(userId, userPassword, email, nickname, memo, null);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        return new UserAccount(userId, userPassword, email, nickname, memo, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }


}
