package com.fastcampus.fastcampusprojectboard.type;

import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@DisplayName("비즈니스 로직 - 게시글 ")
@ExtendWith({MockitoExtension.class})
public class ArticleService2Test {
    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;
    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다 ")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(),"uno","title","content","hashtag");
        sut.saveArticle(dto);
    }
}
