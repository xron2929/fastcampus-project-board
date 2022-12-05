package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleWithCommentResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
<<<<<<< HEAD
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
=======
import com.fastcampus.fastcampusprojectboard.service.PaginationService;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
>>>>>>> #21-페이지네이션기능구현
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
>>>>>>> #21-페이지네이션기능구현

import java.util.List;

@RequiredArgsConstructor
<<<<<<< HEAD
@RequestMapping
@Controller
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/articles")
=======
@RequestMapping("/articles")
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;
    @GetMapping
>>>>>>> #21-페이지네이션기능구현
    public String article(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                    // 10개씩 가져오고ㅡ, createdAt 정렬 내림차순
            ModelMap map) {
<<<<<<< HEAD
        map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        return "articles/main-index";
    }
    @GetMapping("/articles/{articleId}")
=======
        Page<ArticleResponse> articles = articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());
        // 아마 JPa 튜닝을 여기서 getContent() 랑 레이지 로딩을 할 수 있는데 나중에 해야할듯
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers",barNumbers);
        return "articles/main-index";
    }
    @GetMapping("/{articleId}")
>>>>>>> #21-페이지네이션기능구현
    public String article(@PathVariable Long articleId,ModelMap map) {
        ArticleWithCommentResponse article = ArticleWithCommentResponse
                .from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());
        return "articles/detail";
    }

}
