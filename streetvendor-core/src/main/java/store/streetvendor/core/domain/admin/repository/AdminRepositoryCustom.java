package store.streetvendor.core.domain.admin.repository;

import store.streetvendor.core.domain.admin.Admin;

public interface AdminRepositoryCustom {

    Admin findByEmail(String email);

    Admin findByAdminId(Long id);

}
