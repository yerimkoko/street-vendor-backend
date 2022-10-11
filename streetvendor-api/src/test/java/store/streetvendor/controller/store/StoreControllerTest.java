package store.streetvendor.controller.store;

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
import store.streetvendor.config.auth.AuthInterceptor;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.StoreCategory;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;
import store.streetvendor.service.store.dto.request.StoreImageRequest;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.service.store.dto.response.MyStoreInfo;
import store.streetvendor.service.store.dto.response.StoreInfoResponse;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void 사장님_정보가_있을때_창업을한다() throws Exception {
        // given
        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .category(StoreCategory.BUNG_EO_PPANG)
            .location(new Location(33.233, 22.33))
            .storeDescription("붕어빵 맛집.")
            .locationDescription("까치울역 인근입니다.")
            .name("토끼네")
            .menus(Collections.emptyList())
            .storeImages(Collections.emptyList())
            .paymentMethods(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .build();

        // when & then
        mockMvc.perform(post("/api/v1/store")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void 내_가게_정보를_불러온다() throws Exception {
        // given
        String storeName = "토끼네";
        Long storeId = 999L;
        String locationDescription = "신정네거리 3번출구 앞";
        StoreSalesStatus open = StoreSalesStatus.OPEN;

        BDDMockito.when(storeService.getMyStores(any()))
            .thenReturn(List.of(MyStoreInfo.builder()
                .storeName(storeName)
                .storeId(storeId)
                .locationDescription(locationDescription)
                .salesStatus(open)
                .build()));

        // when & then
        mockMvc.perform(get("/api/v1/my-stores")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].storeId").value(storeId))
            .andExpect(jsonPath("$.data[0].storeName").value(storeName))
            .andExpect(jsonPath("$.data[0].locationDescription").value(locationDescription))
            .andExpect(jsonPath("$.data[0].salesStatus").value(open.name()));
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


}
