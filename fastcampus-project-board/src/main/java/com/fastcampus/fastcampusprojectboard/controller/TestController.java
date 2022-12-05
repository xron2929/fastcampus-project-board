<<<<<<< HEAD
package com.fastcampus.fastcampusprojectboard.controller;public class TestController {
=======
package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import com.fastcampus.fastcampusprojectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TestController {
    private final ArticleService articleService;
    private final PaginationService paginationService;
    @GetMapping("/die")
    public String article(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort="createdBy",direction = Sort.Direction.ASC) Pageable pageable,
            // 10개씩 가져오고ㅡ, createdAt 정렬 내림차순
            ModelMap map) {
        System.out.println("pageable = " + pageable);
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        System.out.println("sort = "+articles.getSort());
        System.out.println("sort createdAt = "+articles.getSort().getOrderFor("createdAt"));
        //System.out.println("sort createdAt DirectionName= "+articles.getSort().getOrderFor("createdAt").getDirection().name());
        System.out.println("sort title = "+articles.getSort().getOrderFor("title"));
        // 아마 JPa 튜닝을 여기서 getContent() 랑 레이지 로딩을 할 수 있는데 나중에 해야할듯
        // pageable의 sort랑 direction을 수정하는듯
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        return "articles/main-index";
    }
    @ResponseBody
    @GetMapping("/die2")
    public String article2(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort="createdBy",direction = Sort.Direction.DESC) Pageable pageable,
            // 10개씩 가져오고ㅡ, createdAt 정렬 내림차순
            ModelMap map) {
        System.out.println("pageable = " + pageable);
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        System.out.println("sort = "+articles.getSort());
        System.out.println("sort createdAt = "+articles.getSort().getOrderFor("createdAt"));
        //System.out.println("sort createdAt DirectionName= "+articles.getSort().getOrderFor("createdAt").getDirection().name());
        System.out.println("sort title = "+articles.getSort().getOrderFor("title"));
        // 아마 JPa 튜닝을 여기서 getContent() 랑 레이지 로딩을 할 수 있는데 나중에 해야할듯
        // pageable의 sort랑 direction을 수정하는듯
        return "ok";
    }
>>>>>>> #22-잘못된도메인바로잡기
}
