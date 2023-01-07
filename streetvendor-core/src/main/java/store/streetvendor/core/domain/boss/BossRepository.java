package store.streetvendor.core.domain.boss;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BossRepository extends JpaRepository<Boss, Long>, BossRepositoryCustom {

}
