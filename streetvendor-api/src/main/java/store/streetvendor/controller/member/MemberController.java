package store.streetvendor.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.dto.ApiResponse;
import store.streetvendor.controller.dto.member.MemberSignUpRequestDto;
import store.streetvendor.service.member.MemberService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    private final HttpSession httpSession;

    @PostMapping("/api/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody MemberSignUpRequestDto request) {
        Long memberId = memberService.signUp(request);
        httpSession.setAttribute("memberId", memberId);
        return ApiResponse.success(httpSession.getId());
    }

}
