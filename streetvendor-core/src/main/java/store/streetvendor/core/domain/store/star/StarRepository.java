package store.streetvendor.core.domain.store.star;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long>, StarRepositoryCustom {
}