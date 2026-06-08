package com.min.edu.api;

import com.min.edu.domain.Bookmark;
import com.min.edu.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Test를 위해서 무작위 포트의 사용을 위한 설정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// MVC 테스트를 위한 자동 설정
@AutoConfigureMockMvc

// jdbc:tc:postgresql:9.6.8:///databasename
// 테스트 컨테이너 추가
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
class BookmarkControllerTest {

    // Test를 위한 MockMvc 객체를 선언
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository.deleteAllInBatch();

        List<Bookmark> bookmarks = List.of(
                new Bookmark(null, "OpenAI", "https://openai.com", Instant.now()),
                new Bookmark(null, "Notion", "https://www.notion.so", Instant.now()),
                new Bookmark(null, "Figma", "https://www.figma.com", Instant.now()),
                new Bookmark(null, "Slack", "https://slack.com", Instant.now()),
                new Bookmark(null, "Discord", "https://discord.com", Instant.now()),
                new Bookmark(null, "Spotify", "https://www.spotify.com", Instant.now()),
                new Bookmark(null, "Netflix", "https://www.netflix.com", Instant.now()),
                new Bookmark(null, "Airbnb", "https://www.airbnb.com", Instant.now()),
                new Bookmark(null, "LinkedIn", "https://www.linkedin.com", Instant.now()),
                new Bookmark(null, "Kakao", "https://www.kakao.com", Instant.now()),
                new Bookmark(null, "Coupang", "https://www.coupang.com", Instant.now()),
                new Bookmark(null, "Toss", "https://toss.im", Instant.now()),
                new Bookmark(null, "Naver", "https://www.naver.com", Instant.now()),
                new Bookmark(null, "Baemin", "https://www.baemin.com", Instant.now()),
                new Bookmark(null, "Samsung", "https://www.samsung.com", Instant.now())
        );

        bookmarkRepository.saveAll(bookmarks);
    }

    @Test
    void shouldBookmarkers() throws Exception {
        // 1번 테스트: /api/bookmarkers의 호출 여부 => 결과는 BookmarksDto
        mvc.perform(MockMvcRequestBuilders.get("/api/bookmarks"))
                .andExpect(status().isOk())

        // 2번 테스트: @BeforeEach에 의해서 데이터가 모두 삭제되었기 때문에 totalElement가 0이면 됨
        // .andExpect(jsonPath("$.totalElements", equalTo(0)));

        // 3번 테스트: 가상의 값(15개 - @BeforeEach)을 사용해서 결과를 확인
                .andExpect(jsonPath("$.totalElements", equalTo(15)))
                .andExpect(jsonPath("$.totalPages", equalTo(2)))
                .andExpect(jsonPath("$.currentPage", equalTo(1)))
                .andExpect(jsonPath("$.isFirst", equalTo(true)))
                .andExpect(jsonPath("$.isLast", equalTo(false)))
                .andExpect(jsonPath("$.hasNext", equalTo(true)))
                .andExpect(jsonPath("$.hasPrevious", equalTo(false)));
    }

    // 페이징 값을 입력해서 나오는 결과를 확인한다
    // 페이지가 1이 전달 됐을 때, 2가 전달 됐을 때 테스트
    @ParameterizedTest
    @CsvSource({"1, 15, 2, 1, true, false, true, false", "2, 15, 2, 2, false, true, false, true"})
    void shouldBookmarksPage(
            int pageNo, int totalElements, int totalPages, int currentPage,
            boolean isFirst, boolean isLast, boolean hasNext, boolean hasPrevious
    ) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/bookmarks?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", equalTo(currentPage)))
                .andExpect(jsonPath("$.isFirst", equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", equalTo(isLast)))
                .andExpect(jsonPath("$.hasNext", equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", equalTo(hasPrevious)));
    }
}
