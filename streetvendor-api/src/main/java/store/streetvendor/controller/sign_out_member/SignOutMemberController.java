package store.streetvendor.controller.sign_out_member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.sign_out_member.SignOutMemberService;

@RestController
@Getter
@RequiredArgsConstructor
public class SignOutMemberController {

    private final SignOutMemberService service;

    @PostMapping("/api/v1/sign-out")
    public ApiResponse<Long> signOut(@MemberId Long memberId) {
        return ApiResponse.success(service.signOut(memberId));
    }
}
