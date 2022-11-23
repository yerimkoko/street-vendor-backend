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
import store.streetvendor.MemberFixture;
import store.streetvendor.StoreFixture;
import store.streetvendor.core.config.auth.AuthInterceptor;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.utils.dto.response.OrderListToBossResponse;
import store.streetvendor.service.BossOrderService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BossOrderController.class)
class BossOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private BossOrderService bossOrderService;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);
    }

    @Test
    @Disabled
    void 사장님이_진행중인_주문을_불러온다() throws Exception {
        // given
        Orders order = Orders.preparingOrder(StoreFixture.store(), MemberFixture.member().getId(), PaymentMethod.CASH);

        MultiValueMap<String, String> status = new LinkedMultiValueMap<>();
        status.add("orderStatus", OrderStatus.REQUEST.name());
        status.add("storeId", "1");
        BDDMockito.when(bossOrderService.getAllOrders(1L, MemberFixture.boss().getId(), OrderStatus.PREPARING))
            .thenReturn(List.of(OrderListToBossResponse.of(order, MemberFixture.member())));

        // when
        mockMvc.perform(get("/api/v1/boss/order")
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
