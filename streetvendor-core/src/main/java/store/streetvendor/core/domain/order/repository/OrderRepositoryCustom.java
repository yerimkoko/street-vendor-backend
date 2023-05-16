package store.streetvendor.core.domain.order.repository;

import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.Orders;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Orders> findOrdersByStoreIdAndBossIdAndStatus(Long storeId, Long bossId, OrderStatus status);

    List<Orders> findOrdersByMemberId(Long memberId);

    Orders findByOrderId(Long orderId);

    Orders findByOrderAndMemberId(Long orderId, Long memberId);

}
