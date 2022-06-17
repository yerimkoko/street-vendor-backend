package store.streetvendor.domain.domain.admin.repository;

import store.streetvendor.domain.domain.admin.Admin;

public interface AdminRepositoryCustom {

    Admin findByEmail(String email);

}
