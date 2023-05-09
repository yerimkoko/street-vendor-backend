package store.streetvendor.service.review;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.utils.dto.review.request.AddReviewRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewServiceTest extends MemberFixture {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;


    @AfterEach
    void cleanUp() {
        reviewRepository.deleteAll();
        orderHistoryRepository.deleteAll();
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 리뷰를_등록한다() {
        // given
        String comment = "리뷰 입니다.";
        int rate = 1;
        OrderHistory orderHistory = createOrderHistory(createOrder());
        AddReviewRequest request = AddReviewRequest.builder()
            .comment(comment)
            .rate(rate)
            .orderId(orderHistory.getOrderId())
            .build();

        // when
        reviewService.addReview(request, orderHistory.getMemberId());

        // then
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getRate().getValue()).isEqualTo(rate);
        assertThat(reviews.get(0).getComment()).isEqualTo(comment);

    }

    private Orders createOrder() {
        Orders order = Orders.newOrder(store(), createMember().getId(), PaymentMethod.ACCOUNT_TRANSFER);
        orderRepository.save(order);
        return order;
    }


    private OrderHistory createOrderHistory(Orders order) {
        OrderHistory orderHistory = OrderHistory.newHistory(order.getStore(), order, createMember().getId(), OrderStatusCanceled.ACTIVE);
        orderHistoryRepository.save(orderHistory);
        return orderHistory;
    }

    private Store store() {
        Long bossId = 1L;
        String name = "토끼의 새로운 붕어빵";
        String storeDescription = "팥 붕어빵 맛집";
        String locationDescription = "군포역 2번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;
        Location location = new Location(34.2222, 128.222);
        Store store = Store.newInstance(bossId, name, location, storeDescription, locationDescription, category);
        storeRepository.save(store);

        return store;
    }

    private Member createMember() {
        return memberRepository.save(member());
    }
}
