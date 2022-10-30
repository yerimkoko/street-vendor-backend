package store.streetvendor.core.domain.order_history.repository;

import store.streetvendor.core.domain.order_history.OrderHistory;

import java.util.List;

public interface OrderHistoryRepositoryCustom  {

    List<OrderHistory> findOrderHistoryByStoreId(Long storeId, Long bossId);

    List<OrderHistory> findOrderHistoryByMemberId(Long memberId);

    OrderHistory findOrderHistoryByOrderId(Long orderId);

}
