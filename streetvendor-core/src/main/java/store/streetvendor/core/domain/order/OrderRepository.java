package store.streetvendor.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.core.domain.order.repository.OrderRepositoryCustom;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {

}
