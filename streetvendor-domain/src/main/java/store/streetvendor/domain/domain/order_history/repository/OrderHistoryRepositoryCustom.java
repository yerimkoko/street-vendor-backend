package store.streetvendor.domain.domain.order_history.repository;

import store.streetvendor.domain.domain.order_history.OrderHistory;

import java.util.List;

public interface OrderHistoryRepositoryCustom  {

    List<OrderHistory> findByOrderHistoryByStoreId(Long storeId);

    List<OrderHistory> findByOrderHistoryByMemberId(Long memberId);

}
