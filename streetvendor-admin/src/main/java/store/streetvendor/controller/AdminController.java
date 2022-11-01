package store.streetvendor.controller;

import org.springframework.web.bind.annotation.PutMapping;
import store.streetvendor.controller.dto.UpdateStoreRequest;
import store.streetvendor.controller.dto.request.SignOutMemberRequest;
import store.streetvendor.controller.dto.request.LoginRequest;
import store.streetvendor.core.service.utils.dto.ApiResponse;
import store.streetvendor.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        adminService.login(request);
        return ApiResponse.OK;
    }

    @PutMapping("/admin/sign-out-member")
    public ApiResponse<Long> signOut(@RequestBody SignOutMemberRequest request) {
        return ApiResponse.success(adminService.signOutMember(request));
    }

    @PutMapping("/admin/update-store")
    public ApiResponse<String> updateStore(@RequestBody UpdateStoreRequest request) {
        adminService.updateStoreStatus(request.getStoreId(), request.getAdminId(), request.getSalesStatus());
        return ApiResponse.OK;
    }

}
