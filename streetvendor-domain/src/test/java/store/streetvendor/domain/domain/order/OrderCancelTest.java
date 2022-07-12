package store.streetvendor.domain.domain.order;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class OrderCancelTest {

    @Disabled
    @Test
    void 사용자는_거래완료된_상품일때_order는_취소되지_않는다() {
        // given
        Orders order = Orders.newOrder(1L, 999L);
        order.changeStatusToPreparing();
        order.changeStatusToReadyToPickUp();

        // TODO: order 취소 validate 체크하기 (기존 주문이 취소가 되었을 때 다시 취소 불가능)
        // when & then
        // assertThatThrownBy(order::cancelOrderByUser).isInstanceOf(IllegalArgumentException.class);
    }

    @Disabled
    @Test
    void 사용자는_READY_상태에서_취소하지_못한다() {
        // given
        Orders order = Orders.newOrder(999L, 1L);
        // OrderHistory orderHistory = OrderHistory.newHistory()
        order.changeStatusToPreparing();

        // when & then
        // assertThatThrownBy(order::cancelOrderByUser).isInstanceOf(IllegalArgumentException.class);
    }

}
