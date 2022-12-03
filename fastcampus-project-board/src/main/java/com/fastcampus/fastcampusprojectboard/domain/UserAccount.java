package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {
    @Id
    @Column(length = 50, nullable = false)
    private String userId;
    @Column(nullable = false) private String userPassword;
    // password가 db에 예약어로 있어서 이렇게 하신듯

    @Column(length = 100) private String email;
    @Column(length = 100) private String nickname;
    @Setter private  String memo;

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

    public UserAccount(String userId, String userPassword, String email, String nickname, String memo, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, LocalDateTime createdAt) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdAt = createdAt;
        this.modifiedAt = createdAt;
    }

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo,LocalDateTime createdAt) {
        return new UserAccount(userId, userPassword, email, nickname, memo,createdAt);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
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
