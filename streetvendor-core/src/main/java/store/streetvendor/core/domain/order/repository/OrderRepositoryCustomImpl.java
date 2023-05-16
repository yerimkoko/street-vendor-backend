package store.streetvendor.core.domain.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;

import java.util.List;

import static store.streetvendor.core.domain.order.QOrders.orders;


@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Orders> findOrdersByStoreIdAndBossIdAndStatus(Long storeId, Long bossId, OrderStatus orderStatus) {
        return jpaQueryFactory
            .selectFrom(orders)
            .where(orders.store.id.eq(storeId),
                orders.store.bossId.eq(bossId),
                getUserStatusEq(orderStatus))
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
                orders.memberId.eq(memberId),
                orders.orderStatusCanceled.eq(OrderStatusCanceled.ACTIVE)
            )
            .fetchOne();
    }



    private BooleanExpression getUserStatusEq(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return orders.orderStatus.eq(status);
    }

}
