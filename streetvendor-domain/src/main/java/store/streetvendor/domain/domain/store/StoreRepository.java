package store.streetvendor.domain.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.store.repository.StoreRepositoryCustom;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

}
