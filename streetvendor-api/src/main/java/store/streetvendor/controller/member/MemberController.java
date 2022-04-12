package store.streetvendor.controller.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.service.member.dto.request.MemberSignUpRequestDto;
import store.streetvendor.service.member.dto.response.MemberInfoResponse;

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

    @Auth
    @ApiOperation(value = "마이 페이지")
    @GetMapping("/api/v1/my-page")
    public ApiResponse<MemberInfoResponse> memberInfo(@MemberId Long memberId) {
        return ApiResponse.success(memberService.getMyInformation(memberId));
    }

}
