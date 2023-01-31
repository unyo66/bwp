package com.bwp.app.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/** 할일 : 게시판 페이지 페이지네이션 서비스 구현 */

@Service
public class PaginationService {
    /* 페이지네이션 바의 길이(개수) - 한번에 몇 페이지씩 보여줄지 페이지 숫자 결정 */
    private static final int BAR_LENGTH = 5;

    /*  테스트에서 getPaginationBarNumbers 에 값을 보내서 리턴으로 페이징 결과를 얻어낸다.
    *   List<Integer> : 숫자 리스트를 받아 뷰에 보내줌.
    *   int currentPageNumber : 현재 몇 페이지
    *   int totalPages : 전체 페이지 수
    *
    * */
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        /* startNumber, endNumber 구하기
        *  현재 선택한 페이지 번호가 페이징 부분의 가운데에 위치하게 만들거임.
        *  */

//        int startNumber = Math.max(0, currentPageNumber - (BAR_LENGTH / 2));
        int startNumber = Math.max(0, currentPageNumber / BAR_LENGTH * 5); // 페이징 5개씩 움직이게 하기
        int endNumber = Math.min((startNumber + BAR_LENGTH), totalPages);


        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    /* getter */
    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
