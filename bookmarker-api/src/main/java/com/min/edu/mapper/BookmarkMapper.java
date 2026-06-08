package com.min.edu.mapper;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import org.springframework.stereotype.Component;

// BookmarksDto에서 작성된 유형(BookmarkDto)으로 변환하는 방법의 기능을 가지고 있다
// Bean으로 생성해서 주입하여 사용하면 편하게 사용할 수 있다
@Component
public class BookmarkMapper {

    public BookmarkDto toDto(Bookmark bookmark) {

        BookmarkDto dto = new BookmarkDto();
        dto.setId(bookmark.getId());
        dto.setTitle(bookmark.getTitle());
        dto.setUrl(bookmark.getUrl());
        dto.setCreatedAt(bookmark.getCreatedAt());

        return dto;
    }
}
