package store.streetvendor.service.utils;

import store.streetvendor.core.domain.admin.Admin;
import store.streetvendor.core.domain.admin.AdminRepository;
import store.streetvendor.core.exception.NotFoundException;

public class AdminServiceUtils {

    private AdminServiceUtils() {
    }

    public static Admin findAdmin(Long adminId, AdminRepository adminRepository) {
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 관리자는 존재하지 않습니다.", adminId));
        }
        return admin;
    }


}
