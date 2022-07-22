package store.streetvendor.domain.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.store.Menu;

/**
 * 테스트 용도의 레포지토리
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
