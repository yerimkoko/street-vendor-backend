package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.Admin;
import store.streetvendor.config.AdminId;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;
import store.streetvendor.dto.request.LoginRequest;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(value = "[관리자] 헬스체크")
    @GetMapping("/ping")
    public String pong() {
        return "관리자 서버입니다 pong!";
    }


    @Admin
    @ApiOperation(value = "[관리자-개발용] 회원 탈퇴")
    @PutMapping("/v1/sign-out-member")
    public ApiResponse<Long> signOut(@AdminId Long adminId) {
        return ApiResponse.success(adminService.signOutMember(adminId));
    }

    @Admin
    @ApiOperation(value = "[관리자] 로그인")
    @PostMapping("/v1/login")
    public ApiResponse<AuthResponse> handleAdminGoogleAuthentication(AuthRequest request) {
        return ApiResponse.success(adminService.handleAdminGoogleAuthentication(request));
    }


}
