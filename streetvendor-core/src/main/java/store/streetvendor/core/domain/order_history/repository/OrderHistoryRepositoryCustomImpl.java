package store.streetvendor.core.domain.order_history.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.core.domain.order_history.OrderHistory;

import static store.streetvendor.core.domain.order_history.QOrderHistory.orderHistory;


import java.util.List;

@RequiredArgsConstructor
public class OrderHistoryRepositoryCustomImpl implements  OrderHistoryRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderHistory> findOrderHistoryByStoreId(Long storeId, Long bossId) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.storeInfo.storeId.eq(storeId),
                orderHistory.storeInfo.bossId.eq(bossId)
            ).fetch();
    }

    @Override
    public List<OrderHistory> findOrderHistoryByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.memberId.eq(memberId)
            ).fetch();
    }

    @Override
    public OrderHistory findOrderHistoryById(Long id) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.id.eq(id)
            ).fetchOne();
    }


    @Override
    public List<OrderHistory> findOrderHistoryByOrderIdAndMemberId(Long orderId, Long memberId) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.orderId.eq(orderId),
                orderHistory.memberId.eq(memberId)
            ).fetch();
    }
}
