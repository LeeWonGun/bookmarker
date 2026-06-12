package com.min.edu.ctrl;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.dto.CreateBookmarkRequest;
import com.min.edu.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public BookmarksDto getBookmark(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "query", defaultValue = "") String query) {
        if(query == null || query.trim().length()==0) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);
    }

    /*
        해당 요청의 메서드가 성공했을 경우 HttpStatus.CREATED 값인 201을 반환
        입력 받은 요청 값은 @Valid를 통해서 처리되어 CreateBookmarkRequest 객체가 유효값을 처리할 수 있도록 함
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookmarkDto createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
        return bookmarkService.createBookmark(request);
    }

}
