package store.streetvendor.core.domain.order_history;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.core.domain.order_history.repository.OrderHistoryRepositoryCustom;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>, OrderHistoryRepositoryCustom {
}
