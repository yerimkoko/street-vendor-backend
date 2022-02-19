package store.streetvendor.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.service.member.dto.request.MemberSignUpRequestDto;

import javax.servlet.http.HttpSession;

import static store.streetvendor.config.auth.AuthConstants.MEMBER_ID;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private final HttpSession httpSession;

    @PostMapping("/api/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody MemberSignUpRequestDto request) {
        Long memberId = memberService.signUp(request);
        httpSession.setAttribute(MEMBER_ID, memberId);
        return ApiResponse.success(httpSession.getId());
    }

    @Auth
    @PutMapping("/api/v1/sign-out")
    public ApiResponse<Long> signOut(@MemberId Long memberId) {
        memberService.signOut(memberId);
        return ApiResponse.success(memberId);
    }

}
