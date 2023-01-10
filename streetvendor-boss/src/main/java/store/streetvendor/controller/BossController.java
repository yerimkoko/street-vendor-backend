package store.streetvendor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.BossService;
import store.streetvendor.service.dto.request.BossSignUpRequest;

@RestController
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    @PostMapping("/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody BossSignUpRequest request) {
        bossService.bossSignUp(request);
        return ApiResponse.OK;
    }




}
