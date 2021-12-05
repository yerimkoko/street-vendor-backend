package store.streetvendor.domain.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.order.repository.projection.OrdersToBossProjection;
import store.streetvendor.domain.domain.order.repository.projection.QOrdersToBossProjection;

import java.util.List;

import static store.streetvendor.domain.domain.order.QOrders.orders;


@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrdersToBossProjection> findOrdersByStoreId(Long storeId) {
        return jpaQueryFactory.select(new QOrdersToBossProjection(orders.memberId,
            orders.id,
            orders.orderStatus,
            orders.orderMenus,
            orders.createdAt
        ))
            .from(orders)
            .where(orders.storeId.eq(storeId))
            .fetch();
    }

}
