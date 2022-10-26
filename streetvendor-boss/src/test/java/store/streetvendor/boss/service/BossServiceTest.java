package store.streetvendor.boss.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.Orders;
import streetvendor.boss.service.BossOrderService;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.order.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BossServiceTest {

    private OrderRepository orderRepository;

    private BossOrderService bossOrderService;

//    @Test
//    @Disabled
//    void 사장님이_들어온_주문을_확인하고_PREPARING_상태로_변경한다() {
//        // given
//        Orders order = orderRepository.save(order());
//
//        // when
//        bossOrderService.changeStatusToPreparing(store.getId(), boss.getId(), order.getId());
//
//        // then
//        List<Orders> orders = orderRepository.findAll();
//        assertThat(orders).hasSize(1);
//        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.PREPARING);
//
//    }
}
