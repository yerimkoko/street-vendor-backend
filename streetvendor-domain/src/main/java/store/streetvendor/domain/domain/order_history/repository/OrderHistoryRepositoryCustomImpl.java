package store.streetvendor.domain.domain.order_history.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.order_history.OrderHistory;

import static store.streetvendor.domain.domain.order_history.QOrderHistory.orderHistory;


import java.util.List;

@RequiredArgsConstructor
public class OrderHistoryRepositoryCustomImpl implements  OrderHistoryRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderHistory> findByOrderHistoryByStoreId(Long storeId) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.storeInfo.storeId.eq(storeId)
            ).fetch();
    }

    @Override
    public List<OrderHistory> findByOrderHistoryByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(orderHistory)
            .where(
                orderHistory.memberId.eq(memberId)
            ).fetch();
    }
}
