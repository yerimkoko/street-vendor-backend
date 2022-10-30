package store.streetvendor.core.domain.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.core.domain.admin.repository.AdminRepositoryCustom;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
}
