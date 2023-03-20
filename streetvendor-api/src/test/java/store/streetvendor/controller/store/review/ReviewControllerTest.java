package store.streetvendor.controller.store.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.AuthInterceptor;
import store.streetvendor.StoreFixture;
import store.streetvendor.core.domain.store.Rate;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.review.Review;
import store.streetvendor.core.utils.dto.store.request.AddStoreReviewRequest;
import store.streetvendor.core.utils.dto.store.response.StoreReviewResponse;
import store.streetvendor.service.store.StoreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthInterceptor authInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    void 가게_리뷰_조회하기() throws Exception {
        // given
        Store store = StoreFixture.store();
        Long storeId = 1L;
        Long memberId = 1L;
        StoreReviewResponse response = StoreReviewResponse.of(
            storeId,
            List.of(Review.of(store, memberId, Rate.five, "최고의 가게")));

        BDDMockito.when(storeService.getStoreReviews(storeId))
            .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/store/review/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.storeId").value(storeId));
    }

    @Test
    void 리뷰_등록하기() throws Exception {
        // given
        String comment = "뽀미는 직각직각";
        Rate rate = Rate.five;
        AddStoreReviewRequest request = AddStoreReviewRequest.builder()
            .comment(comment)
            .rate(rate)
            .build();

        // when & then
        mockMvc.perform(post("/api/v1/store/review/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }


}
