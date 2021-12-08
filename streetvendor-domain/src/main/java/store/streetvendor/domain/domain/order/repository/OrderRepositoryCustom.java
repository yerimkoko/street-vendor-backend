package store.streetvendor.domain.domain.order.repository;

import store.streetvendor.domain.domain.order.Orders;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Orders> findOrdersByStoreId(Long storeId);

}
