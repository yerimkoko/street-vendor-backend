package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.order.*;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenuRepository;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.domain.domain.model.exception.NotFoundException;
import store.streetvendor.domain.domain.store.menu.Menu;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.service.order.dto.request.OrderMenusRequest;
import store.streetvendor.service.order_history.dto.request.AddNewOrderHistoryRequest;
import store.streetvendor.service.order_history.dto.request.OrderHistoryMenusRequest;
import store.streetvendor.service.store.SetUpStore;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OrdersServiceTest extends SetUpStore {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderHistoryMenuRepository orderHistoryMenuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        orderHistoryRepository.deleteAll();
        cleanup();
    }

    @Test
    void 주문을_한다() {
        // given
        Location location = new Location(30.78639644286605, 126.40572677813635);

        AddNewOrderRequest addNewOrderRequest = AddNewOrderRequest.testBuilder()
            .storeId(store.getId())
            .location(location)
            .paymentMethod(paymentMethod)
            .menus(List.of(createMenuRequest()))
            .build();

        // when
        orderService.addNewOrder(addNewOrderRequest, member.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertOrder(orders.get(0), member.getId(), store.getId());

        List<OrderMenu> orderMenus = orderMenuRepository.findAll();
        assertThat(orderMenus).hasSize(1);
        assertThat(orderMenus.get(0).getMenu().getId()).isEqualTo(menu.getId());
        assertThat(orderMenus.get(0).getTotalPrice()).isEqualTo(menu.getPrice() * createMenuRequest().getCount());

    }

    @Test
    void 주문이_안될_때() {
        // given
        Location location = new Location(30.78639644286605, 126.40572677813635);

        AddNewOrderRequest addNewOrderRequest = AddNewOrderRequest.testBuilder()
            .storeId(store.getId() + 1)
            .location(location)
            .paymentMethod(paymentMethod)
            .menus(List.of(createMenuRequest()))
            .build();

        // when & then
        assertThatThrownBy(() -> orderService.addNewOrder(addNewOrderRequest, member.getId() + 998L))
            .isInstanceOf(NotFoundException.class);

    }

    @Test
    void 사장님이_들어온_주문을_확인하고_PREPARING_상태로_변경한다() {
        // given
        Orders order = orderRepository.save(order());

        // when
        orderService.changeStatusToPreparing(store.getId(), boss.getId(), order.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.PREPARING);

    }

    @Test
    void 사장님에게_주문상태가_PREPARING_일때_READY_TO_PICK_UP_으로_변경한다() {
        // given
        Orders order = orderRepository.save(order());

        order.changeStatusToPreparing();
        orderRepository.save(order);

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
        orderService.changeStatusToReadyToPickUp(boss.getId(), historyRequest);

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).isEmpty();

        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orderHistories).hasSize(1);

    }

    // TODO: 값 비교하기
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
        orderService.addToCompletedOrder(request, boss.getId());

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
        orderService.cancelOrderByBoss(store.getId(), order.getId(), boss.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orders).isEmpty();
        assertThat(orderHistories).hasSize(1);
    }

    @Test
    @Disabled
    /**
     * TODO: 다시 짜기
     */
    void 사용자가_주문을_취소한다() {
        // given
        Orders order = orderRepository.save(order());

        // when
        orderService.cancelOrderByUser(order.getId(), member.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).isEmpty();

        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();
        assertThat(orderHistories).hasSize(1);
        assertOrderHistory(orderHistories.get(0), order.getId(), member.getId(), order.getStore().getId());

        List<OrderHistoryMenu> orderHistoryMenus = orderHistoryMenuRepository.findAll();
        assertThat(orderHistoryMenus).hasSize(1);

        assertOrderHistoryMenu(orderHistoryMenus.get(0));

    }

    private void assertOrderHistoryMenu(OrderHistoryMenu orderHistoryMenu) {
        assertThat(orderHistoryMenu.getMenuName()).isEqualTo(menuName);
        assertThat(orderHistoryMenu.getPrice()).isEqualTo(price);
        assertThat(orderHistoryMenu.getCount()).isEqualTo(count);
        assertThat(orderHistoryMenu.getPictureUrl()).isEqualTo(pictureUrl);
    }

    void assertOrderHistory(OrderHistory orderHistory, Long orderId, Long memberId, Long storeId) {
        assertThat(orderHistory.getOrderId()).isEqualTo(orderId);
        assertThat(orderHistory.getMemberId()).isEqualTo(memberId);
        assertThat(orderHistory.getStoreInfo().getStoreId()).isEqualTo(storeId);
    }


    // TODO: 체크하기
    @Test
    void 사용자가_주문을_취소할_때_주문이_존재하지_않을때() {
        // given
        Orders order = orderRepository.save(order());

        // when & then
        assertThatThrownBy(() -> orderService.cancelOrderByUser(order.getId() + 1, member.getId() + 999L))
            .isInstanceOf(NotFoundException.class);

    }

    void assertOrder(Orders orders, Long memberId, Long storeId) {
        assertThat(orders.getMemberId()).isEqualTo(memberId);
        assertThat(orders.getStore().getId()).isEqualTo(storeId);
    }

    private OrderMenu createOrderMenu(Orders orders, Menu menu) {
        int count = 3;
        OrderMenu orderMenu = OrderMenu.of(orders, menu, count);
        return orderMenuRepository.save(orderMenu);
    }

    private Orders order() {
        return Orders.newOrder(store, member.getId(), paymentMethod);
    }

    private OrderMenusRequest createMenuRequest() {
        int totalCount = 2;

        return OrderMenusRequest.testBuilder()
            .menu(menu)
            .count(totalCount)
            .build();

    }

}
