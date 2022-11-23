package store.streetvendor.controller.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.core.config.auth.MemberId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.core.utils.dto.member.request.MemberSaveBossInfoRequest;
import store.streetvendor.core.utils.dto.member.request.MemberSignUpRequestDto;
import store.streetvendor.core.utils.dto.member.response.MemberInfoResponse;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static store.streetvendor.core.config.auth.AuthConstants.MEMBER_ID;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private final HttpSession httpSession;

    @ApiOperation("회원 가입")
    @PostMapping("/api/v1/sign-up")
    public ApiResponse<String> signUp(@Valid @RequestBody MemberSignUpRequestDto request) {
        Long memberId = memberService.signUp(request);
        httpSession.setAttribute(MEMBER_ID, memberId);
        return ApiResponse.success(httpSession.getId());
    }

    @Auth
    @ApiOperation(value = "마이 페이지")
    @GetMapping("/api/v1/my-page")
    public ApiResponse<MemberInfoResponse> memberInfo(@MemberId Long memberId) {
        return ApiResponse.success(memberService.getMyInformation(memberId));
    }

    @Auth
    @ApiOperation(value = "사장님 정보 등록 하기")
    @PostMapping("/api/v1/bossInfo")
    public ApiResponse<String> saveBossInfo(@MemberId Long memberId, @Valid @RequestBody MemberSaveBossInfoRequest request) {
        memberService.saveMemberBossInfo(memberId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "회원 탈퇴")
    @PostMapping("/api/v1/sign-out")
    public ApiResponse<Long> signOut(@MemberId Long memberId) {
        return ApiResponse.success(memberService.signOut(memberId));
    }

    @Auth
    @ApiOperation(value = "사장님 정보 확인")
    @GetMapping("/api/v1/boss/check")
    public ApiResponse<String> checkBoss(@MemberId Long memberId) {
        memberService.checkBoss(memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "프로필 사진 수정하기")
    @PutMapping("/api/v1/my-page/profileUrl")
    public ApiResponse<String> changeMyProfile(@MemberId Long memberId, @RequestBody String profileUrl) {
        memberService.changeProfileImage(memberId, profileUrl);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "닉네임 수정하기")
    @PutMapping("/api/v1/my-page/nickname")
    public ApiResponse<String> changeMyNickName(@MemberId Long memberId, @RequestParam String nickName) {
        memberService.changeNickName(memberId, nickName);
        return ApiResponse.OK;
    }

}
