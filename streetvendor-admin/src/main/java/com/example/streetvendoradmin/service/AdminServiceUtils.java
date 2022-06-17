package com.example.streetvendoradmin.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.admin.Admin;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.exception.UnAuthorizedException;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminServiceUtils {

    public static Optional<Admin> validateAdmin(AdminRepository adminRepository, Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isEmpty()) {
            throw new UnAuthorizedException(String.format("<%s>는 관리자 아이디가 아닙니다. 관리자로 로그인해주세요.", adminId));
        }
        return admin;
    }

}
