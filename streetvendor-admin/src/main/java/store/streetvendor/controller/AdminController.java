package store.streetvendor.controller;

import org.springframework.web.bind.annotation.PutMapping;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.dto.request.SignOutMemberRequest;
import store.streetvendor.dto.request.LoginRequest;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Auth
    @GetMapping("/admin/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        adminService.login(request);
        return ApiResponse.OK;
    }

    @Auth
    @PutMapping("/admin/sign-out-member")
    public ApiResponse<Long> signOut(@RequestBody SignOutMemberRequest request) {
        return ApiResponse.success(adminService.signOutMember(request));
    }


}
