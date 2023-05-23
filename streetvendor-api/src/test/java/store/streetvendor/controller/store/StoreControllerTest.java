package store.streetvendor.controller.store;

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
import store.streetvendor.core.auth.AuthInterceptor;
import store.streetvendor.StoreFixture;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.dto.store.response.StoreDetailResponse;
import store.streetvendor.core.utils.dto.store.response.StoreInfoResponse;
import store.streetvendor.service.store.StoreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private StoreService storeService;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }


    @Test
    @Disabled
    void 카테고리로_가게_조회하기() throws Exception {
        // given
        Store store = StoreFixture.store();

        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        BDDMockito.when(storeService.getStoresByCategoryAndLocationAndStoreStatus(category, any(), any(), any(), any(), any()))
            .thenReturn(List.of(StoreInfoResponse.of(store, any(), any(), any())));

        // when & then
        mockMvc.perform(get("/api/v1/store/category/" + category + "?longitude=33.333&latitude=128.33")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            // .andExpect(jsonPath("$.data.storeId").value(store.getId()))
            .andExpect(jsonPath("$.data.locationDescription").value(store.getLocationDescription()))
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
