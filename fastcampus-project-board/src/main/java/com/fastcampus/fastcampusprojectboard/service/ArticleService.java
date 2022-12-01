package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType) {
        return Page.empty();
        // 나중에는 articleRepository.findALl(); 호출 할 예정임
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
}
