package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import com.fastcampus.fastcampusprojectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface ArticleRepository
        extends JpaRepository<Article,Long>
        ,QuerydslPredicateExecutor<Article>
        ,QuerydslBinderCustomizer<QArticle>
    // 여기 위에 하나는 Article 하나는 QArticle인데 predi는  기본 정보
        // Blinder는 Qclass 정보라는 걸 꼭 맞춰야 됨
    // 갖다 대보면 확인 할수있는데 Prdicate는 T 일반 정보가 들어가고
    // Blinder는 Entitypath<?> 이런 식으로 Q엔티티 관련 정보가 필요하다는거
    // 확인 O
    // QuerydslPredicate만 있어도 검색 기능 만들 수 있어서
    // 따로 만들지 않음 여기서 하는 말은

{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        // 자동 바인딩 제거
        // 대소문자 더할 꺼고 일부만 필터링 되게 할꺼라 위처럼 제거함
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);;
        // 바인딩 할 것들만 추가
        // bindings.bind(root.title).first(StringExpression::likeIgnoreCase); //  like '%${v}%'

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        // like '${v}'
        // 포함 여부할 꺼면 % %이렇게 해야되서 크면 좋겠지만, 작고 간단한 프로젝트 할꺼라 containsIgnoreCase를 함
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
        // http://localhost:8080/api/articles?title=x
        // 이렇게 하면 대소문자 상관없이 x가 포함된거는 다 됨
        // 그리고 contains이거 전에 Excluding 빼면 대소문자 구분도 안되고, 중간에 머 넣는 것도 안되었는데
        // 이게 including 덕에 된거 같기도 하고 암튼 그럼
        // 만약 http://localhost:8080/api/articles?modifiedBy=x 이러면 필터링 조차도 안됨 이유는
        // bindings.excludeUnlistedProperties(true); 여기서 제거했기 때문에 그냥 20개가 뜸 
    }
}
