package store.streetvendor.domain.domain.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.admin.repository.AdminRepositoryCustom;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
}
