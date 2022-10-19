package store.streetvendor.domain.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.order.Orders;

import java.util.List;

import static store.streetvendor.domain.domain.order.QOrders.orders;


@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Orders> findOrdersByStoreId(Long storeId) {
        return jpaQueryFactory
            .selectFrom(orders)
            .where(orders.store.id.eq(storeId))
            .fetch();
    }

    @Override
    public List<Orders> findOrdersByMemberId(Long memberId) {
        return jpaQueryFactory
            .selectFrom(orders)
            .where(orders.memberId.eq(memberId))
            .fetch();
    }

    @Override
    public Orders findByOrderId(Long orderId) {
        return jpaQueryFactory
            .selectFrom(orders)
            .where(orders.id.eq(orderId))
            .fetchOne();
    }

    @Override
    public Orders findByOrderAndMemberId(Long orderId, Long memberId) {
        return jpaQueryFactory
            .selectFrom(orders)
            .where(orders.id.eq(orderId),
                orders.memberId.eq(memberId))
            .fetchOne();
    }

}
