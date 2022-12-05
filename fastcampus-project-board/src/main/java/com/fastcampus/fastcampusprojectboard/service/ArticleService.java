package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyWord, Pageable pageable) {
        if (searchKeyWord == null || searchKeyWord.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);

        }
        // 그룹에 따른 클린코드화 방법은 2가지
        // 1. 부모타입을 오버로딩 하는 거 이 경우는 부모와 자식이라는 문제가 생김(하나의 그룹이
        // 부모와 자식이라 볼 수 있지만 상황에 따라서 다른 경우의 부모와 자식인 경우)
        // 도형입장에서 보면 사각형이 정사각형, 직사각형의 부모라 볼 수 있고, 사각형의 얼굴을 닮은꼴 그룹에
        // 해당이 안되지만
        // 정사각형 형태꼴 이라고 하면, 정사각형 형태꼴과 직사각형 형태꼴이라고 했을 때
        // 정사각형과 직사각형은 각각 다른 그룹에 있음. 즉 상황에 따라서는 같은 형태일 수 있고, 다른 형태
        // 물론 이걸 인터페이스를 받아서 처리하면 되는데 상속이라는 느낌이 부모와 자식을 일차원으로
        // 생각하니 이넘 타입이 더 좋음
        // 즉 그룹에 대해서 묶는다는 생각이 나오면, 네이밍 vs 오버로딩 vs 이넘인데, 이게 N개의 형태가 되어버리면
        // 무조건 이넘이 베스트이므로, 장기적으로 생각했을 떄
        // searchTitle() 같이 그냥 스트링으로 해결되는 타입이면,네이밍
        // 그게 아니라면 이넘이 무조건 베스트라는 점
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitle(searchKeyWord, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_UserIdContaining(searchKeyWord, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyWord, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findById(searchKeyWord, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyWord, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long id) {
        System.out.println("articleRepository = " + articleRepository);
        System.out.println("id = " + id);
        System.out.println("articleRepository = " + articleRepository.count());
        Article findArticle = articleRepository.findById(id).orElse(null);
        System.out.println("findArticle = " + findArticle);
        ArticleDto articleDto = ArticleDto.of(findArticle);
        return articleDto;

    }

    @Transactional(readOnly = true)
    public List<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return List.of();
    }


    public void saveArticle(ArticleDto dto) {

    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
        return ArticleWithCommentsDto.from(article);
    }


    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
        Page<Article> pages = new PageImpl<>();

    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }
}