package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleWithCommentResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping
@Controller
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/articles")
    public String article(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                    // 10개씩 가져오고ㅡ, createdAt 정렬 내림차순
            ModelMap map) {
        map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        return "articles/main-index";
    }
    @GetMapping("/articles/{articleId}")
    public String article(@PathVariable Long articleId,ModelMap map) {
        ArticleWithCommentResponse article = ArticleWithCommentResponse
                .from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());
        return "articles/detail";
    }

}
