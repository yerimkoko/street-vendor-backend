package store.streetvendor.domain.domain.store.star;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long>, StarRepositoryCustom {
}
