package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.*;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.service.order.dto.request.OrderMenusRequest;
import store.streetvendor.service.order_history.dto.AddNewOrderHistoryRequest;
import store.streetvendor.service.order_history.dto.OrderHistoryMenusRequest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 주문을_한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        Menu menu = createMenu(store);

        Days friDay = Days.FRI;
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        BusinessHours businessHours = createBusinessHours(store, friDay, startTime, endTime);

        List<Menu> menus = List.of(menu);

        store.addMenus(List.of(menu));

        store.addBusinessDays(List.of(businessHours));

        storeRepository.save(store);

        Menu findMenu = store.findMenu(store.getMenus().get(0).getId());

        int totalCount = 2;

        OrderMenusRequest orderMenusRequest = OrderMenusRequest.testBuilder()
            .menu(findMenu)
            .count(totalCount)
            .build();

        List<OrderMenusRequest> orderMenusRequests = List.of(orderMenusRequest);

        AddNewOrderRequest addNewOrderRequest = AddNewOrderRequest.testBuilder()
            .storeId(store.getId())
            .menus(orderMenusRequests)
            .build();

        // when
        orderService.addNewOrder(addNewOrderRequest, member.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertOrder(orders.get(0), member.getId(), store.getId());

        List<OrderMenu> orderMenus = orderMenuRepository.findAll();
        assertThat(orderMenus).hasSize(1);
        assertThat(orderMenus.get(0).getMenu().getId()).isEqualTo(menus.get(0).getId());
    }

    @Test
    void 사장님이_들어온_주문을_확인하고_준비중_상태로_변경한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        storeRepository.save(store);

        Orders myOrder = Orders.newOrder(store.getId(), member.getId());
        orderRepository.save(myOrder);

        // when
        orderService.changeStatusToReady(store.getId(), member.getId(), myOrder.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.READY);

    }

    @Test
    void 사장님에게_주문상태가_READY일때_COMPLETE로_변경한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        storeRepository.save(store);

        Orders order = orderRepository.save(Orders.newOrder(store.getId(), member.getId()));

        Menu menu = Menu.of(store, "슈붕", 3, 1000, "http:/3");

        OrderMenu orderMenu = OrderMenu.of(order, menu, 3);
        List<OrderHistoryMenusRequest> menusRequest = List.of(new OrderHistoryMenusRequest(menu, orderMenu));
        AddNewOrderHistoryRequest request = new AddNewOrderHistoryRequest(order.getId(), store.getId(), menusRequest);

        order.changeStatusToReady();
        orderRepository.save(order);

        // when
        orderService.changeStatusToComplete(store.getId(), member.getId(), order.getId(), request);

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);

        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }

    @Test
    void 주문이_거래완료가_되면_기존_Order가_삭제된다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        Menu menu = createMenu(store);
        store.addMenus(List.of(menu));
        storeRepository.save(store);

        Orders order = orderRepository.save(Orders.newOrder(store.getId(), member.getId()));
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
        orderService.addToCompletedOrder(request, member.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).isEmpty();

    }

    @Test
    void 사장님이_주문을_취소한다() {
        // given
        Member boss = createMember();
        Member user = createMember();
        Store store = createStore(boss);
        storeRepository.save(store);

        Orders order = orderRepository.save(Orders.newOrder(store.getId(), user.getId()));

        // when
        orderService.cancelOrderByBoss(store.getId(), order.getId(), boss.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatusCanceled()).isEqualTo(OrderStatusCanceled.CANCELED);
    }

    @Test
    void 사용자가_주문을_취소한다() {
        // given
        Member boss = createMember();
        Member user = createMember();
        Store store = createStore(boss);
        storeRepository.save(store);

        Orders order = orderRepository.save(Orders.newOrder(store.getId(), user.getId()));

        // when
        orderService.cancelOrderByUser(order.getId(), user.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderStatusCanceled()).isEqualTo(OrderStatusCanceled.CANCELED);

    }

    void assertOrder(Orders orders, Long memberId, Long storeId) {
        assertThat(orders.getMemberId()).isEqualTo(memberId);
        assertThat(orders.getStoreId()).isEqualTo(storeId);
    }

    private OrderMenu createOrderMenu(Orders orders, Menu menu) {
        int count = 3;
        OrderMenu orderMenu = OrderMenu.of(orders, menu, count);
        return orderMenuRepository.save(orderMenu);
    }




    private Member createMember() {
        String name = "yerimkoko";
        String nickName = "yerimko";
        String email = "gochi97@naver.com";
        String pictureUrl = "https://rabbit.com";

        Member member = Member.newGoogleInstance(name, nickName, email, pictureUrl);
        return memberRepository.save(member);

    }

    private Store createStore(Member member) {
        String location = "신정네거리 3번출구";
        String description = "토끼네";

        return Store.newInstance(member.getId(), member.getName(), member.getProfileUrl(), location, description);
    }

    private Menu createMenu(Store store) {
        int count = 2;
        int price = 2000;
        String menuName = "슈크림 2개";
        String pictureUrl = "https://rabbit.shop";
        return Menu.of(store, menuName, count, price, pictureUrl);
    }

    private BusinessHours createBusinessHours(Store store, Days days, LocalTime startTime, LocalTime endTime) {
        return BusinessHours.of(store, days, startTime, endTime);
    }

}
