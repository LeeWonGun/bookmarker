package com.min.edu.service;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.mapper.BookmarkMapper;
import com.min.edu.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository repository;
    private final BookmarkMapper bookmarkMapper;

    @Transactional(readOnly = true)
    public BookmarksDto getBookmarks(Integer page) {

        int pageNo = page < 1 ? 0 : page-1;

        // jpa page를 사용한다면 Pageable에 담아서 JPA 메서드에 전달
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");

//      return new BookmarksDto(repository.findAll(pageable));

        // 방법1: Entity -> 값을 꺼내서 DTO에 담아준다
//      Page<BookmarkDto> bookmarkPage = repository.findAll(pageable).map(bookmark -> bookmarkMapper.toDto(bookmark));
//      return new BookmarksDto(bookmarkPage);

        // 방법2: Repository에 JPQL을 작성해서 직접 결과를 BookmarkDto에 매핑해서 java객체를 반환
        Page<BookmarkDto> bookmarkPage = repository.findByBookmarks(pageable);
        return new BookmarksDto(bookmarkPage);
    }
}
