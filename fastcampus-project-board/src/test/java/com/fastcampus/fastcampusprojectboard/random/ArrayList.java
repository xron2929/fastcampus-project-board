package com.fastcampus.fastcampusprojectboard.random;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class ArrayList {
    @DisplayName("1부터 10까지만값 가져오기")
    @Test
    void randomOneToTen() {
        List<Integer> arrs = List.of(1,2,3,4,5,6,7,8,9,10);
        // arrs.addAll(IntStream.range(1,11).boxed().toList());
        IntStream.range(1,11).forEach(x-> System.out.println(x));
        arrs.equals(IntStream.range(1,11).boxed().toList());
    }

}
