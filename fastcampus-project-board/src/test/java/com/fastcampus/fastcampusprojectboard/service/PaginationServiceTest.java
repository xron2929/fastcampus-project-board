package com.fastcampus.fastcampusprojectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,classes=PaginationService.class)
// WebEnvironment를 목으로 설정할수도 있는데 NONE으로 줄일 수 있고, 전부 다 빈 주입하는 게 아니라 일부만 빈 주입해서
    // 가볍게 사용가능 물론 스프링 부트 테스트 자체가 가볍진 않지만 줄일 수는 있음
class PaginationServiceTest {
    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService sut) {
        this.sut = sut;
    }
    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면 페이징 번호 바 리스트를 준다")
    @MethodSource
    @ParameterizedTest
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer>expected) {

        //given
        // 현재 페이지, 총 페이지 수를 가져와서 그 중 안 값을 확인하는데 실제로는 데이터 전체수는 현재 데이터 갯수로 할듯
        //when
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber,totalPages);
        System.out.println("actual = " + actual);
        //then
        assertThat(actual).isEqualTo(expected);
        // 당연히 actual는 null을 리턴하게했으니 false일듯

    }

    @DisplayName("현재 설정되어있는 페이지네이션 바의 길이를 알려준다")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        //given

        //when
        int barLength = sut.currentBarLength();
        //then
        assertThat(barLength).isEqualTo(5);
    }
    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(arguments(0,13,List.of(0,1,2,3,4)),
                         arguments(1,13,List.of(0,1,2,3,4)),
                         arguments(2,13,List.of(0,1,2,3,4)),
                         arguments(3,13,List.of(1,2,3,4,5)),
                         arguments(4,13,List.of(2,3,4,5,6)),
                         arguments(5,13,List.of(3,4,5,6,7)),
                         arguments(6,13,List.of(4,5,6,7,8)),
                         arguments(10,13,List.of(8,9,10,11,12)),
                         arguments(11,13,List.of(9,10,11,12)),
                         arguments(12,13,List.of(10,11,12)));

    }

}