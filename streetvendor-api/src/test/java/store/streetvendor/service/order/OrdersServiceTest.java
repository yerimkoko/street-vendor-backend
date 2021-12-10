package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.*;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.service.order.dto.request.OrderMenusRequest;

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
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(10,0);
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
        order.changeStatusToReady();
        orderRepository.save(order);

        // when
        orderService.changeStatusToComplete(store.getId(), member.getId(), order.getId());

        // then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);

        assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }



    void assertOrder(Orders orders, Long memberId, Long storeId) {
        assertThat(orders.getMemberId()).isEqualTo(memberId);
        assertThat(orders.getStoreId()).isEqualTo(storeId);
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
