package com.fastcampus.fastcampusprojectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping
@Controller
public class ArticleController {
    @GetMapping("/articles")
    public String article(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/main-index";
    }
    @GetMapping("/articles/{articleId}")
    public String article(ModelMap map,Long itemId) {
        map.addAttribute("article",null);
        map.addAttribute("articles", List.of());
        return "articles/detail";
    }

}
