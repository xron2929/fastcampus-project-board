package com.fastcampus.fastcampusprojectboard.sort;

import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DataJpaTest
public class SortTest {
    @Autowired
    ArticleService articleService;

    @Test
    void sortTest() {

    }
}
