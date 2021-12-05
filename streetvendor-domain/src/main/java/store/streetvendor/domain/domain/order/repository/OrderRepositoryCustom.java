package store.streetvendor.domain.domain.order.repository;

import store.streetvendor.domain.domain.order.repository.projection.OrdersToBossProjection;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrdersToBossProjection> findOrdersByStoreId(Long storeId);

}
