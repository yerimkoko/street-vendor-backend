package store.streetvendor.boss.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import store.streetvendor.StoreFixture;
import store.streetvendor.boss.service.service.BossOrderService;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.StoreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BossOrderServiceTest extends StoreFixture {

    @Autowired
    private BossOrderService bossOrderService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Disabled
    void 사장님이_들어온_주문을_확인하고_PREPARING_상태로_변경한다() {
        // given

        PaymentMethod paymentMethod = PaymentMethod.ACCOUNT_TRANSFER;
        Orders newOrder = Orders.newOrder(StoreFixture.store(), StoreFixture.member().getId(), paymentMethod);
        storeRepository.save(StoreFixture.store());
        Orders order = orderRepository.save(newOrder);

        // when
        bossOrderService.changeStatusToPreparing(StoreFixture.store().getId(), StoreFixture.store().getMemberId(), order.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.PREPARING);

    }
}
