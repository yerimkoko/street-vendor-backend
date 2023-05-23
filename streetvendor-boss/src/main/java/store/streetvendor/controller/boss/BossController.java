package store.streetvendor.controller.boss;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.service.boss.BossService;
import store.streetvendor.service.dto.request.BossSignUpRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static store.streetvendor.core.auth.AuthConstants.MEMBER_ID;

@RestController
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    private final HttpSession httpSession;

    @ApiOperation(value = "[사장님] 회원가입을 합니다.")
    @PostMapping("/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody @Valid BossSignUpRequest request) {
        Long bossId = bossService.bossSignUp(request);
        httpSession.setAttribute(MEMBER_ID, bossId);
        return ApiResponse.success(httpSession.getId());
    }

    @ApiOperation(value = "[테스트용] 세션을 가져옵니다.")
    @PostMapping("/v1/get-session")
    public ApiResponse<String> getTestSession() {
        return ApiResponse.success(httpSession.getAttribute(MEMBER_ID).toString());
    }




}
