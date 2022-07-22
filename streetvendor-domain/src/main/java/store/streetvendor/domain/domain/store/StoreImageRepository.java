package store.streetvendor.domain.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.store.StoreImage;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
}
