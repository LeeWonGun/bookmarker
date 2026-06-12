package com.min.edu.service;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.dto.CreateBookmarkRequest;
import com.min.edu.mapper.BookmarkMapper;
import com.min.edu.repository.BookmarkRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
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

    @Transactional(readOnly = true)
    public BookmarksDto searchBookmarks(String query, Integer page) {
        int pageNo = page<1 ? 0 : page-1;

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDto> bookmarkPage = repository.searchByBookmarks(query, pageable);

        return new BookmarksDto(bookmarkPage);
    }

    public BookmarkDto createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
        // 외부에서 요청을 받아서 유효성 검산는 JAVA의 일반 객체를 사용하고, 오류가 발생하면 해당 결과를  Call Back으로 보냄
        // 외부의 요청이 정상이면 Entity로 만들어서 save 처리함
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());

        // 입력을 성공하면 입력된 Entity 객체를 반환
        Bookmark savedBookmark = repository.save(bookmark);

        // 반환된 Entity를 BookmarkMapper를 통해서 JAVA의 객체로 변경
        return bookmarkMapper.toDto(savedBookmark);
    }

}
