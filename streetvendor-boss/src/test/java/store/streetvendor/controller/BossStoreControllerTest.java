package store.streetvendor.controller;

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
import store.streetvendor.auth.BossInterceptor;
import store.streetvendor.controller.store.BossStoreController;
import store.streetvendor.core.utils.dto.store.request.StoreImageRequest;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;
import store.streetvendor.service.BossStoreService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BossStoreController.class)
class BossStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BossStoreService bossStoreService;

    @MockBean
    private BossInterceptor bossInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void createBoss() {
        BDDMockito.when(bossInterceptor.preHandle(any(), any(), any())).thenReturn(true);
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
