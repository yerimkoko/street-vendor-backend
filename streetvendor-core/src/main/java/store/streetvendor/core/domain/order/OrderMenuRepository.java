package store.streetvendor.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 테스트용 레포지토리
 */
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
