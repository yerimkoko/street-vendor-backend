package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.Order;
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
public class OrderServiceTest {

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
        memberRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
    }

    @Test
    void 주문을_한다() {
        // given
        String name = "yerimkoko";
        String nickName = "yerimko";
        String email = "gochi97@naver.com";
        String pictureUrl = "https://rabbit.com";

        String location = "신정네거리 3번출구";
        String description = "토끼네";
        LocalTime startTime = LocalTime.of(10,00,00);
        LocalTime endTime = LocalTime.of(18,00,00);

        // menu
        String menuName = "슈크림 2개";
        int count = 2;
        int price = 2000;

        Member member = Member.newGoogleInstance(name, nickName, email, pictureUrl);
        memberRepository.save(member);

        Store store = Store.newInstance(member.getId(), member.getName(), member.getProfileUrl(), description, location, startTime, endTime);

        Menu menu = Menu.of(store, menuName, count, price, pictureUrl);
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
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getMemberId()).isEqualTo(member.getId());


    }

}
