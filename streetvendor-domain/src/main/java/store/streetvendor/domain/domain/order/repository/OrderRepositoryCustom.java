package store.streetvendor.domain.domain.order.repository;

import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.Orders;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Orders> findOrdersByStoreIdAndBossIdAndStatus(Long storeId, Long bossId, OrderStatus status);

    List<Orders> findOrdersByMemberId(Long memberId);

    Orders findByOrderId(Long orderId);

    Orders findByOrderAndMemberId(Long orderId, Long memberId);

}
