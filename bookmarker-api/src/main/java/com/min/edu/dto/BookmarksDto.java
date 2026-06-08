package com.min.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.min.edu.domain.Bookmark;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

// JPA를 통해서 Pagenation 처리를 하면 현재 페이지, 전체 페이지, 끝 여부... => DTO로 변경해서 처리하겠다
@Getter
@Setter
public class BookmarksDto {

    private List<BookmarkDto> data; // 현재 페이지 데이터 목록
    private long  totalElements; // 전체 데이터 수
    private int totalPages; // 전체 페이지 수
    private int currentPage; // 현재 페이지

    // JSON의 결과물 값이 다름
    @JsonProperty(value = "isFirst")
    private boolean isFirst;

    @JsonProperty(value = "isLast")
    private boolean isLast;

    private boolean hasNext;
    private boolean hasPrevious;

    // service에서 반환타입
    public BookmarksDto(Page<BookmarkDto> bookmarkPage) {
        this.setData(bookmarkPage.getContent()); // JPA의 결과 중에서 데이터(게시글만)를 담는다
        this.setTotalElements(bookmarkPage.getTotalElements());
        this.setTotalPages(bookmarkPage.getTotalPages());
        this.setCurrentPage(bookmarkPage.getNumber()+1);
        this.setFirst(bookmarkPage.isFirst());
        this.setLast(bookmarkPage.isLast());
        this.setHasNext(bookmarkPage.hasNext());
        this.setHasPrevious(bookmarkPage.hasPrevious());
    }

}
