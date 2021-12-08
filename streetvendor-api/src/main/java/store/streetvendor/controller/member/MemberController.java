package store.streetvendor.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

}
