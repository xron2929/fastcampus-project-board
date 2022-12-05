package com.fastcampus.fastcampusprojectboard.sort;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SortTest {
    @Test
    void sortTest() {
        System.out.println(pages.getSort().getOrderFor("title"));
=======
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DataJpaTest
public class SortTest {
    @Autowired ArticleService articleService;

    @Test
    void sortTest() {
>>>>>>> #22-잘못된도메인바로잡기

    }
}
