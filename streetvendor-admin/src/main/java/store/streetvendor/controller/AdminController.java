package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.Admin;
import store.streetvendor.config.AdminId;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.AdminService;
import lombok.RequiredArgsConstructor;
import store.streetvendor.service.dto.request.AdminSignUpRequest;

import javax.servlet.http.HttpSession;

import static store.streetvendor.config.AdminConstants.ADMIN_ID;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final HttpSession httpSession;


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

    @ApiOperation(value = "[관리자] 로그인")
    @PostMapping("/v1/login")
    public ApiResponse<AuthResponse> handleAdminGoogleAuthentication(AuthRequest request) {
        return ApiResponse.success(adminService.handleAdminGoogleAuthentication(request));
    }

    @ApiOperation(value = "[관리자] 회원가입")
    @PostMapping("/v1/sign-up")
    public ApiResponse<String> adminSignUp(@RequestBody AdminSignUpRequest request) {
        Long adminId = adminService.adminSignUp(request);
        httpSession.setAttribute(ADMIN_ID, adminId);
        return ApiResponse.success(httpSession.getId());
    }

}
