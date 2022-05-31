package store.streetvendor.domain.domain.order;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderCancelTest {

    @Test
    void 사용자는_거래완료된_상품일때_order는_취소되지_않는다() {
        // given
        Orders order = Orders.newOrder(1L, 999L);
        order.changeStatusToPreparing();
        order.changeStatusToReadyToPickUp();

        // when & then
        assertThatThrownBy(order::cancelOrderByUser).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사용자는_READY_상태에서_취소하지_못한다() {
        // given
        Orders order = Orders.newOrder(999L, 1L);
        order.changeStatusToPreparing();

        // when & then
        assertThatThrownBy(order::cancelOrderByUser).isInstanceOf(IllegalArgumentException.class);
    }

}
