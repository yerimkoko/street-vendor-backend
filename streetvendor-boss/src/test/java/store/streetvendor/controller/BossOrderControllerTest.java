package store.streetvendor.controller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import store.streetvendor.BossFixture;
import store.streetvendor.auth.BossInterceptor;
import store.streetvendor.StoreFixture;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.utils.dto.order.response.OrderListToBossResponse;
import store.streetvendor.service.BossOrderService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BossOrderController.class)
class BossOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BossInterceptor bossInterceptor;

    @MockBean
    private BossOrderService bossOrderService;


    @BeforeEach
    void createBossInterceptor() {
        BDDMockito.when(bossInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);

    }


    @Test
    @Disabled
    void 사장님이_진행중인_주문을_불러온다() throws Exception {
        // given
        Boss boss = Boss.newGoogleInstance("dd", "name", "1002-222-22222", "gochi97@naver.com", "profileUrl", "0202");

        Orders order = Orders.preparingOrder(StoreFixture.store(), boss.getId(), PaymentMethod.CASH, LocalDateTime.now().plusHours(1L));

        MultiValueMap<String, String> status = new LinkedMultiValueMap<>();
        status.add("orderStatus", OrderStatus.REQUEST.name());
        status.add("storeId", "1");

        BDDMockito.when(bossOrderService.getAllOrders(1L, BossFixture.boss().getId(), OrderStatus.PREPARING))
            .thenReturn(List.of(OrderListToBossResponse.of(order, boss)));

        // when
        mockMvc.perform(get("/api/v1/orders/1")
                .params(status)
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

    @Test
    @Disabled
    void 사장님이_주문을_취소한다() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/v1/1/orders/1/cancel")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


}
