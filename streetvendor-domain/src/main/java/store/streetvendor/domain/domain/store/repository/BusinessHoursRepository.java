package store.streetvendor.domain.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.store.BusinessHours;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
}
