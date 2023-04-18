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
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.core.utils.dto.store.response.StoreDetailResponse;
import store.streetvendor.core.utils.dto.store.response.StoreResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        String baseUrl = "baseUrl";
        BDDMockito.when(storeService.getStoreDetail(any(), any()))
            .thenReturn(StoreDetailResponse.of(store, baseUrl));

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


}
