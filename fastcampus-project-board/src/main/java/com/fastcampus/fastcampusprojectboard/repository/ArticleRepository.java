package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface ArticleRepository
        extends JpaRepository<Article,Long>
        ,QuerydslPredicateExecutor<Article>
        // ,QuerydslBinderCustomizer<QArticle>
    // 여기 위에 하나는 Article 하나는 QArticle인데 predi는  기본 정보
        // Blinder는 Qclass 정보라는 걸 꼭 맞춰야 됨
    // 갖다 대보면 확인 할수있는데 Prdicate는 T 일반 정보가 들어가고
    // Blinder는 Entitypath<?> 이런 식으로 Q엔티티 관련 정보가 필요하다는거
    // 확인 O
    // QuerydslPredicate만 있어도 검색 기능 만들 수 있어서
    // 따로 만들지 않음 여기서 하는 말은

{
}
