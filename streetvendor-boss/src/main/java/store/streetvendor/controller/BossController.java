package store.streetvendor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.BossService;
import store.streetvendor.service.dto.request.BossSignUpRequest;

import javax.servlet.http.HttpSession;

import static store.streetvendor.auth.BossConstants.BOSS_ID;

@RestController
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    private final HttpSession httpSession;

    @PostMapping("/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody BossSignUpRequest request) {
        Long bossId = bossService.bossSignUp(request);
        httpSession.setAttribute(BOSS_ID, bossId);
        return ApiResponse.success(httpSession.getId());
    }

}
