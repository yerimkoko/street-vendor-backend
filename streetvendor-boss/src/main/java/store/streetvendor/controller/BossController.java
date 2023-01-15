package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.auth.Boss;
import store.streetvendor.auth.BossId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.BossService;
import store.streetvendor.core.utils.dto.store.request.AddNewStoreRequest;
import store.streetvendor.service.dto.request.BossSignUpRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static store.streetvendor.auth.BossConstants.BOSS_ID;

@RestController
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    private final HttpSession httpSession;

    @ApiOperation(value = "[사장님] 회원가입을 합니다.")
    @PostMapping("/v1/sign-up")
    public ApiResponse<String> signUp(@RequestBody BossSignUpRequest request) {
        Long bossId = bossService.bossSignUp(request);
        httpSession.setAttribute(BOSS_ID, bossId);
        return ApiResponse.success(httpSession.getId());
    }

    @Boss
    @ApiOperation(value = "[사장님] 창업을 합니다.")
    @PostMapping("/v1/create-store")
    public ApiResponse<String> addNewStore(@Valid @RequestBody AddNewStoreRequest request, @BossId Long bossId) {
        bossService.addNewStore(request, bossId);
        return ApiResponse.OK;
    }


}
