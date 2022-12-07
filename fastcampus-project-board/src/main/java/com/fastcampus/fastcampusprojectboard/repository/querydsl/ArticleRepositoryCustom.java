package com.fastcampus.fastcampusprojectboard.repository.querydsl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleRepositoryCustom {
    List<String> findAllDistinctHashtags();
}
