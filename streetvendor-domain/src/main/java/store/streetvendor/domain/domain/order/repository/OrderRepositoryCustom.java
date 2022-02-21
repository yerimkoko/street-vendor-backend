package store.streetvendor.domain.domain.order.repository;

import store.streetvendor.domain.domain.order.Orders;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Orders> findOrdersByStoreId(Long storeId);
    Orders findByOrderId(Long orderId);
    List<Orders> findOrdersByOrderIdAndMemberId(Long memberId, Long orderId);
    Orders findByOrderAndMemberId(Long orderId, Long memberId);
}
