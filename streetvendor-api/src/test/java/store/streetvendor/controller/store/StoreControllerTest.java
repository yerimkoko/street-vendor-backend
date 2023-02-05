package store.streetvendor.controller.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.StoreFixture;
import store.streetvendor.AuthInterceptor;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.review.Review;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.core.utils.dto.store.response.MyStoreInfo;
import store.streetvendor.core.utils.dto.store.response.StoreDetailResponse;
import store.streetvendor.core.utils.dto.store.response.StoreResponse;
import store.streetvendor.core.utils.dto.store.response.StoreReviewResponse;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

//    @Test
//    void 사장님_정보가_있을때_창업을한다() throws Exception {
//        // given
//        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
//            .category(StoreCategory.BUNG_EO_PPANG)
//            .location(new Location(33.233, 22.33))
//            .storeDescription("붕어빵 맛집.")
//            .locationDescription("까치울역 인근입니다.")
//            .name("토끼네")
//            .menus(Collections.emptyList())
//            .storeImages(Collections.emptyList())
//            .paymentMethods(Collections.emptyList())
//            .businessHours(Collections.emptyList())
//            .build();
//
//        // when & then
//        mockMvc.perform(post("/api/v1/store")
//                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//            .andDo(print())
//            .andExpect(status().isOk());
//    }

    @Test
    @Disabled
    void 카테고리로_가게_조회하기() throws Exception {
        // given
        Store store = StoreFixture.store();
        StoreCategoryRequest request = StoreCategoryRequest.testBuilder()
            .latitude(store.getLocation().getLatitude())
            .distance(2.0)
            .longitude(store.getLocation().getLongitude())
            .salesStatus(store.getSalesStatus())
            .status(store.getStatus())
            .build();

        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        BDDMockito.when(storeService.getStoresByCategoryAndLocationAndStoreStatus(request, category))
            .thenReturn(List.of(StoreResponse.of(store)));

        // when & then
        mockMvc.perform(get("/api/v1/store/category/" + category)
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            // .andExpect(jsonPath("$.data.storeId").value(store.getId()))
            .andExpect(jsonPath("$.data.locationDescription").value(store.getLocationDescription()))
            .andExpect(jsonPath("$.data.salesStatus").value(store.getSalesStatus().name()))
            .andExpect(jsonPath("$.data.storeName").value(store.getName()));

    }

    @Test
    void 가게의_상세정보를_조회한다() throws Exception {
        // given
        Store store = StoreFixture.store();
        BDDMockito.when(storeService.getStoreDetail(any()))
            .thenReturn(StoreDetailResponse.of(StoreFixture.store(), StoreFixture.boss()));

        // when & then
        mockMvc.perform(get("/api/v1/store/detail/999")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.storeName").value(store.getName()))
            .andExpect(jsonPath("$.data.storeId").value(store.getId()))
            .andExpect(jsonPath("$.data.salesStatus").value(store.getSalesStatus().name()))
            .andExpect(jsonPath("$.data.locationDescription").value(store.getLocationDescription()));
    }

    @Test
    void 내_가게_정보_업데이트_하기() throws Exception {
        // given
        String name = "새로운 가게 이름";
        List<StoreImageRequest> storeImages = List.of(StoreImageRequest.builder()
            .isThumbNail(true)
            .imageUrl("tokki")
            .build());
        String description = "사랑해";

        StoreUpdateRequest request = StoreUpdateRequest.testBuilder()
            .name(name)
            .menus(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .storeImages(storeImages)
            .paymentMethods(Collections.emptyList())
            .description(description)
            .build();

        // then
        mockMvc.perform(put("/api/v1/store/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 가게를_삭제하다() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/v1/store/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 메뉴_상태_수정하기() throws Exception {
        // when & then
        mockMvc.perform(put("/api/v1/store/1/menu/1/ON_SALE")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
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

    @Test
    void 리뷰_수정하기() throws Exception {
        String comment = "뽀미는 사실은 세모였어요";
        Rate rate = Rate.five;
        UpdateStoreReviewRequest request = UpdateStoreReviewRequest.builder()
            .comment(comment)
            .rate(rate)
            .build();

        // when & then
        mockMvc.perform(put("/api/v1/store/review/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
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
    void 가게_즐겨찾기_추가하기() throws Exception {
        // when & then
        mockMvc.perform(post("/api/v1/star")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 가게_즐겨찾기_삭제하기() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/v1/star/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


}
