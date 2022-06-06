package store.streetvendor.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.admin.AdminService;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("api/v1/admin/sign-out")
    public ApiResponse<Long> signOutMember(Long memberId, Long adminId) {
        return ApiResponse.success(adminService.signOutMember(memberId, adminId));
    }

}
