package com.min.edu.ctrl;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public BookmarksDto getBookmark(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        return bookmarkService.getBookmarks(page);
    }
}
