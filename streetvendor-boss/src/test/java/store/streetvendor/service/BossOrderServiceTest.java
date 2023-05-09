package store.streetvendor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import store.streetvendor.SetUpStore;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.order.*;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.utils.dto.order_history.request.AddNewOrderHistoryRequest;
import store.streetvendor.core.utils.dto.order_history.request.OrderHistoryMenusRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BossOrderServiceTest extends SetUpStore {

    @Autowired
    private BossOrderService bossOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @AfterEach
    void cleanUp() {
        orderHistoryRepository.deleteAll();
        orderMenuRepository.deleteAll();
        orderRepository.deleteAll();
        cleanup();
    }

    @Test
    void 사장님이_들어온_주문을_확인하고_PREPARING_상태로_변경한다() {
        // given
        Orders order = orderRepository.save(Orders.newOrder(store, boss.getId(), paymentMethod, LocalDateTime.now().plusHours(1L)));

        // when
        bossOrderService.changeStatusToPreparing(store.getId(), boss.getId(), order.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.PREPARING);

    }

    @Test
    void 사장님에게_주문상태가_PREPARING_일때_READY_TO_PICK_UP_으로_변경한다() {
        // given
        LocalDateTime pickUpTime = LocalDateTime.now().plusHours(1L);
        Orders order = orderRepository.save(Orders.preparingOrder(store, boss.getId(), paymentMethod, pickUpTime));

        OrderHistoryMenusRequest request = OrderHistoryMenusRequest.builder()
            .menu(menu)
            .orderMenu(createOrderMenu(order, menu))
            .build();

        AddNewOrderHistoryRequest historyRequest = AddNewOrderHistoryRequest.builder()
            .menus(List.of(request))
            .orderId(order.getId())
            .storeId(store.getId())
            .build();

        // when
        bossOrderService.changeStatusToReadyToPickUp(boss.getId(), historyRequest);

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).isEmpty();

        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orderHistories).hasSize(1);

    }

    @Test
    void 사장님이_주문을_취소한다() {
        // given
        Orders order = orderRepository.save(order());

        // when
        bossOrderService.cancelOrderByBoss(store.getId(), order.getId(), boss.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orders).isEmpty();
        assertThat(orderHistories).hasSize(1);
    }

    private OrderMenu createOrderMenu(Orders orders, Menu menu) {
        int count = 3;
        OrderMenu orderMenu = OrderMenu.of(orders, menu, count);
        return orderMenuRepository.save(orderMenu);
    }

    @Test
    void 주문이_거래완료가_되면_기존_Order_가_삭제된다() {
        // given
        Orders order = orderRepository.save(order());
        OrderMenu orderMenu = createOrderMenu(order, menu);

        OrderHistoryMenusRequest menusRequest = OrderHistoryMenusRequest.builder()
            .orderMenu(orderMenu)
            .menu(menu)
            .build();

        AddNewOrderHistoryRequest request = AddNewOrderHistoryRequest.builder()
            .orderId(order.getId())
            .menus(List.of(menusRequest))
            .storeId(store.getId())
            .build();

        // when
        bossOrderService.addToCompletedOrder(request, boss.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).isEmpty();

        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orderHistories).hasSize(1);
    }


    private Orders order() {
        return Orders.newOrder(store, boss.getId(), paymentMethod, LocalDateTime.now().plusHours(1L));
    }

}
