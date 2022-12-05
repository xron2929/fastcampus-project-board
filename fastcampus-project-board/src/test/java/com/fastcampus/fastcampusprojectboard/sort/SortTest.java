package com.fastcampus.fastcampusprojectboard.sort;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SortTest {
    @Test
    void sortTest() {
        System.out.println(pages.getSort().getOrderFor("title"));

    }
}
