package store.streetvendor.domain.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.store.Payment;

/**
 * 테스트 용도의 레포지토리
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
