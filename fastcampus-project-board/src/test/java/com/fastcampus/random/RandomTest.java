package com.fastcampus.random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomTest {
    @DisplayName("랜덤 문자열 여러개 생성")
    @Test
    void random() {
        RandomString randomString = new RandomString();
        List<String> findRandomStrings = randomString.
                randomSizeStrings(100, 20);
        for (String findRandomString : findRandomStrings){
            System.out.println("findRandomString = " + findRandomString.toString());
            assertThat(findRandomString).hasSizeLessThan(21);
        }
    }
    @DisplayName("랜덤 크기의 문자열 생성")
    @Test
    void random_size_string() {
        RandomString randomString = new RandomString();
        String s = randomString.randomSizeString(20);
        System.out.println("s = " + s);
    }

    @DisplayName("정해진 크기의 랜덤 문자열 생성")
    @Test
    void random_string() {
        RandomString randomString = new RandomString();
        String s = randomString.randomToString(20);
        System.out.println("s = " + s);
    }
}
