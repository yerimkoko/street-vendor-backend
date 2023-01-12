package store.streetvendor.service;

import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;
import store.streetvendor.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.admin.Admin;
import store.streetvendor.core.domain.admin.AdminRepository;
import store.streetvendor.external.google.GoogleApiCaller;
import store.streetvendor.external.google.dto.response.GoogleUserInfoResponse;
import store.streetvendor.service.utils.AdminServiceUtils;

import javax.servlet.http.HttpSession;

import static store.streetvendor.config.AdminConstants.ADMIN_ID;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final HttpSession httpSession;

    private final GoogleApiCaller googleApiCaller;


    @Transactional
    public Long signOutMember(Long adminId) {
        Admin admin = AdminServiceUtils.findAdmin(adminId, adminRepository);
        adminRepository.delete(admin);
        return adminId;
    }


    @Transactional
    public AuthResponse handleAdminGoogleAuthentication(AuthRequest request) {
        GoogleUserInfoResponse adminInfoResponse = googleApiCaller.getGoogleUserProfileInfo(request.getRequestToken());
        Admin admin = adminRepository.findByEmail(adminInfoResponse.getEmail());

        if (admin == null) {
            throw new NotFoundException(String.format("[%s]는 관리자가 아닙니다.", adminInfoResponse.getEmail()));
        }

        httpSession.setAttribute(ADMIN_ID, admin.getId());

        return AuthResponse.logIn(httpSession.getId());

    }


}
