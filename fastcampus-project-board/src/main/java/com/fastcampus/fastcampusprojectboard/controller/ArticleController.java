package com.fastcampus.fastcampusprojectboard.controller;

<<<<<<< HEAD
=======
<<<<<<< Updated upstream
=======
>>>>>>> #22-잘못된도메인바로잡기
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleWithCommentResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import com.fastcampus.fastcampusprojectboard.service.PaginationService;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
=======
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
>>>>>>> Stashed changes
>>>>>>> #22-잘못된도메인바로잡기
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {
<<<<<<< HEAD
=======
<<<<<<< Updated upstream
    @GetMapping("/articles")
    public String article(ModelMap map) {
        map.addAttribute("articles", List.of());
=======
>>>>>>> #22-잘못된도메인바로잡기
    private final ArticleService articleService;
    private final PaginationService paginationService;
    @GetMapping
    public String article(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                    // 10개씩 가져오고ㅡ, createdAt 정렬 내림차순
            ModelMap map) {
<<<<<<< HEAD
=======
        System.out.println("pageable = " + pageable);
>>>>>>> #22-잘못된도메인바로잡기
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        System.out.println("sort = "+articles.getSort());
        System.out.println("sort createdAt = "+articles.getSort().getOrderFor("createdAt"));
<<<<<<< HEAD

        // 아마 JPa 튜닝을 여기서 getContent() 랑 레이지 로딩을 할 수 있는데 나중에 해야할듯
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
=======
        //System.out.println("sort createdAt DirectionName= "+articles.getSort().getOrderFor("createdAt").getDirection().name());
        System.out.println("sort title = "+articles.getSort().getOrderFor("title"));
        // 아마 JPa 튜닝을 여기서 getContent() 랑 레이지 로딩을 할 수 있는데 나중에 해야할듯
        // pageable의 sort랑 direction을 수정하는듯
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
>>>>>>> Stashed changes
>>>>>>> #22-잘못된도메인바로잡기
        return "articles/main-index";
    }
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId,ModelMap map) {
        ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());


        return "articles/detail";
    }


}
