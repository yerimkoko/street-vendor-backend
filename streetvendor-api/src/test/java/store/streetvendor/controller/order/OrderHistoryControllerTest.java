package store.streetvendor.controller.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.core.auth.AuthInterceptor;
import store.streetvendor.service.order.OrderHistoryService;
import store.streetvendor.service.order.dto.response.OrderDetailViewResponse;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderHistoryController.class)
class OrderHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderHistoryService orderHistoryService;

    @MockBean
    private AuthInterceptor authInterceptor;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    void 주문내역_상세정보를_가져온다() throws Exception {
        // given
        Long storeId = 100L;
        Long orderId = 999L;
        LocalDateTime orderTime = LocalDateTime.of(2022, 11, 25, 16, 8, 0,0);
        int totalPrice = 35000;

        BDDMockito.when(orderHistoryService.getOrderDetail(any(), any()))
            .thenReturn(OrderDetailViewResponse.builder()
                .orderId(orderId)
                .orderTime(orderTime)
                .totalOrderAmount(totalPrice)
                .storeId(storeId)
                .build());

        // when & then
        mockMvc.perform(get("/api/v1/order/1")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.orderId").value(orderId))
            .andExpect(jsonPath("$.data.totalOrderAmount").value(totalPrice))
            .andExpect(jsonPath("$.data.storeId").value(storeId));

    }


}
