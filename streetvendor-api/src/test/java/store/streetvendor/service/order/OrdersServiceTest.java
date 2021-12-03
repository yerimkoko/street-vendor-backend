package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
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

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
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
        LocalTime startTime = LocalTime.of(10, 00, 00);
        LocalTime endTime = LocalTime.of(18, 00, 00);

        return Store.newInstance(member.getId(), member.getName(), member.getProfileUrl(), description, location, startTime, endTime);
    }

    private Menu createMenu(Store store) {
        int count = 2;
        int price = 2000;
        String menuName = "슈크림 2개";
        String pictureUrl = "https://rabbit.shop";
        return Menu.of(store, menuName, count, price, pictureUrl);
    }

    @Test
    @Transactional
    void 주문을_한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        Menu menu = createMenu(store);

        List<Menu> menus = List.of(menu);

        store.addMenus(menus);

        storeRepository.save(store);

        Menu findMenu = store.findMenu(store.getMenus().get(0).getId());

        int totalCount = 2;

        OrderMenusRequest orderMenusRequest = OrderMenusRequest.testBuilder()
            .menuId(findMenu.getId())
            .totalCount(totalCount)
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
        assertOrder(orders.get(0), member.getId(), store.getId(), menu.getId());
    }

    void assertOrder(Orders orders, Long memberId, Long storeId, Long menuId) {
        assertThat(orders.getOrderMenus().get(0).getMenuId()).isEqualTo(menuId);
        assertThat(orders.getMemberId()).isEqualTo(memberId);
        assertThat(orders.getStoreId()).isEqualTo(storeId);
    }

}
