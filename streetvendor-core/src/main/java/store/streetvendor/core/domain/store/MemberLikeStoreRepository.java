package store.streetvendor.core.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikeStoreRepository extends JpaRepository<MemberLikeStore, Long>, MemberLikeStoreCustom {
}
