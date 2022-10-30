package store.streetvendor.core.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 테스트 용도의 레포지토리
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
