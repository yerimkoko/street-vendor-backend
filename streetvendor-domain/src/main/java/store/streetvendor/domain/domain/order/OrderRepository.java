package store.streetvendor.domain.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.order.repository.OrderRepositoryCustom;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
